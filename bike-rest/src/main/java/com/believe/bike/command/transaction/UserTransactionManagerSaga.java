package com.believe.bike.command.transaction;

import com.believe.bike.api.payment.*;
import com.believe.bike.api.transaction.*;
import com.believe.bike.api.user.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.saga.EndSaga;
import org.axonframework.eventhandling.saga.SagaEventHandler;
import org.axonframework.eventhandling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.axonframework.eventhandling.saga.SagaLifecycle.associateWith;
import static org.axonframework.eventhandling.saga.SagaLifecycle.end;

/**
 * <p> The user transaction saga. </p>
 *
 * @author Li Xingping
 */
@Slf4j
@Saga
@Data
public class UserTransactionManagerSaga {

  private transient CommandGateway commandGateway;
  private TransactionId identifier;
  private UserId userIdentifier;
  private TransactionStatus transactionStatus;
  private TransactionType transactionType;
  private BigDecimal transactionAmount;

  @Autowired
  public void setCommandGateway(CommandGateway commandGateway) {
    this.commandGateway = commandGateway;
  }

  @StartSaga
  @SagaEventHandler(associationProperty = "identifier")
  public void handle(TransactionStartedEvent event) {
    if (log.isDebugEnabled()) {
      log.debug("Staring transaction type: {} current status: {} amount: {} .", event.getType(), event.getStatus(), event.getAmount());
    }
    switch (event.getType()) {
      case PAY_DEPOSIT:
        associateWith("paymentId", createPayment(event));
        break;
      case RECHARGE:
        associateWith("paymentId", createPayment(event));
        break;
      case WITHDRAW_DEPOSIT:
        associateWith("approvalId", createApproval(event));
        break;
      case REFUND:
        associateWith("approvalId", createApproval(event));
        break;
    }
    setIdentifier(event.getIdentifier());
    setTransactionStatus(event.getStatus());
    setTransactionType(event.getType());
    setTransactionAmount(event.getAmount());
    setUserIdentifier(event.getUserId());
  }

  @SagaEventHandler(associationProperty = "paymentId")
  public void handle(PlatformPaySuccessfulEvent event) {
    log.debug("{} Transaction {} is complete.", this.getTransactionType(), event.getPaymentId());
    commandGateway.send(new CompleteTransactionCommand(
      this.getIdentifier(),
      this.getTransactionType(),
      this.getUserIdentifier(),
      event.getTrade_no(), "Platform pay successful."));
  }

  @SagaEventHandler(associationProperty = "paymentId")
  public void handle(AliPaySuccessfulEvent event) {
    log.debug("{} Transaction {} is complete.", this.getTransactionType(), event.getPaymentId());
    commandGateway.send(new CompleteTransactionCommand(
      this.getIdentifier(),
      this.getTransactionType(),
      this.getUserIdentifier(),
      event.getTrade_no(), "Alipay pay successful."));
  }

  @SagaEventHandler(associationProperty = "paymentId")
  public void handle(WxPaySuccessfulEvent event) {
    log.debug("{} Transaction {} is complete.", this.getTransactionType(), event.getPaymentId());
    commandGateway.send(new CompleteTransactionCommand(
      this.getIdentifier(),
      this.getTransactionType(),
      this.getUserIdentifier(),
      event.getOut_trade_no(), "Wechat pay successful."));
  }

  @SagaEventHandler(associationProperty = "identifier")
  @EndSaga
  public void handle(TransactionCompleteEvent event) {
    log.debug("{} Transaction {} is complete.", this.getTransactionType(), event.getIdentifier());
    broadcastUserAccount();
  }

  @SagaEventHandler(associationProperty = "identifier")
  @EndSaga
  public void handle(TransactionCancelledEvent event) {
    log.debug("{} Transaction {} is cancelled.", this.getTransactionType(), event.getIdentifier());
  }

  private void broadcastUserAccount() {
    switch (this.getTransactionType()) {
      case PAY_DEPOSIT:
        commandGateway.send(new PayDepositCommand(this.getUserIdentifier(), this.getTransactionAmount()));
        break;
      case WITHDRAW_DEPOSIT:
        commandGateway.send(new WithdrawDepositCommand(this.getUserIdentifier(), this.getTransactionAmount()));
        break;
      case RECHARGE:
        commandGateway.send(new UserRechargeCommand(this.getUserIdentifier(), this.getTransactionAmount()));
        break;
      case WITHDRAW:
        commandGateway.send(new UserWithdrawRequestCommand(this.getUserIdentifier(), this.getTransactionAmount()));
        break;
      case REFUND:
        commandGateway.send(new UserWithdrawRequestCommand(this.getUserIdentifier(), this.getTransactionAmount()));
        break;
    }
  }

  private String createPayment(final TransactionStartedEvent event) {
    CreatePaymentCommand command = new CreatePaymentCommand(new PaymentId());
    command.setUserId(event.getUserId());
    command.setTransactionId(event.getIdentifier());
    command.setPaymentChannel(event.getPaymentChannel());
    command.setTradeNo(event.getTradeNo());
    command.setAmount(event.getAmount());
    command.setRemark(event.getRemark());
    commandGateway.send(command);
    return command.getIdentifier().getIdentifier();
  }

  private String createApproval(final TransactionStartedEvent event) {
    //todo
    return null;
  }

}
