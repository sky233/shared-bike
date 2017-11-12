package com.believe.bike.api.user;

import lombok.Value;

import java.io.Serializable;

/**
 * <p> The describe </p>
 *
 * @author Li Xingping
 */
@Value
public class UserCreatedEvent implements Serializable {

  private final UserId identifier;
  private final String cellNo;
  private final String passwordHash;

}
