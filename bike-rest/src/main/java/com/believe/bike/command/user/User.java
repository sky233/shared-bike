package com.believe.bike.command.user;

import com.believe.bike.api.user.UserAuthenticatedEvent;
import com.believe.bike.api.user.UserCreatedEvent;
import com.believe.bike.api.user.UserId;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateMember;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.util.DigestUtils;

import javax.validation.constraints.NotNull;
import java.io.UnsupportedEncodingException;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

/**
 * <p> The describe </p>
 *
 * @author Li Xingping
 */
@Data
@NoArgsConstructor
@Aggregate
public class User {

  @NotNull
  @AggregateIdentifier
  private UserId identifier;
  private String cellNo;
  private String passwordHash;

  @AggregateMember
  private UserAccount account;

  public User(UserId identifier, String username, String password) {
    apply(new UserCreatedEvent(identifier, username, hashOf(password)));
  }

  public boolean authenticate(String password) {
    boolean success = this.passwordHash.equals(hashOf(password));
    if (success) {
      apply(new UserAuthenticatedEvent(this.identifier));
    }
    return success;
  }

  @EventHandler
  public void on(UserCreatedEvent event) {
    this.identifier = event.getIdentifier();
    this.cellNo = event.getCellNo();
    this.passwordHash = event.getPasswordHash();
  }

  private String hashOf(String password) {
    try {
      return DigestUtils.md5DigestAsHex(password.getBytes("UTF-8"));
    } catch (UnsupportedEncodingException e) {
      //Ignored.
    }
    return null;
  }

}
