package com.believe.bike.api.user;

import lombok.Value;
import java.math.BigDecimal;

/**
 * <p> The describe </p>
 *
 * @author Li Xingping
 */
@Value
public class UserWithdrawSuccessfulEvent {

  private UserId identifier;
  private BigDecimal amount;

}
