package com.believe.bike.query.admin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
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
public class Admin {

  @Id
  @GeneratedValue
  private Long id;

  @NotNull
  @Column(name = "created_date", nullable = false, updatable = false)
  private Instant createdDate;

  @NotBlank
  @Column(unique = true, nullable = false)
  private String username;

  @JsonIgnore
  @Column
  private String password;
}
