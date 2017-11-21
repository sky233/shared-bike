package com.believe.bike.query.user;

import com.believe.bike.api.user.*;
import com.believe.bike.core.exception.DomainException;
import com.believe.bike.core.exception.ErrorCode;
import com.believe.bike.query.user.repositories.UserEntryRepository;
import com.believe.bike.query.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.Timestamp;
import org.axonframework.eventsourcing.SequenceNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;

/**
 * <p> The describe </p>
 *
 * @author Li Xingping
 */
@Slf4j
@Component
public class UserHandleListener {
  @Autowired
  private UserEntryRepository userEntryRepository;
  @Autowired
  private UserService userService;

  @EventHandler
  public void handle(final UserCreatedEvent event, @Timestamp Instant timestamp, @SequenceNumber Long version) {
    log.info("UserCreatedEvent: [{}] ", event.getIdentifier());
    UserEntry userEntry = new UserEntry();
    userEntry.setId(event.getIdentifier().getIdentifier());
    userEntry.setCellNo(event.getCellNo());
    userEntry.setRealName(event.getRealName());
    userEntry.setPassword(event.getPasswordHash());

    AccountEntry accountEntry = new AccountEntry();
    accountEntry.setCreatedDate(timestamp);
    accountEntry.setUser(userEntry);
    userEntry.setAccount(accountEntry);

    userEntry.setAggregateVersion(version);
    userEntry.setCreatedDate(timestamp);
    userEntry.setLastModifiedDate(timestamp);
    userService.save(userEntry);
  }

  @EventHandler
  public void handle(final UserPaidDepositSuccessfulEvent event, @Timestamp Instant timestamp, @SequenceNumber Long version) {
    log.info("UserPaidDepositSuccessfulEvent: [{}] ", event.getIdentifier());
    UserEntry user = get(event.getIdentifier().getIdentifier());
    user.getAccount().frozen(event.getDepositAmount());
    user.setPaidDeposit(true);
    this.update(user, version, timestamp);
  }

  @EventHandler
  public void handle(final UserWithdrawDepositSuccessfulEvent event, @Timestamp Instant timestamp, @SequenceNumber Long version) {
    log.info("UserWithdrawDepositSuccessfulEvent: [{}] ", event.getIdentifier());
    UserEntry user = get(event.getIdentifier().getIdentifier());
    user.getAccount().thaw(event.getAmount());
    user.setPaidDeposit(false);
    this.update(user, version, timestamp);
  }

  @EventHandler
  public void handle(final UserRechargeSuccessfulEvent event, @Timestamp Instant timestamp, @SequenceNumber Long version) {
    log.info("UserRechargeSuccessfulEvent: [{}] ", event.getIdentifier());
    UserEntry user = get(event.getIdentifier().getIdentifier());
    user.getAccount().increase(event.getAmount());
    this.update(user, version, timestamp);
  }

  @EventHandler
  public void handle(final UserWithdrawSuccessfulEvent event, @Timestamp Instant timestamp, @SequenceNumber Long version) {
    log.info("UserWithdrawSuccessfulEvent: [{}] ", event.getIdentifier());
    UserEntry user = get(event.getIdentifier().getIdentifier());
    user.getAccount().decrease(event.getAmount());
    this.update(user, version, timestamp);
  }

  private void update(UserEntry user, Long version, Instant timestamp) {
    user.setAggregateVersion(version);
    user.setLastModifiedDate(timestamp);
    userService.save(user);
  }

  private UserEntry get(String identifier) {
    return userEntryRepository.findOneById(identifier).orElseThrow(() -> new DomainException(ErrorCode.USER_NOT_FOUND));
  }
}
