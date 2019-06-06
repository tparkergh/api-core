package gov.ca.cwds.data;

import java.io.Serializable;

/**
 * Interface marks the unique key type of a persistence class.
 * 
 * <p>
 * Legacy DB2 mostly relies on String keys (char(3) staff id + char(7) base-62 timestamp.
 * </p>
 * 
 * <p>
 * New PostgreSQL mostly relies on numeric keys, notably Long.
 * </p>
 * 
 * @author CWDS API Team
 * @param <T> the unique key type
 */
public interface ApiTypedIdentifier<T extends Serializable> extends Serializable {

  /**
   * @return the unique key
   */
  T getId();

  /**
   * Set the unique key.
   * 
   * @param id the unique key
   */
  void setId(T id);

}
