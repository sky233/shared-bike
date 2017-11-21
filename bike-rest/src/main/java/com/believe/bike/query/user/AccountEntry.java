package com.believe.bike.query.user;

import com.believe.bike.core.model.PersistenceModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * <p> The describe </p>
 *
 * @author Li Xingping
 */
@Data
@Entity
@Table(name = "user_account")
@EqualsAndHashCode(callSuper = true, exclude = {"user"})
@ToString(callSuper = true, exclude = {"user"})
public class AccountEntry extends PersistenceModel {

  @Column
  private boolean active = true;

  @Column
  private BigDecimal frozen = BigDecimal.ZERO;

  @Column
  private BigDecimal balance = BigDecimal.ZERO;

  @NotNull
  @JsonIgnore
  @OneToOne
  @JoinColumn(name = "user_id", nullable = false)
  private UserEntry user;

  void frozen(BigDecimal frozenAmount) {
    this.frozen = this.frozen.add(frozenAmount);
  }

  void thaw(BigDecimal amount) {
    this.frozen = this.frozen.subtract(amount);
  }

  void increase(BigDecimal amount) {
    this.balance = this.balance.add(amount);
  }

  void decrease(BigDecimal amount) {
    this.balance = this.balance.subtract(amount);
  }

}
