package com.believe.bike.command.user;

import com.believe.bike.api.user.AuthenticateUserCommand;
import com.believe.bike.api.user.CreateUserCommand;
import com.believe.bike.api.user.UserId;
import com.believe.bike.core.exception.DomainException;
import com.believe.bike.core.exception.ErrorCode;
import com.believe.bike.query.user.UserEntry;
import com.believe.bike.query.user.repositories.UserEntryRepository;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.Repository;
import org.axonframework.eventhandling.EventBus;

/**
 * <p> Users aggregate Handler </p>
 *
 * @author Li Xingping
 */
public class UserCommandHandler {

  private final Repository<User> repository;
  private final UserEntryRepository userEntryRepository;
  private final EventBus eventBus;

  public UserCommandHandler(Repository<User> repository, UserEntryRepository userEntryRepository, EventBus eventBus) {
    this.repository = repository;
    this.userEntryRepository = userEntryRepository;
    this.eventBus = eventBus;
  }

  @CommandHandler
  public UserId handleCreateUser(CreateUserCommand command) throws Exception {
    UserId identifier = command.getIdentifier();
    UserEntry userEntry = userEntryRepository.findByCellNo(command.getCellNo());
    if (null != userEntry) {
      throw new DomainException(ErrorCode.VIOLATION_CONSTRAINT, "com.believe.bike.error.cellNo.repeated", command.getCellNo());
    }
    repository.newInstance(() -> new User(identifier, command.getCellNo(), command.getRealName(), command.getPassword()));
    return identifier;
  }

  @CommandHandler
  public void handleAuthenticateUser(AuthenticateUserCommand command) {
    // todo
  }

}
