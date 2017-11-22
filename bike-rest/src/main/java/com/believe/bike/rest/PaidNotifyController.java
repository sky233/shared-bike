package com.believe.bike.rest;

import com.believe.bike.query.transaction.PaymentTransaction;
import com.believe.bike.query.transaction.repositories.PaymentTransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p> The describe </p>
 *
 * @author Li Xingping
 */
@Slf4j
@RestController
@RequestMapping("/notify")
public class PaidNotifyController {

  @Autowired
  private CommandGateway commandGateway;

  @Autowired
  private PaymentTransactionRepository paymentTransactionRepository;

  @PostMapping("/alipay/paid")
  public String paid(HttpServletRequest request) {
    String trade_no = request.getParameter("trade_no");
    PaymentTransaction paymentTransaction = paymentTransactionRepository.findByTradeNo(trade_no);
/*    PaidAliPayCommand command = new PaidAliPayCommand(
      new PaymentId(paymentTransaction.getId()),
      new UserId(paymentTransaction.getTransactor().getId()),
      new TransactionId(paymentTransaction.getTransactionId())
      );*/
    try {
//      commandGateway.sendAndWait(command);
    } catch (Exception e) {
      log.error(e.toString());
      return "fail";
    }
    return "ok";
  }

}
