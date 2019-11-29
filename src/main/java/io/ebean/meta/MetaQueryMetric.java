package io.ebean.meta;

/**
 * Query execution metrics.
 */
public interface MetaQueryMetric extends MetaTimedMetric {

  /**
   * The type of entity or DTO bean.
   */
  Class<?> getType();

  /**
   * The label for the query (can be null).
   */
  String getLabel();

  /**
   * The actual SQL of the query.
   */
  String getSql();

  /**
   * Return the hash of the plan.
   */
  String getHash();

  /**
   * Return true if this is the first metrics collection for this query.
   * <p>
   * This can be used to suppress including the SQL and location from metrics
   * content.
   * </p>
   */
  boolean initialCollection();
}
