package com.believe.bike.command.transaction;

import com.believe.bike.api.approval.ApprovalId;
import com.believe.bike.api.transaction.TransactionId;
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
//@Aggregate
public class ApprovalProcessor {

  @NotNull
  @AggregateIdentifier
  private ApprovalId identifier;
  private TransactionId transactionId;
  private UserId userId;

}
