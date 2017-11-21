package com.believe.bike.api.user;

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
public class UserWithdrawRequestCommand {

  @NotNull
  @TargetAggregateIdentifier
  private UserId identifier;

  @NotNull
  private BigDecimal amount;

}
