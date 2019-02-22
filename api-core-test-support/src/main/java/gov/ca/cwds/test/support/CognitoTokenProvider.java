package gov.ca.cwds.test.support;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableMap;

/**
 * 
 * @author CWDS API Team
 *
 */
public class CognitoTokenProvider implements TokenProvider<CognitoLoginAuthParams> {

  private static final Logger LOG = LoggerFactory.getLogger(CognitoTokenProvider.class);
  private ChromeDriverService service;

  private void createAndStartService() {
    service = new ChromeDriverService.Builder()
        .usingDriverExecutable(new File(System.getenv("CHROME_DRIVER"))).usingAnyFreePort()
        .withEnvironment(ImmutableMap.of("DISPLAY", ":0.0")).build();
    try {
      service.start();
      LOG.info("Chrome Driver Service Started");
    } catch (IOException e) {
      LOG.info("Error in Chrome Driver Creation", e.getMessage());
    }
  }

  private WebDriver createWebDriver(ChromeDriverService service) {
    ChromeOptions chromeOptions = new ChromeOptions();
    chromeOptions.addArguments("--headless");
    chromeOptions.addArguments("--no-sandbox");
    DesiredCapabilities capabilities = new DesiredCapabilities();
    capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
    LOG.info("Web Driver Created");
    return new RemoteWebDriver(service.getUrl(), capabilities);
  }

  @Override
  public String doGetToken(CognitoLoginAuthParams loginParams) {
    try {
      createAndStartService();
      WebDriver driver = createWebDriver(service);
      WebDriverWait wait = new WebDriverWait(driver, 15);
      String tokenUrl = loginAndGetTokenUrl(driver, wait, loginParams);
      service.stop();
      LOG.info("Successful in getting Token URL ", tokenUrl);
      return tokenUrl.substring(tokenUrl.indexOf("token=") + 6, tokenUrl.length());
    } catch (Exception e) {
      LOG.info("Error in extracting Token", e.getMessage());
      service.stop();
      throw e;
    }
  }


  private String loginAndGetTokenUrl(WebDriver driver, WebDriverWait wait,
      CognitoLoginAuthParams loginParams) {
    driver.get(loginParams.getUrl() + "swagger");
    wait.until(ExpectedConditions
        .visibilityOfElementLocated(By.xpath("//input[@type='submit' and @value='LOGIN']")));
    driver.findElement(By.xpath("//input[@type='submit' and @value='LOGIN']")).click();
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));
    driver.findElement(By.id("email")).sendKeys(loginParams.getUser());
    driver.findElement(By.id("password")).sendKeys(loginParams.getPassword());
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("submit")));
    driver.findElement(By.id("submit")).click();
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("code")));
    driver.findElement(By.id("code")).sendKeys(loginParams.getCode());
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("validateButton")));
    driver.findElement(By.id("validateButton")).click();
    wait.until(ExpectedConditions
        .visibilityOfElementLocated(By.xpath("//input[@type='submit' and @value='LOGIN']")));
    return driver.getCurrentUrl();
  }

}
