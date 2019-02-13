package gov.ca.cwds.logging;

/**
 * Interface for audit logger
 * 
 * @author CWDS API Team
 */
@FunctionalInterface
public interface AuditLogger {

  /**
   * Writes given message to the audit log with the given event type
   * 
   * @param data The data to audit
   *
   */
  public void audit(String data);
}
