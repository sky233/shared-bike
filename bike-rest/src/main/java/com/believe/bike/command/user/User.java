package com.believe.bike.command.user;

import com.believe.bike.api.user.*;
import com.believe.bike.core.exception.DomainException;
import com.believe.bike.core.exception.ErrorCode;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateMember;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
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

  public User(UserId identifier, String username, String realName, String password) {
    apply(new UserCreatedEvent(identifier, username, realName, hashOf(password)));
  }

  @CommandHandler
  public void payDeposit(PayDepositCommand command) {
    if (!this.account.isActive()) {
      throw new DomainException(ErrorCode.USER_ACCOUNT_INVALID);
    }
    apply(new UserPaidDepositSuccessfulEvent(command.getIdentifier(), command.getDepositAmount()));
  }

  @CommandHandler
  public void withdrawDeposit(WithdrawDepositCommand command) {
    if (!this.account.isActive()) {
      throw new DomainException(ErrorCode.USER_ACCOUNT_INVALID);
    }
    apply(new UserWithdrawDepositSuccessfulEvent(command.getIdentifier(), command.getDepositAmount()));
  }

  @CommandHandler
  public void recharge(UserRechargeCommand command) {
    if (!this.account.isActive()) {
      throw new DomainException(ErrorCode.USER_ACCOUNT_INVALID);
    }
    apply(new UserRechargeSuccessfulEvent(command.getIdentifier(), command.getAmount()));
  }

  @CommandHandler
  public void withdraw(UserWithdrawRequestCommand command) {
    if (!this.account.isActive()) {
      throw new DomainException(ErrorCode.USER_ACCOUNT_INVALID);
    }
    apply(new UserWithdrawSuccessfulEvent(command.getIdentifier(), command.getAmount()));
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
    this.account = new UserAccount();
  }

  @EventSourcingHandler
  public void on(UserPaidDepositSuccessfulEvent event) {
    this.identifier = event.getIdentifier();
    this.account.frozen(event.getDepositAmount());
  }

  @EventSourcingHandler
  public void on(UserWithdrawDepositSuccessfulEvent event) {
    this.identifier = event.getIdentifier();
    this.account.thaw(event.getAmount());
  }

  @EventSourcingHandler
  public void on(UserRechargeSuccessfulEvent event) {
    this.identifier = event.getIdentifier();
    this.account.increase(event.getAmount());
  }

  @EventSourcingHandler
  public void on(UserWithdrawSuccessfulEvent event) {
    this.identifier = event.getIdentifier();
    this.account.decrease(event.getAmount());
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
