package com.believe.bike.command.user;

import lombok.Data;

import java.math.BigDecimal;

/**
 * <p> The describe </p>
 *
 * @author Li Xingping
 */
@Data
class UserAccount {

  private boolean active;
  private BigDecimal frozen;
  private BigDecimal balance;

  UserAccount() {
    this.active = true;
    this.frozen = BigDecimal.ZERO;
    this.balance = BigDecimal.ZERO;
  }

  void frozen(BigDecimal amount) {
    this.frozen = this.frozen.add(amount);
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
