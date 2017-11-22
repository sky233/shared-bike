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
public class PaidWxPayCommand {
  @NotNull
  @TargetAggregateIdentifier
  private PaymentId identifier;
  @NotNull
  private UserId userId;
  @NotNull
  private TransactionId transactionId;

  /**
   * Wechat pay return.
   */
  private String out_trade_no;
  private String transaction_id;
  private String bank_type;
  private String openid;
  private String time_end;
  private BigDecimal total_fee;
}
