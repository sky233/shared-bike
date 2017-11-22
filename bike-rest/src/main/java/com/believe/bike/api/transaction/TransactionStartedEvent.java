package com.believe.bike.api.transaction;

import com.believe.bike.api.payment.PaymentChannel;
import com.believe.bike.api.user.UserId;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p> The describe </p>
 *
 * @author Li Xingping
 */
@Value
public class TransactionStartedEvent implements Serializable {

  private TransactionId identifier;
  private String tradeNo;
  private UserId userId;
  private TransactionType type;
  private TransactionStatus status;
  private BigDecimal amount;
  private String remark;
  private PaymentChannel paymentChannel;

}
