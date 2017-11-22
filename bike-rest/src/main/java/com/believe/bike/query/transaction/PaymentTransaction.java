package com.believe.bike.query.transaction;

import com.believe.bike.api.payment.PaymentChannel;
import com.believe.bike.api.payment.PaymentCreatedEvent;
import com.believe.bike.api.payment.PaymentStatus;
import com.believe.bike.core.model.Domain;
import com.believe.bike.query.user.UserEntry;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * <p> The describe </p>
 *
 * @author Li Xingping
 */
@NoArgsConstructor
@Data
@Entity
@Table(name = "payment_transaction")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PaymentTransaction extends Domain<String> {

  @JsonIgnore
  @ManyToOne
  @JoinColumn(nullable = false, name = "transactor_id")
  private UserEntry transactor;

  /**
   * 商户内部订单
   */
  @Column(nullable = false, unique = true, name = "trade_no")
  private String tradeNo;

  @Column(name = "out_trade_no")
  private String outTradeNo;

  @Enumerated(EnumType.STRING)
  private PaymentChannel channel;

  @Enumerated(EnumType.STRING)
  private PaymentStatus status;

  @Column(nullable = false)
  private BigDecimal amount;
  /**
   * 实际成交
   */
  @Column
  private BigDecimal totalAmount;
  @NotBlank
  private String transactionId;
  private String remark;

  public PaymentTransaction(PaymentCreatedEvent event, UserEntry transactor) {
    this.setId(event.getIdentifier().getIdentifier());
    this.transactor = transactor;
    this.tradeNo = event.getTradeNo();
    this.amount = event.getAmount();
    this.remark = event.getRemark();
    this.status = event.getStatus();
    this.channel = event.getPaymentChannel();
    this.transactionId = event.getTransactionId().getIdentifier();
  }
}
