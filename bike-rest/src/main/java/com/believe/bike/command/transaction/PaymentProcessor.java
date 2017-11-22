package com.believe.bike.command.transaction;

import com.believe.bike.api.payment.*;
import com.believe.bike.api.transaction.*;
import com.believe.bike.api.user.UserId;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import javax.validation.constraints.NotNull;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

/**
 * <p> The describe </p>
 *
 * @author Li Xingping
 */
@Data
@NoArgsConstructor
@Aggregate
public class PaymentProcessor {

  @NotNull
  @AggregateIdentifier
  private PaymentId identifier;
  private TransactionId transactionId;
  private UserId userId;

  private PaymentChannel paymentChannel;

  @CommandHandler
  public PaymentProcessor(CreatePaymentCommand command) {
    apply(new PaymentCreatedEvent(command.getIdentifier(), command.getTransactionId(), command.getUserId(), command.getTradeNo(), command.getAmount(), command.getRemark(), command.getPaymentChannel(), PaymentStatus.PENDING));
  }

  @CommandHandler
  public void paid(PaidPlatformPayCommand command) {
    apply(new PlatformPaySuccessfulEvent(command.getIdentifier(),
        command.getTransactionId(),
        command.getUserId(),
        command.getTrade_no(),
        command.getTotal_amount()
      )
    );
  }

  @CommandHandler
  public void paid(PaidAliPayCommand command) {
    apply(new AliPaySuccessfulEvent(command.getIdentifier(),
        command.getTransactionId(),
        command.getUserId(),
        command.getNotify_type(),
        command.getTrade_no(),
        command.getOut_trade_no(),
        command.getTotal_amount()
      )
    );
  }

  @CommandHandler
  public void paid(PaidWxPayCommand command) {
    apply(new WxPaySuccessfulEvent(command.getIdentifier(),
        command.getTransactionId(),
        command.getUserId(),
        command.getOut_trade_no(),
        command.getTransaction_id(),
        command.getBank_type(),
        command.getOpenid(),
        command.getTime_end(),
        command.getTotal_fee()
      )
    );
  }

  @EventSourcingHandler
  public void on(PaymentCreatedEvent event) {
    this.identifier = event.getIdentifier();
    this.transactionId = event.getTransactionId();
    this.userId = event.getUserId();
    this.paymentChannel = event.getPaymentChannel();
  }

}
