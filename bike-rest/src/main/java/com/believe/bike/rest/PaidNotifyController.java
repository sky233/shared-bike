package com.believe.bike.rest;

import com.believe.bike.api.transaction.CompleteTransactionCommand;
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
@RequestMapping("/notify")
public class PaidNotifyController {

  @Autowired
  private CommandGateway commandGateway;

  @Autowired
  private UserTransactionRepository userTransactionRepository;

  @PostMapping("/paid")
  public String paid(@Valid @RequestBody UserTransactionDto dto) {
    UserTransaction userTransaction = userTransactionRepository.findByTradeNo(dto.getTradeNo());

    CompleteTransactionCommand command = new CompleteTransactionCommand(
      new TransactionId(userTransaction.getId()),
      TransactionType.PAY_DEPOSIT,
      new UserId(userTransaction.getTransactor().getId()),
      userTransaction.getTradeNo(), null);
    commandGateway.sendAndWait(command);

    return "ok";
  }

}
