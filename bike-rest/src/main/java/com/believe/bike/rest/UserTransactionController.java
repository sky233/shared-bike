package com.believe.bike.rest;

import com.believe.bike.query.transaction.UserTransaction;
import com.believe.bike.query.transaction.repositories.UserTransactionRepository;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

}
