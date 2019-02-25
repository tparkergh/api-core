package gov.ca.cwds.data.persistence.cms;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import gov.ca.cwds.data.std.ApiMarker;
import gov.ca.cwds.rest.services.ServiceException;

/**
 * Represents a CMS system code entry.
 * 
 * <p>
 * Optionally construct by parsing a delimited String of system codes column via method
 * {@link #produce(String, String)}.
 * </p>
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("squid:S00107")
public class CmsSystemCode implements ApiMarker {

  private static final long serialVersionUID = 1L;

  private final int sysId;
  private final String fksMetaT;
  private final String shortDsc;
  private final String logicalId;
  private final String inactive;
  private final String categoryId;
  private final String otherCd;
  private final String longDsc;

  /**
   * Construct from field values.
   * 
   * @param sysId unique system code id
   * @param fksMetaT system code category
   * @param shortDsc short description (e.g., "California")
   * @param logicalId logical id. Often zero-padded sort order (e.g., "0002") or standard code
   *        ("CA")
   * @param inactive inactive flag (N or Y)
   * @param categoryId sub-category
   * @param otherCd optional, 2 character code, such as "CA" for the State of California.
   * @param longDsc long description. Only populated occasionally.
   */
  public CmsSystemCode(int sysId, String fksMetaT, String shortDsc, String logicalId,
      String inactive, String categoryId, String otherCd, String longDsc) {
    this.sysId = sysId;
    this.fksMetaT = fksMetaT;
    this.shortDsc = shortDsc;
    this.logicalId = logicalId;
    this.inactive = inactive;
    this.categoryId = categoryId;
    this.otherCd = otherCd;
    this.longDsc = longDsc;
  }

  /**
   * Produce a {@link CmsSystemCode} from a delimited String.
   * 
   * <p>
   * Expected file layout.
   * </p>
   * 
   * <p>
   * SYS_ID, FKS_META_T, SHORT_DSC, LGC_ID, INACTV_IND, CATEGORY_ID, OTHER_CD, LONG_DSC
   * </p>
   * 
   * @param line delimited system code line to parse
   * @param delim chose delimiter
   * @return prepared CmsSystemCode
   * @throws ServiceException if line is blank
   */
  public static CmsSystemCode produce(final String line, final String delim)
      throws ServiceException {

    if (StringUtils.isBlank(line)) {
      throw new ServiceException("System code line cannot be empty");
    }

    int sysId;
    String fksMetaT;
    String shortDsc;
    String lgcId;
    String inactvInd;
    String categoryId;
    String otherCd;
    String longDsc;

    final String[] tokens = line.split(delim);
    sysId = Integer.parseInt(tokens[0]);
    fksMetaT = tokens[1].trim();
    shortDsc = tokens[2].trim();
    lgcId = tokens[3].trim();
    inactvInd = tokens[4].trim();
    categoryId = tokens[5].trim();
    otherCd = tokens[6].trim();
    longDsc = tokens[7].trim();

    return new CmsSystemCode(sysId, fksMetaT, shortDsc, lgcId, inactvInd, categoryId, otherCd,
        longDsc);
  }

  /**
   * See method {@link #produce(String, String)}. Delimiter defaults to tab.
   * 
   * @param line delimited system code line to parse
   * @return prepared CmsSystemCode
   * @throws ServiceException if line is blank
   */
  public static CmsSystemCode produce(final String line) throws ServiceException {
    return produce(line, "\\t");
  }

  /**
   * Getter for system code id.
   * 
   * @return system code id
   */
  public int getSysId() {
    return sysId;
  }

  /**
   * Getter for "meta" (system code category).
   * 
   * @return system code category
   */
  public String getFksMetaT() {
    return fksMetaT;
  }

  /**
   * Getter for short description, "California" instead of "CA".
   * 
   * @return short description
   */
  public String getShortDsc() {
    return shortDsc;
  }

  /**
   * Getter for logical id, the zero-padded sort order (e.g., "0001").
   * 
   * @return logical id
   */
  public String getLgcId() {
    return logicalId;
  }

  /**
   * Getter for inactive flag.
   * 
   * @return inactive flag
   */
  public String getInactvInd() {
    return inactive;
  }

  /**
   * Getter for the system code category.
   * 
   * @return the system code category
   */
  public String getCategoryId() {
    return categoryId;
  }

  /**
   * Getter for "other code", an optional short code for some categories.
   * 
   * @return other code
   */
  public String getOtherCd() {
    return otherCd;
  }

  /**
   * Getter for long description.
   * 
   * @return long description
   */
  public String getLongDsc() {
    return longDsc;
  }

  @Override
  public final int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  @Override
  public final boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

}
