package gov.ca.cwds.tracelog.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import gov.ca.cwds.data.legacy.cms.CmsPersistentObject;

@Entity
@Table(name = "client_view_logs")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TraceLogClientViewLog extends CmsPersistentObject {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "ID")
  private String id;

  @NotNull
  @Column(name = "USER")
  private String user;

  @NotNull
  @Column(name = "TIMESTAMP")
  private Timestamp ts;

  @NotNull
  @Column(name = "RECORD_ID")
  private String recordId;

  @NotNull
  @Column(name = "RECORD_TYPE")
  private String recordType;

  public TraceLogClientViewLog(String user, Timestamp ts, String recordId, String recordType) {
    super();
    this.user = user;
    this.ts = ts;
    this.recordId = recordId;
    this.recordType = recordType;
  }

  @Override
  public Serializable getPrimaryKey() {
    return id;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public String getRecordId() {
    return recordId;
  }

  public void setRecordId(String recordId) {
    this.recordId = recordId;
  }

  public String getRecordType() {
    return recordType;
  }

  public void setRecordType(String recordType) {
    this.recordType = recordType;
  }

  public Timestamp getTs() {
    return ts;
  }

  public void setTs(Timestamp ts) {
    this.ts = ts;
  }

}
