package com.believe.bike.query.admin;

import com.believe.bike.core.model.PersistenceModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;

/**
 * <p> The describe </p>
 *
 * @author Li Xingping
 */
@Data
@Entity
@Table(name = "admin")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Admin extends PersistenceModel {

  @NotBlank
  @Column(unique = true, nullable = false)
  private String username;

  @JsonIgnore
  @Column
  private String password;
}
