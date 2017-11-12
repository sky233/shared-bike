package com.believe.bike.query.user;

import com.believe.bike.core.model.Domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * <p> The describe </p>
 *
 * @author Li Xingping
 */
@Data
@Entity
@Table(name = "users")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserEntry extends Domain<String> {

  @NotBlank
  @Column(unique = true, nullable = false)
  private String cellNo;

  @JsonIgnore
  @Column
  private String password;


}
