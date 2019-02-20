package gov.ca.cwds.authorizer;

import static gov.ca.cwds.authorizer.util.StaffPrivilegeUtil.toStaffPersonPrivilegeTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.authorizer.drools.DroolsAuthorizationService;
import gov.ca.cwds.authorizer.drools.configuration.DroolsAuthorizer;
import gov.ca.cwds.security.authorizer.BaseAuthorizer;
import gov.ca.cwds.security.realm.PerryAccount;
import gov.ca.cwds.security.realm.PerrySubject;

/**
 * Abstract, Drools-aware implementation of Base Authorizer.
 *
 * @param <T> class type
 * @param <I> identifier type
 * @author CWDS TPT-2 Team
 */
public abstract class AbstractBaseAuthorizer<T, I> extends BaseAuthorizer<T, I> {

  private static final Logger LOGGER = LoggerFactory.getLogger(AbstractBaseAuthorizer.class);

  private DroolsAuthorizationService droolsAuthorizationService;

  private DroolsAuthorizer droolsConfiguration;

  /**
   * Constructor.
   *
   * @param droolsAuthorizationService Authorization service
   * @param droolsConfiguration        Drools configuration
   */
  public AbstractBaseAuthorizer(DroolsAuthorizationService droolsAuthorizationService,
                                DroolsAuthorizer droolsConfiguration) {
    this.droolsAuthorizationService = droolsAuthorizationService;
    this.droolsConfiguration = droolsConfiguration;
  }

  private void logAuthorization(final PerryAccount perryAccount,
                                final Set<StaffPrivilegeType> staffPrivilegeTypes, final T instance,
                                final boolean authorizationResult) {
    String instanceName =
      Optional.ofNullable(instance).map(t -> t.getClass().getSimpleName()).orElse(null);
    String message = perryAccount.toString().replaceAll("\n", " ").replaceAll("\r", "");
    LOGGER.info(
      "Authorization: StaffPerson [{}] with staffPrivilegeTypes = {} is performing action on object [{}]. "
        + "Authorization result = [{}]. {}",
      perryAccount.getStaffId(), staffPrivilegeTypes, instanceName, authorizationResult,
      message);
  }

  protected boolean authorizeInstanceOperation(final T instance, List<Object> authorizationFacts) {
    final PerryAccount perryAccount = PerrySubject.getPerryAccount();
    final Set<StaffPrivilegeType> staffPrivilegeTypes = toStaffPersonPrivilegeTypes(perryAccount);
    if (staffPrivilegeTypes.isEmpty()) {
      String message = perryAccount.toString().replaceAll("\n", " ").replaceAll("\r", "");
      LOGGER.info(
        "Authorization: staff person has no privileges. Authorization result = [{}]. {}",
        Boolean.FALSE, message);
      return false;
    }
    if (authorizationFacts == null) {
      authorizationFacts = new ArrayList<>();
    }
    authorizationFacts.add(instance);
    authorizationFacts.add(perryAccount);
    final boolean authorizationResult = droolsAuthorizationService
      .authorizeObjectOperation(staffPrivilegeTypes, droolsConfiguration, authorizationFacts);
    logAuthorization(perryAccount, staffPrivilegeTypes, instance, authorizationResult);
    return authorizationResult;
  }

  @Override
  protected boolean checkInstance(T instance) {
    if (instance == null) {
      return true;
    }
    return authorizeInstanceOperation(instance, prepareFacts(instance));
  }

  protected abstract List<Object> prepareFacts(T instance);
}
