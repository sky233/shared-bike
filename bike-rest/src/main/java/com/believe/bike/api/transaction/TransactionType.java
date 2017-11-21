package com.believe.bike.api.transaction;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p> The describe </p>
 *
 * @author Li Xingping
 */
@Getter
@AllArgsConstructor
public enum TransactionType {

  PAY_DEPOSIT("com.believe.bike.transactionType.PayDeposit"),
  WITHDRAW_DEPOSIT("com.believe.bike.transactionType.WithdrawDeposit"),
  RECHARGE("com.believe.bike.transactionType.Recharge"),
  WITHDRAW("com.believe.bike.transactionType.Withdraw");

  private String code;
}
