package com.believe.bike.api.payment;

import com.believe.bike.api.transaction.TransactionId;
import com.believe.bike.api.user.UserId;
import lombok.Value;

import java.math.BigDecimal;

/**
 * @author Li Xingping
 */
@Value
public class PlatformPaySuccessfulEvent {

  private PaymentId paymentId;
  private TransactionId transactionId;
  private UserId userId;

  private String trade_no;
  private BigDecimal total_amount;

}
