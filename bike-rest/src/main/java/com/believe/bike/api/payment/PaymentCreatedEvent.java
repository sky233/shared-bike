package com.believe.bike.api.payment;

import com.believe.bike.api.transaction.TransactionId;
import com.believe.bike.api.user.UserId;
import lombok.Value;

import java.math.BigDecimal;

/**
 * @author Li Xingping
 */
@Value
public class PaymentCreatedEvent {

  private PaymentId identifier;
  private TransactionId transactionId;
  private UserId userId;
  private String tradeNo;
  private BigDecimal amount;
  private String remark;
  private PaymentChannel paymentChannel;
  private PaymentStatus status;

}
