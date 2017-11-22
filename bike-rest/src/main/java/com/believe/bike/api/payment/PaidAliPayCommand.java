package com.believe.bike.api.payment;

import com.believe.bike.api.transaction.TransactionId;
import com.believe.bike.api.user.UserId;
import lombok.Data;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * <p> The describe </p>
 *
 * @author Li Xingping
 */
@Data
public class PaidAliPayCommand {
  @NotNull
  @TargetAggregateIdentifier
  private PaymentId identifier;
  @NotNull
  private UserId userId;
  @NotNull
  private TransactionId transactionId;

  /**
   * Ali pay return.
   */
  private String notify_type;
  private String trade_no;
  private String out_trade_no;
  private BigDecimal total_amount;
}
