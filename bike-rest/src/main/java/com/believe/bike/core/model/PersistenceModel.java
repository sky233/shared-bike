package com.believe.bike.core.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;

/**
 * <p> The describe </p>
 *
 * @author Li Xingping
 */
@MappedSuperclass
@Data
public abstract class PersistenceModel implements Serializable {

  @Id
  @Column(name = "id")
  @GeneratedValue(generator = "custom-uuid")
  @GenericGenerator(name = "custom-uuid", strategy = "com.believe.bike.core.help.CustomUUIDGenerator")
  private String id;

  @NotNull
  @Column(name = "created_date", nullable = false, updatable = false)
  private Instant createdDate;

}
