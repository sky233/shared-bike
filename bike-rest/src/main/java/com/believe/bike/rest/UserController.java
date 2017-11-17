package com.believe.bike.rest;

import com.believe.bike.api.user.CreateUserCommand;
import com.believe.bike.query.user.UserEntry;
import com.believe.bike.query.user.repositories.UserEntryRepository;
import com.believe.bike.rest.dto.UserDto;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p> The describe </p>
 *
 * @author Li Xingping
 */
@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired
  private CommandGateway commandGateway;

  @Autowired
  private UserEntryRepository userEntryRepository;

  @GetMapping
  public List<UserEntry> users() {
    return userEntryRepository.findAll();
  }

  @GetMapping({"/of_pages"})
  public Page<UserEntry> users(Pageable pageable) {
    return userEntryRepository.findAll(pageable);
  }

  @GetMapping({"/{identifier}"})
  public UserEntry users(@PathVariable("identifier") String identifier) {
    return userEntryRepository.findOne(identifier);
  }

  @PostMapping
  public String users(@Valid @RequestBody UserDto userDto) {
    CreateUserCommand command = new CreateUserCommand(userDto.getCellNo(), userDto.getPassword());
    command.setRealName(userDto.getRealName());
    commandGateway.sendAndWait(command);
    return command.getIdentifier().getIdentifier();
  }

}
