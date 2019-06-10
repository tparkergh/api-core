package gov.ca.cwds.data.es;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Date;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Enumeration of ES query types by String format. Builds appropriate ES query objects via
 * #{@link ElasticSearchQuery#buildQuery(BoolQueryBuilder, String)}.
 * 
 * <p>
 * Each enumerated type can match an appropriate String format for suitability.
 * </p>
 * 
 * @author CWDS API Team
 */
public enum ElasticSearchQuery {

  /**
   * Name
   */
  NAME {

    @Override
    boolean match(String term) {
      return term.matches("[a-z]+");
    }

    @Override
    BoolQueryBuilder buildQuery(BoolQueryBuilder queryBuilder, String term) {
      queryBuilder.should(
          QueryBuilders.prefixQuery(ElasticSearchPerson.ESColumn.FIRST_NAME.getCol(), term));
      queryBuilder
          .should(QueryBuilders.prefixQuery(ElasticSearchPerson.ESColumn.LAST_NAME.getCol(), term));
      return queryBuilder;
    }

  },

  /**
   * Social Security Number and Birth Year
   */
  SSN {

    @Override
    boolean match(String term) {
      return term.matches("\\d{0,9}") || term.matches("\\d{3}-\\d{2}-\\d{4}");
    }

    @Override
    BoolQueryBuilder buildQuery(BoolQueryBuilder queryBuilder, String term) {
      queryBuilder.should(QueryBuilders.prefixQuery("ssn", term.replace("-", "")));
      if (term.length() == 4) {
        String startDate = term + "-01-01";
        String endDate = term + "-12-31";
        queryBuilder.should(QueryBuilders.rangeQuery(DATE_OF_BIRTH).gte(startDate).lte(endDate));
      }
      return queryBuilder;
    }

  },

  /**
   * Date Of Birth
   */
  DOB {

    @Override
    boolean match(String term) {
      return term.matches("[0-9-/]+") && !term.matches("\\d{3}-\\d{2}-\\d{4}")
          && !term.matches("\\d{5,}") || term.matches("\\{1998}");
    }

    @Override
    BoolQueryBuilder buildQuery(BoolQueryBuilder queryBuilder, String term) {
      String formattedDate = term;
      if (formattedDate.contains("/")) {
        try {
          DateFormat df = new SimpleDateFormat("mm/dd/yyyy");
          Date dob = df.parse(formattedDate);
          if (dob != null) {
            DateFormat esdf = new SimpleDateFormat("yyyy-mm-dd");
            esdf.setLenient(false);
            formattedDate = esdf.format(dob);
          }
        } catch (ParseException e) {
          LOGGER.warn("ElasticSearchQuery: unable to parse birth date {}", term);
          return queryBuilder;
        }
      }

      DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
      formatter = formatter.withResolverStyle(ResolverStyle.STRICT);
      LocalDate dateOfBirth = LocalDate.parse(formattedDate, formatter);
      formattedDate = dateOfBirth.format(formatter);
      queryBuilder.should(QueryBuilders.matchQuery(DATE_OF_BIRTH, formattedDate));
      return queryBuilder;
    }

  },

  /**
   * UNKNOWN
   */
  UNKNOWN {

    @Override
    boolean match(String term) {
      return true;
    }

    @Override
    BoolQueryBuilder buildQuery(BoolQueryBuilder queryBuilder, String term) {
      return queryBuilder;
    }

  };

  private static final String DATE_OF_BIRTH = ElasticSearchPerson.ESColumn.BIRTH_DATE.getCol();

  private static final Logger LOGGER = LoggerFactory.getLogger(ElasticSearchQuery.class);

  /**
   * Match the format of the given String to the first matching ElasticSearchQuery type.
   * 
   * @param term String term to evaluate
   * @return ES search query instance
   */
  public static ElasticSearchQuery getQueryFromTerm(String term) {
    for (ElasticSearchQuery query : values()) {
      if (query.match(term)) {
        return query;
      }

    }
    return UNKNOWN;
  }

  /**
   * Evaluate whether this String format matches this type
   * 
   * @param term String to evaluate
   * @return if String format matches this type
   */
  abstract boolean match(String term);

  /**
   * Add a String term to boolean query builder.
   * 
   * @param queryBuilder ES boolean query builder
   * @param term String to evaluate
   * @return the same boolean query builder
   */
  abstract BoolQueryBuilder buildQuery(BoolQueryBuilder queryBuilder, String term);

}
