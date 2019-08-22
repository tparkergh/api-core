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
@Table(name = "client_view_logs")
@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TraceLogClientViewEntry implements PersistentObject, Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_view_logs_id_seq")
  @SequenceGenerator(name = "client_view_logs_id_seq", sequenceName = "client_view_logs_id_seq",
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
  @Column(name = "RECORD_ID")
  private String recordId;

  @NotNull
  @Column(name = "RECORD_TYPE")
  private String recordType;

  public TraceLogClientViewEntry(String user, Timestamp ts, String recordId, String recordType) {
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
