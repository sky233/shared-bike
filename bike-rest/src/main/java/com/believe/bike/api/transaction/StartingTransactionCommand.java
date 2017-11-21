package com.believe.bike.api.transaction;

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
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StartingTransactionCommand {

  @NotNull
  @TargetAggregateIdentifier
  private TransactionId identifier;
  private String tradeNo;
  @NotNull
  private UserId userId;
  @NotNull
  private TransactionType type;
  @NotNull
  private BigDecimal amount;

  private String remark;
}
