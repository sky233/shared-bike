package com.believe.bike.api.transaction;

import com.believe.bike.api.user.UserId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.TargetAggregateIdentifier;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * <p> The describe </p>
 *
 * @author Li Xingping
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CancelTransactionCommand {

  @NotNull
  @TargetAggregateIdentifier
  private TransactionId identifier;
  @NotNull
  private TransactionType type;
  @NotNull
  private UserId userId;
  @NotBlank
  private String tradeNo;
  private String remark;
}
