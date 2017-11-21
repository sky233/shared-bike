package com.believe.bike.rest;

import com.believe.bike.api.transaction.StartingTransactionCommand;
import com.believe.bike.api.transaction.TransactionId;
import com.believe.bike.api.transaction.TransactionType;
import com.believe.bike.api.user.UserId;
import com.believe.bike.query.transaction.UserTransaction;
import com.believe.bike.query.transaction.repositories.UserTransactionRepository;
import com.believe.bike.rest.dto.UserTransactionDto;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p> The describe </p>
 *
 * @author Li Xingping
 */
@RestController
@RequestMapping("/tx")
public class UserTransactionController {

  @Autowired
  private CommandGateway commandGateway;

  @Autowired
  private UserTransactionRepository userTransactionRepository;

  @GetMapping
  public List<UserTransaction> transactions() {
    return userTransactionRepository.findAll();
  }

  @GetMapping({"/{identifier}"})
  public UserTransaction transactions(@PathVariable("identifier") String identifier) {
    return userTransactionRepository.findOne(identifier);
  }

  @PostMapping("/pay_deposit")
  public String payDeposit(@Valid @RequestBody UserTransactionDto dto) {
    StartingTransactionCommand command = new StartingTransactionCommand(new TransactionId(),
      dto.getTradeNo(),
      new UserId(dto.getUserId()),
      TransactionType.PAY_DEPOSIT,
      dto.getAmount(),
      dto.getRemark());
    commandGateway.sendAndWait(command);
    return command.getIdentifier().getIdentifier();
  }

}
