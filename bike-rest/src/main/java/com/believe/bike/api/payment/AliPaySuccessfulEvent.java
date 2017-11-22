package com.believe.bike.api.payment;

import com.believe.bike.api.transaction.TransactionId;
import com.believe.bike.api.user.UserId;
import lombok.Value;

import java.math.BigDecimal;

/**
 * <p> https://docs.open.alipay.com/204/105301/ </p>
 *
 * @author Li Xingping
 */
@Value
public class AliPaySuccessfulEvent {

  private PaymentId paymentId;
  private TransactionId transactionId;
  private UserId userId;

  /**
   * Ali pay return.
   */
  private String notify_type;
  private String trade_no;
  private String out_trade_no;
  private BigDecimal total_amount;

}
