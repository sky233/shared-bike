package com.believe.bike.command.transaction;

import com.believe.bike.api.transaction.*;
import com.believe.bike.api.user.UserId;
import com.believe.bike.core.utils.GeneralUtils;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import javax.validation.constraints.NotNull;

import java.math.BigDecimal;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

/**
 * <p> The describe </p>
 *
 * @author Li Xingping
 */
@Data
@NoArgsConstructor
@Aggregate
public class Transaction {

  @NotNull
  @AggregateIdentifier
  private TransactionId identifier;

  private UserId userId;
  private TransactionType type;
  private TransactionStatus status;

  // todo 修改 TradeNo 默认生成方式
  public Transaction(TransactionId identifier, String tradeNo, UserId userId, TransactionType type, BigDecimal amount, String remark) {
    apply(new TransactionStartedEvent(identifier,
        isNotBlank(tradeNo) ? tradeNo : GeneralUtils.randomNumeric(10),
        userId,
        type,
        TransactionStatus.PENDING,
        amount,
        remark
      )
    );
  }

  public TransactionId complete(CompleteTransactionCommand command, String remark) {
    apply(new TransactionCompleteEvent(command.getIdentifier(),
        command.getTradeNo(),
        command.getUserId(),
        remark
      )
    );
    return command.getIdentifier();
  }

  public TransactionId cancel(CancelTransactionCommand command, String remark) {
    apply(new TransactionCancelledEvent(command.getIdentifier(),
        command.getTradeNo(),
        command.getUserId(),
        remark
      )
    );
    return command.getIdentifier();
  }

  @EventSourcingHandler
  public void on(TransactionStartedEvent event) {
    this.identifier = event.getIdentifier();
    this.status = event.getStatus();
    this.type = event.getType();
  }

  @EventSourcingHandler
  public void on(TransactionCompleteEvent event) {
    this.status = TransactionStatus.COMPLETE;
  }

  @EventSourcingHandler
  public void on(TransactionCancelledEvent event) {
    this.status = TransactionStatus.CANCEL;
  }

}
