package com.believe.bike.api.user;

import lombok.Value;

/**
 * <p> The describe </p>
 *
 * @author Li Xingping
 */
@Value
public class UserAuthenticatedEvent {
  private final UserId identifier;
}
