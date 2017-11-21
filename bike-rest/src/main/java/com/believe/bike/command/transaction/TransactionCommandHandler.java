package com.believe.bike.command.transaction;

import com.believe.bike.api.transaction.*;
import com.believe.bike.core.exception.DomainException;
import com.believe.bike.core.exception.ErrorCode;
import com.believe.bike.core.help.MessagesHelper;
import com.believe.bike.query.transaction.repositories.UserTransactionRepository;
import com.believe.bike.query.user.repositories.UserEntryRepository;
import org.apache.commons.lang3.StringUtils;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.Repository;
import org.axonframework.eventhandling.EventBus;

/**
 * <p> Transaction aggregate Handler </p>
 *
 * @author Li Xingping
 */
public class TransactionCommandHandler {

  private final Repository<Transaction> repository;
  private final UserTransactionRepository userTransactionRepository;
  private final UserEntryRepository userEntryRepository;
  private final MessagesHelper messagesHelper;
  private final EventBus eventBus;

  public TransactionCommandHandler(Repository<Transaction> repository, UserTransactionRepository userTransactionRepository, UserEntryRepository userEntryRepository, MessagesHelper messagesHelper, EventBus eventBus) {
    this.repository = repository;
    this.userTransactionRepository = userTransactionRepository;
    this.userEntryRepository = userEntryRepository;
    this.messagesHelper = messagesHelper;
    this.eventBus = eventBus;
  }

  @CommandHandler
  public TransactionId handle(StartingTransactionCommand command) throws Exception {
    userEntryRepository.findOneById(command.getUserId().getIdentifier()).orElseThrow(() -> new DomainException(ErrorCode.USER_NOT_FOUND));
    TransactionId identifier = command.getIdentifier();
    String remark = defaultRemark(command.getRemark(), command.getType(), TransactionStatus.PENDING);
    repository.newInstance(() -> new Transaction(
      identifier, command.getTradeNo(), command.getUserId(), command.getType(), command.getAmount(), remark));
    return identifier;
  }

  @CommandHandler
  public TransactionId handle(CompleteTransactionCommand command) throws Exception {
    TransactionId identifier = command.getIdentifier();
    final String remark = defaultRemark(command.getRemark(), command.getType(), TransactionStatus.COMPLETE);
    repository.load(command.getIdentifier().getIdentifier()).invoke(transaction -> transaction.complete(command, remark));
    return identifier;
  }

  @CommandHandler
  public TransactionId handle(CancelTransactionCommand command) throws Exception {
    TransactionId identifier = command.getIdentifier();
    final String remark = defaultRemark(command.getRemark(), command.getType(), TransactionStatus.CANCEL);
    repository.load(command.getIdentifier().getIdentifier()).invoke(transaction -> transaction.cancel(command, remark));
    return identifier;
  }

  private String defaultRemark(String remark, TransactionType type, TransactionStatus status) {
    return messagesHelper.get("com.believe.bike.transaction.remark",
      new Object[]{
        StringUtils.isNotBlank(remark) ? remark : messagesHelper.get(type.getCode())
        , status
      }
    );
  }
}
