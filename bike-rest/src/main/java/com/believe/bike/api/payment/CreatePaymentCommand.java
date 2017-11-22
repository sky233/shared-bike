package com.believe.bike.api.payment;

import com.believe.bike.api.transaction.TransactionId;
import com.believe.bike.api.user.UserId;
import lombok.AllArgsConstructor;
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
public class CreatePaymentCommand {
  @NotNull
  @TargetAggregateIdentifier
  private final PaymentId identifier;
  @NotNull
  private UserId userId;
  @NotNull
  private TransactionId transactionId;
  @NotNull
  private PaymentChannel paymentChannel;
  private String tradeNo;
  @NotNull
  private BigDecimal amount;
  private String remark;
}
