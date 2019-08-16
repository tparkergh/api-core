package gov.ca.cwds.tracelog.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import gov.ca.cwds.data.persistence.PersistentObject;

@Entity
@Table(name = "query_logs")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TraceLogSearchQueryLog implements PersistentObject, Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "query_logs_id_seq")
  @SequenceGenerator(name = "query_logs_id_seq", sequenceName = "query_logs_id_seq",
      allocationSize = 50)
  @Column(name = "ID")
  private Long id;

  @NotNull
  @Column(name = "\"user\"")
  private String user;

  @NotNull
  @Column(name = "\"timestamp\"")
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

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
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
