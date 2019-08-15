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
@Table(name = "query_logs")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TraceLogSearchQueryLog extends CmsPersistentObject {

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
  @Column(name = "TERM")
  private String term;

  @NotNull
  @Column(name = "VALUE")
  private String value;

  public TraceLogSearchQueryLog(String user, Timestamp ts, String term, String value) {
    super();
    this.user = user;
    this.ts = ts;
    this.term = term;
    this.value = value;
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

  public String getTerm() {
    return term;
  }

  public void setTerm(String term) {
    this.term = term;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public Timestamp getTs() {
    return ts;
  }

  public void setTs(Timestamp ts) {
    this.ts = ts;
  }

}
