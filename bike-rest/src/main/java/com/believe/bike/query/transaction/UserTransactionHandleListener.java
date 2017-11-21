package com.believe.bike.query.transaction;

import com.believe.bike.api.transaction.TransactionCancelledEvent;
import com.believe.bike.api.transaction.TransactionCompleteEvent;
import com.believe.bike.api.transaction.TransactionStartedEvent;
import com.believe.bike.api.transaction.TransactionStatus;
import com.believe.bike.core.exception.DomainException;
import com.believe.bike.core.exception.ErrorCode;
import com.believe.bike.query.transaction.repositories.UserTransactionRepository;
import com.believe.bike.query.transaction.service.UserTransactionService;
import com.believe.bike.query.user.repositories.UserEntryRepository;
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
public class UserTransactionHandleListener {
  @Autowired
  private UserTransactionRepository userTransactionRepository;
  @Autowired
  private UserEntryRepository userEntryRepository;
  @Autowired
  private UserTransactionService userTransactionService;

  @EventHandler
  public void handle(final TransactionStartedEvent event, @Timestamp Instant timestamp, @SequenceNumber Long version) {
    log.info("TransactionStartedEvent: [{}] ", event.getIdentifier());
    UserTransaction userTransaction = new UserTransaction(event, userEntryRepository.findOneById(event.getUserId().getIdentifier()).orElseThrow(() -> new DomainException(ErrorCode.USER_NOT_FOUND)));
    userTransaction.setAggregateVersion(version);
    userTransaction.setCreatedDate(timestamp);
    userTransaction.setLastModifiedDate(timestamp);
    userTransactionService.save(userTransaction);
  }

  @EventHandler
  public void handle(final TransactionCompleteEvent event, @Timestamp Instant timestamp, @SequenceNumber Long version) {
    log.info("TransactionCompleteEvent: [{}] ", event.getIdentifier());
    UserTransaction userTransaction = get(event.getIdentifier().getIdentifier());

    userTransaction.setRemark(event.getRemark());
    userTransaction.setStatus(TransactionStatus.COMPLETE);

    userTransaction.setAggregateVersion(version);
    userTransaction.setLastModifiedDate(timestamp);
    userTransactionService.save(userTransaction);
  }

  @EventHandler
  public void handle(final TransactionCancelledEvent event, @Timestamp Instant timestamp, @SequenceNumber Long version) {
    log.info("TransactionCancelledEvent: [{}] ", event.getIdentifier());
    UserTransaction userTransaction = get(event.getIdentifier().getIdentifier());

    userTransaction.setRemark(event.getRemark());
    userTransaction.setStatus(TransactionStatus.CANCEL);

    userTransaction.setAggregateVersion(version);
    userTransaction.setLastModifiedDate(timestamp);
    userTransactionService.save(userTransaction);
  }

  private UserTransaction get(String identifier) {
    return userTransactionRepository.findOneById(identifier).orElseThrow(() -> new DomainException(ErrorCode.USER_TRANSACTION_NOT_FOUND));
  }
}
