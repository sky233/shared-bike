package com.believe.bike.rest;

import com.believe.bike.api.payment.PaymentChannel;
import com.believe.bike.api.transaction.StartingTransactionCommand;
import com.believe.bike.api.transaction.TransactionId;
import com.believe.bike.api.transaction.TransactionType;
import com.believe.bike.api.user.UserId;
import com.believe.bike.query.transaction.repositories.PaymentTransactionRepository;
import com.believe.bike.rest.dto.UserTransactionDto;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p> The describe </p>
 *
 * @author Li Xingping
 */
@RestController
@RequestMapping("/pay")
public class PaymentController {

  @Autowired
  private CommandGateway commandGateway;

  @Autowired
  private PaymentTransactionRepository paymentTransactionRepository;

  @PostMapping("/deposit")
  public String payDeposit(@Valid @RequestBody UserTransactionDto dto) {
    StartingTransactionCommand command = new StartingTransactionCommand(new TransactionId(),
      dto.getTradeNo(),
      new UserId(dto.getUserId()),
      TransactionType.PAY_DEPOSIT,
      dto.getAmount(),
      dto.getRemark(),
      PaymentChannel.PLATFORM_PAY
    );
    commandGateway.sendAndWait(command);
    return command.getIdentifier().getIdentifier();
  }

}
