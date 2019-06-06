package gov.ca.cwds.rest.mapping;

/**
 * CWDS J Team
 */
public interface Mapper<S, T> {
  T map(S source);
}
