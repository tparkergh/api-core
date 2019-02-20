package gov.ca.cwds.data.persistence.cms;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import gov.ca.cwds.data.CmsSystemCodeDeserializer;
import gov.ca.cwds.data.ReadablePhone;
import gov.ca.cwds.data.SystemCodeSerializer;
import gov.ca.cwds.data.std.ApiAddressAware;
import gov.ca.cwds.data.std.ApiAddressUtils;
import gov.ca.cwds.data.std.ApiMultiplePhonesAware;
import gov.ca.cwds.data.std.ApiPhoneAware;

/**
 * @author CWDS API Team
 */
@MappedSuperclass
public abstract class BaseAddress extends CmsPersistentObject
    implements ApiAddressAware, ApiMultiplePhonesAware {

  private static final long serialVersionUID = 1L;

  // ====================
  // COLUMNS:
  // ====================

  @Id
  @Column(name = "IDENTIFIER", nullable = false, length = CMS_ID_LEN)
  protected String id;

  @Column(name = "CITY_NM", nullable = false)
  @ColumnTransformer(read = "trim(CITY_NM)")
  protected String city;

  @Column(name = "EMRG_TELNO", nullable = false)
  protected Long emergencyNumber;

  @Type(type = "integer")
  @Column(name = "EMRG_EXTNO", nullable = false)
  protected Integer emergencyExtension;

  @Column(name = "FRG_ADRT_B", nullable = false)
  protected String frgAdrtB;

  @SystemCodeSerializer(logical = true, description = true)
  @JsonDeserialize(using = CmsSystemCodeDeserializer.class)
  @Type(type = "short")
  @Column(name = "GVR_ENTC", nullable = false)
  protected Short governmentEntityCd;

  @Column(name = "MSG_TEL_NO", nullable = false)
  protected Long messageNumber;

  @Type(type = "integer")
  @Column(name = "MSG_EXT_NO", nullable = false)
  protected Integer messageExtension;

  @Column(name = "HEADER_ADR", nullable = false)
  @ColumnTransformer(read = "trim(HEADER_ADR)")
  protected String headerAddress;

  @Column(name = "PRM_TEL_NO", nullable = false)
  protected Long primaryNumber;

  @Type(type = "integer")
  @Column(name = "PRM_EXT_NO", nullable = false)
  protected Integer primaryExtension;

  @SystemCodeSerializer(logical = true, description = true)
  @JsonDeserialize(using = CmsSystemCodeDeserializer.class)
  @Type(type = "short")
  @Column(name = "STATE_C", nullable = false)
  protected Short state;

  @Column(name = "STREET_NM", nullable = false)
  @ColumnTransformer(read = "trim(STREET_NM)")
  protected String streetName;

  @Column(name = "STREET_NO", nullable = false)
  @ColumnTransformer(read = "trim(STREET_NO)")
  protected String streetNumber;

  // TODO: #143810605 legacy database stores zip code as an Integer.
  // ApiAddressAware interface requires a String type
  @Column(name = "ZIP_NO", nullable = false)
  @ColumnTransformer(read = "trim(ZIP_NO)")
  protected String zip;

  @Column(name = "ADDR_DSC", nullable = false)
  @ColumnTransformer(read = "trim(ADDR_DSC)")
  protected String addressDescription;

  @Type(type = "short")
  @Column(name = "ZIP_SFX_NO", nullable = false)
  protected Short zip4;

  @Column(name = "POSTDIR_CD", nullable = false)
  @ColumnTransformer(read = "trim(POSTDIR_CD)")
  protected String postDirCd;

  @Column(name = "PREDIR_CD", nullable = false)
  @ColumnTransformer(read = "trim(PREDIR_CD)")
  protected String preDirCd;

  @SystemCodeSerializer(logical = true, description = true)
  @JsonDeserialize(using = CmsSystemCodeDeserializer.class)
  @Type(type = "short")
  @Column(name = "ST_SFX_C", nullable = false)
  protected Short streetSuffixCd;

  @SystemCodeSerializer(logical = true, description = true)
  @JsonDeserialize(using = CmsSystemCodeDeserializer.class)
  @Type(type = "short")
  @Column(name = "UNT_DSGC", nullable = false)
  protected Short unitDesignationCd;

  @Column(name = "UNIT_NO", nullable = false)
  @ColumnTransformer(read = "trim(UNIT_NO)")
  protected String unitNumber;

  // ====================
  // CONTEXT:
  // ====================

  /**
   * The type of address from the perspective of the owning client or other entity.
   */
  protected transient Short contextAddressType;

  // ====================
  // CONSTRUCTORS:
  // ====================

  /**
   * Default constructor.
   */
  public BaseAddress() {
    super();
  }

  /**
   * @param lastUpdateId user id of last update
   */
  public BaseAddress(String lastUpdateId) {
    super(lastUpdateId);
  }

  /**
   * @param lastUpdateId user id of last update
   * @param lastUpdatedTime time for the last update
   */
  public BaseAddress(String lastUpdateId, Date lastUpdatedTime) {
    super(lastUpdateId, lastUpdatedTime);
  }

  // ==================
  // IDENTIFIERS:
  // ==================

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.data.persistence.PersistentObject#getPrimaryKey()
   */
  @Override
  public String getPrimaryKey() {
    return getId();
  }

  // ==================
  // ACCESSORS:
  // ==================

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Override
  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public Long getEmergencyNumber() {
    return emergencyNumber;
  }

  public void setEmergencyNumber(Long emergencyNumber) {
    this.emergencyNumber = emergencyNumber;
  }

  public Integer getEmergencyExtension() {
    return emergencyExtension;
  }

  public void setEmergencyExtension(Integer emergencyExtension) {
    this.emergencyExtension = emergencyExtension;
  }

  public Long getMessageNumber() {
    return messageNumber;
  }

  public void setMessageNumber(Long messageNumber) {
    this.messageNumber = messageNumber;
  }

  public Integer getMessageExtension() {
    return messageExtension;
  }

  public void setMessageExtension(Integer messageExtension) {
    this.messageExtension = messageExtension;
  }

  public String getHeaderAddress() {
    return headerAddress;
  }

  public void setHeaderAddress(String headerAddress) {
    this.headerAddress = headerAddress;
  }

  public Long getPrimaryNumber() {
    return primaryNumber;
  }

  public void setPrimaryNumber(Long primaryNumber) {
    this.primaryNumber = primaryNumber;
  }

  public Integer getPrimaryExtension() {
    return primaryExtension;
  }

  public void setPrimaryExtension(Integer primaryExtension) {
    this.primaryExtension = primaryExtension;
  }

  @JsonIgnore
  @Override
  public String getState() {
    return this.state != null ? this.state.toString() : null;
  }

  @Override
  public Short getStateCd() {
    return state;
  }

  public void setStateCd(Short state) {
  }

  @Override
  public String getStreetName() {
    return streetName;
  }

  public void setStreetName(String streetName) {
    this.streetName = streetName;
  }

  @Override
  public String getStreetNumber() {
    return streetNumber;
  }

  public void setStreetNumber(String streetNumber) {
    this.streetNumber = streetNumber;
  }

  @Override
  public String getZip() {
    return zip;
  }

  public void setZip(String zip) {
    this.zip = zip;
  }

  public String getAddressDescription() {
    return addressDescription;
  }

  public void setAddressDescription(String addressDescription) {
    this.addressDescription = addressDescription;
  }

  public Short getZip4() {
    return zip4;
  }

  public void setZip4(Short zip4) {
    this.zip4 = zip4;
  }

  public String getPostDirCd() {
    return postDirCd;
  }

  public void setPostDirCd(String postDirCd) {
    this.postDirCd = postDirCd;
  }

  public String getPreDirCd() {
    return preDirCd;
  }

  public void setPreDirCd(String preDirCd) {
    this.preDirCd = preDirCd;
  }

  public Short getStreetSuffixCd() {
    return streetSuffixCd;
  }

  public void setStreetSuffixCd(Short streetSuffixCd) {
    this.streetSuffixCd = streetSuffixCd;
  }

  public Short getUnitDesignationCd() {
    return unitDesignationCd;
  }

  public void setUnitDesignationCd(Short unitDesignationCd) {
    this.unitDesignationCd = unitDesignationCd;
  }

  public String getUnitNumber() {
    return unitNumber;
  }

  public void setUnitNumber(String unitNumber) {
    this.unitNumber = unitNumber;
  }

  public Short getGovernmentEntityCd() {
    return governmentEntityCd;
  }

  public void setGovernmentEntityCd(Short governmentEntityCd) {
    this.governmentEntityCd = governmentEntityCd;
  }

  public String getFrgAdrtB() {
    return frgAdrtB;
  }

  public void setFrgAdrtB(String frgAdrtB) {
    this.frgAdrtB = frgAdrtB;
  }

  public void setState(Short state) {
    this.state = state;
  }

  // =======================
  // ApiMultiplePhonesAware:
  // =======================

  @Override
  public ApiPhoneAware[] getPhones() {
    final List<ApiPhoneAware> phones = new ArrayList<>();
    if (this.primaryNumber != null && this.primaryNumber != 0) {
      phones.add(new ReadablePhone(null, String.valueOf(this.primaryNumber),
          this.primaryExtension != null ? this.primaryExtension.toString() : null, null));
    }

    if (this.messageNumber != null && this.messageNumber != 0) {
      phones.add(new ReadablePhone(null, String.valueOf(this.messageNumber),
          this.messageExtension != null ? this.messageExtension.toString() : null,
          ApiPhoneAware.PhoneType.Cell));
    }

    if (this.emergencyNumber != null && this.emergencyNumber != 0) {
      phones.add(new ReadablePhone(null, String.valueOf(this.emergencyNumber),
          this.emergencyExtension != null ? this.emergencyExtension.toString() : null,
          ApiPhoneAware.PhoneType.Other));
    }

    return phones.toArray(new ApiPhoneAware[0]);
  }

  @Override
  public String getStreetAddress() {
    final StringBuilder buf = new StringBuilder();
    if (StringUtils.isNoneBlank(this.streetNumber)) {
      buf.append(this.streetNumber).append(' ');
    }

    if (StringUtils.isNoneBlank(this.streetName)) {
      buf.append(this.streetName);
    }

    if (StringUtils.isNotBlank(this.unitNumber)) {
      buf.append(" #").append(this.unitNumber);
    }
    return buf.toString();
  }

  @Override
  public String getCounty() {
    return null;
  }

  @JsonIgnore
  @Override
  public String getAddressId() {
    return this.id;
  }

  @Override
  public String getApiAdrZip4() {
    return ApiAddressUtils.formatZip4(zip4);
  }

  @Override
  public String getApiAdrUnitNumber() {
    return this.unitNumber;
  }

  /**
   * NOTE: Parent class, {@link BaseClientAddress}, holds the address type.
   * 
   * <p>
   * In the legacy CMS data model, addresses are assigned one or more uses as a "type", depending on
   * the relation. For example, a unique address could serve as someone's primary residence for a
   * time or as a child's foster home for a different time period. It depends on a client's
   * perspective, not the physical address itself.
   * </p>
   * 
   * @see #getContextAddressType()
   */
  @Override
  public Short getApiAdrAddressType() {
    return null;
  }

  @Override
  public Short getApiAdrUnitType() {
    return this.unitDesignationCd;
  }

  public Short getContextAddressType() {
    return contextAddressType;
  }

  public void setContextAddressType(Short contextAddressType) {
    this.contextAddressType = contextAddressType;
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

}
