package gov.ca.cwds.data;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Find annoying bugs -- especially those nasty ones planted by your co-workers.
 * 
 * @author CWDS API Team
 */
public class CaresStackUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(CaresStackUtils.class);

  private CaresStackUtils() {
    // private, static methods only
  }

  public static void logStack() {
    logStack(LOGGER);
  }

  public static void logStack(Logger logger) {
    if (logger.isTraceEnabled()) {
      try {
        final StackTraceElement[] stack = getStackTrace();
        Arrays.stream(stack, 0, stack.length - 1).forEach(x -> logger.trace("\t{}", x));
      } catch (Throwable e) {
        logger.error("FAILED TO LOG STACK! ", e); // how ironic
        throw e;
      }
    }
  }

  public static String stackToString() {
    return stackToString("\n");
  }

  public static String stackToString(String delim) {
    StringBuilder buf = new StringBuilder();
    try {
      final StackTraceElement[] stack = getStackTrace();
      Arrays.stream(stack, 0, stack.length - 1).forEach(e -> buf.append(delim).append(e));
    } catch (Throwable e) {
      LOGGER.error("ERROR LOGGING EXCEPTION: {}", e.getMessage(), e);
      throw e;
    }

    return buf.toString();
  }

  public static StackTraceElement[] getStackTrace() {
    try {
      final StackTraceElement[] stack = Thread.currentThread().getStackTrace();
      return Arrays.stream(stack, 0, stack.length - 1)
          .filter(e -> e.getClassName().startsWith("gov.ca.cwds")
              && !e.getClassName().startsWith("gov.ca.cwds.rest.filters")
              && !e.getClassName().contains("$$"))
          .collect(Collectors.toList()).toArray(new StackTraceElement[0]);
    } catch (Throwable e) {
      LOGGER.error("ERROR LOGGING EXCEPTION: {}", e.getMessage(), e);
      throw e;
    }
  }

}
