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

  private UserId identifier;
  private String cellNo;
  private String realName;
  private String passwordHash;

}
