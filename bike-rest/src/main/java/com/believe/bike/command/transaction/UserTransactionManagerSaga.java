package com.believe.bike.command.transaction;

import com.believe.bike.api.transaction.*;
import com.believe.bike.api.user.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.GenericCommandMessage;
import org.axonframework.eventhandling.saga.EndSaga;
import org.axonframework.eventhandling.saga.SagaEventHandler;
import org.axonframework.eventhandling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

/**
 * <p> The user transaction saga. </p>
 *
 * @author Li Xingping
 */
@Slf4j
@Saga
@Data
public class UserTransactionManagerSaga {

  private transient CommandBus commandBus;
  private TransactionId identifier;
  private UserId userIdentifier;
  private TransactionStatus transactionStatus;
  private TransactionType transactionType;
  private BigDecimal transactionAmount;

  @Autowired
  public void setCommandBus(CommandBus commandBus) {
    this.commandBus = commandBus;
  }

  @StartSaga
  @SagaEventHandler(associationProperty = "identifier")
  public void handle(TransactionStartedEvent event) {
    if (log.isDebugEnabled()) {
      log.debug("Staring transaction type: {} current status: {} amount: {} .", event.getType(), event.getStatus(), event.getAmount());
    }
    setIdentifier(event.getIdentifier());
    setTransactionStatus(event.getStatus());
    setTransactionType(event.getType());
    setTransactionAmount(event.getAmount());
    setUserIdentifier(event.getUserId());
  }

  @SagaEventHandler(associationProperty = "identifier")
  @EndSaga
  public void handle(TransactionCompleteEvent event) {
    log.debug("{} Transaction {} is complete.", this.getTransactionType(), event.getIdentifier());

    switch (this.getTransactionType()) {
      case PAY_DEPOSIT:
        commandBus.dispatch(new GenericCommandMessage<Object>(
          new PayDepositCommand(this.getUserIdentifier(), this.getTransactionAmount())
        ));
        break;
      case WITHDRAW_DEPOSIT:
        commandBus.dispatch(new GenericCommandMessage<Object>(
          new WithdrawDepositCommand(this.getUserIdentifier(), this.getTransactionAmount())
        ));
        break;
      case RECHARGE:
        commandBus.dispatch(new GenericCommandMessage<Object>(
          new UserRechargeCommand(this.getUserIdentifier(), this.getTransactionAmount())
        ));
        break;
      case WITHDRAW:
        commandBus.dispatch(new GenericCommandMessage<Object>(
          new UserWithdrawRequestCommand(this.getUserIdentifier(), this.getTransactionAmount())
        ));
        break;
    }
  }

  @SagaEventHandler(associationProperty = "identifier")
  @EndSaga
  public void handle(TransactionCancelledEvent event) {
    log.debug("{} Transaction {} is cancelled.", this.getTransactionType(), event.getIdentifier());
  }

}
