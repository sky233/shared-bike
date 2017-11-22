package com.believe.bike.api.payment;

import com.believe.bike.api.transaction.TransactionId;
import com.believe.bike.api.user.UserId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * <p> The describe </p>
 *
 * @author Li Xingping
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaidPlatformPayCommand {
  @NotNull
  @TargetAggregateIdentifier
  private PaymentId identifier;
  @NotNull
  private UserId userId;
  @NotNull
  private TransactionId transactionId;

  private String trade_no;
  private BigDecimal total_amount;
}
