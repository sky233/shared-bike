package com.believe.bike.query.user;

import com.believe.bike.core.model.Domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;

/**
 * <p> The describe </p>
 *
 * @author Li Xingping
 */
@Data
@Entity
@Table(name = "users")
@EqualsAndHashCode(callSuper = true, exclude = {"account"})
@ToString(callSuper = true, exclude = {"account"})
public class UserEntry extends Domain<String> {

  @Column(name = "real_name")
  private String realName;

  @NotBlank
  @Column(unique = true, nullable = false)
  private String cellNo;

  @JsonIgnore
  @Column
  private String password;

  @Column(name = "paid_deposit")
  private boolean paidDeposit;

  @JsonIgnore
  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
  private AccountEntry account;

}
