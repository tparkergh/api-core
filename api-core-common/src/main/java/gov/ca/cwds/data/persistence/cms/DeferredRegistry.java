package gov.ca.cwds.data.persistence.cms;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import gov.ca.cwds.data.std.ApiMarker;
import gov.ca.cwds.inject.SystemCodeCache;

/**
 * Store singleton, typed instances for easy retrieval. Useful where dependency injection into
 * short-lived beans is overly complicated or unwieldy.
 * 
 * <h3>Usage:</h3>
 * 
 * <p>
 * See {@link SystemCodeCache} for usage. An implementation calls {@code SystemCodeCache.register()}
 * from its constructor to register a singleton instance, and users call
 * {@code SystemCodeCache.global()} to retrieve the singleton instance.
 * </p>
 * 
 * <h3>Note on static generics:</h3>
 * 
 * <p>
 * Java Generics are <strong>NOT</strong> C++ templates. That is, Java does not compile a class type
 * into byte code with its own static variables, but rather, generic classes <strong>share</strong>
 * static variables among all type instances. In other words, static members lack type information,
 * since types are only carried at the class instance level. Naughty Java!
 * </p>
 * 
 * <p>
 * To paraphrase Java's documentation, static variables and methods are <strong>shared</strong>
 * among ALL class instances. For example, {@code MyClass<T>} contains static variable
 * {@code private static T shared}. Unfortunately, {@code MyClass<ABC>} would <strong>share</strong>
 * that static variable with {@code MyClass<XYZ>}. Question: which static type would win, ABC or
 * XYZ?
 * </p>
 * 
 * <p>
 * Therefore, to prevent confusion, static variables cannot follow an instance type because ALL
 * instances would refer to the same static type.
 * </p>
 * 
 * @param <T> CWDS type to wrap
 * @author CWDS API Team
 * @see SystemCodeCache
 */
@SuppressWarnings({"fb-contrib:PMB_POSSIBLE_MEMORY_BLOAT"})
public class DeferredRegistry<T extends ApiMarker> implements ApiMarker {

  private static final long serialVersionUID = 1L;

  /**
   * Store deferred registry entries by type.
   */
  private static Map<Class<?>, DeferredRegistry<ApiMarker>> registry = new ConcurrentHashMap<>();

  private final T wrapped;
  private final Class<T> klass;

  /**
   * Construct from class type and instance. Since Java loses abandons type information at runtime,
   * a generic instance of T is insufficient.
   * 
   * @param klass class type to wrap
   * @param t instance to wrap
   */
  protected DeferredRegistry(Class<T> klass, T t) {
    this.wrapped = t;
    this.klass = klass;
  }

  /**
   * Construct from class type and instance. Since Java loses abandons type information at runtime,
   * a generic instance of T is insufficient.
   * 
   * @param klass class type to wrap
   * @param t instance to wrap
   * @param overwrite overwrite registered instance
   */
  @SuppressWarnings("unchecked")
  public DeferredRegistry(Class<T> klass, T t, boolean overwrite) {
    this.wrapped = t;
    this.klass = klass;

    if (overwrite || !registry.containsKey(klass)) {
      registry.put(klass, (DeferredRegistry<ApiMarker>) this);
    }
  }

  /**
   * Register an instance. Allows overwrite of previously registered instances.
   * 
   * @param <T> type to wrap
   * @param klass class
   * @param t serializable type
   * @param overwrite overwrite if already registered
   */
  @SuppressWarnings("unchecked")
  public static final <T extends ApiMarker> void register(Class<T> klass, T t, boolean overwrite) {
    if (overwrite || !registry.containsKey(klass)) {
      registry.put(klass, (DeferredRegistry<ApiMarker>) new DeferredRegistry<>(klass, t));
    }
  }

  /**
   * Register this instance. Do not overwrite a registered instance, if the same type has already
   * been registered.
   * 
   * @param <T> type to wrap
   * @param klass class
   * @param t serializable type
   */
  public static final <T extends ApiMarker> void register(Class<T> klass, T t) {
    register(klass, t, false);
  }

  /**
   * Unwrap the underlying of the given type.
   * 
   * @param <T> type to wrap
   * @param klass class
   * @return the underlying
   */
  @SuppressWarnings("unchecked")
  public static final <T extends ApiMarker> T unwrap(Class<?> klass) {
    final DeferredRegistry<T> reg = (DeferredRegistry<T>) registry.get(klass);
    return reg != null ? reg.wrapped : null;
  }

  public Class<T> getKlass() {
    return klass;
  }

}
