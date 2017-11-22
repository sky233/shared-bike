package com.believe.bike.query.transaction;

import com.believe.bike.api.payment.*;
import com.believe.bike.core.exception.DomainException;
import com.believe.bike.core.exception.ErrorCode;
import com.believe.bike.query.transaction.repositories.PaymentTransactionRepository;
import com.believe.bike.query.transaction.service.UserTransactionService;
import com.believe.bike.query.user.repositories.UserEntryRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
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
public class PaymentTransactionHandleListener {
  @Autowired
  private PaymentTransactionRepository paymentTransactionRepository;
  @Autowired
  private UserEntryRepository userEntryRepository;
  @Autowired
  private UserTransactionService userTransactionService;

  @Autowired
  private CommandGateway commandGateway;

  @EventHandler
  public void handle(final PaymentCreatedEvent event, @Timestamp Instant timestamp, @SequenceNumber Long version) {
    log.info("PaymentCreatedEvent: [{}] ", event.getIdentifier());
    PaymentTransaction paymentTransaction = new PaymentTransaction(event, userEntryRepository.findOneById(event.getUserId().getIdentifier()).orElseThrow(() -> new DomainException(ErrorCode.USER_NOT_FOUND)));
    paymentTransaction.setAggregateVersion(version);
    paymentTransaction.setCreatedDate(timestamp);
    paymentTransaction.setLastModifiedDate(timestamp);
    userTransactionService.save(paymentTransaction);

    if (PaymentChannel.PLATFORM_PAY.equals(event.getPaymentChannel())) {
      commandGateway.send(new PaidPlatformPayCommand(event.getIdentifier(), event.getUserId(), event.getTransactionId(), event.getTradeNo(), event.getAmount()));
    }
  }

  @EventHandler
  public void handle(final PlatformPaySuccessfulEvent event, @Timestamp Instant timestamp, @SequenceNumber Long version) {
    log.info("PlatformPaySuccessfulEvent: [{}] ", event.getPaymentId());
    PaymentTransaction paymentTransaction = get(event.getPaymentId().getIdentifier());

    paymentTransaction.setStatus(PaymentStatus.COMPLETE);
    paymentTransaction.setTotalAmount(event.getTotal_amount());

    paymentTransaction.setAggregateVersion(version);
    paymentTransaction.setLastModifiedDate(timestamp);
    userTransactionService.save(paymentTransaction);

  }

  @EventHandler
  public void handle(final AliPaySuccessfulEvent event, @Timestamp Instant timestamp, @SequenceNumber Long version) {
    log.info("AliPaySuccessfulEvent: [{}] ", event.getPaymentId());
    PaymentTransaction paymentTransaction = get(event.getPaymentId().getIdentifier());

    paymentTransaction.setStatus(PaymentStatus.COMPLETE);
    paymentTransaction.setTotalAmount(event.getTotal_amount());
    paymentTransaction.setOutTradeNo(event.getOut_trade_no());

    paymentTransaction.setAggregateVersion(version);
    paymentTransaction.setLastModifiedDate(timestamp);
    userTransactionService.save(paymentTransaction);
  }

  @EventHandler
  public void handle(final WxPaySuccessfulEvent event, @Timestamp Instant timestamp, @SequenceNumber Long version) {
    log.info("WxPaySuccessfulEvent: [{}] ", event.getPaymentId());
    PaymentTransaction paymentTransaction = get(event.getPaymentId().getIdentifier());

    paymentTransaction.setStatus(PaymentStatus.COMPLETE);
    paymentTransaction.setTotalAmount(event.getTotal_fee());
    paymentTransaction.setOutTradeNo(event.getOut_trade_no());

    paymentTransaction.setAggregateVersion(version);
    paymentTransaction.setLastModifiedDate(timestamp);
    userTransactionService.save(paymentTransaction);
  }

  private PaymentTransaction get(String identifier) {
    return paymentTransactionRepository.findOneById(identifier).orElseThrow(() -> new DomainException(ErrorCode.USER_TRANSACTION_NOT_FOUND));
  }
}
