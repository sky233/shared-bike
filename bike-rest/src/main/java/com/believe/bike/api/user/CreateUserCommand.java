package com.believe.bike.api.user;

import lombok.Data;
import org.axonframework.commandhandling.TargetAggregateIdentifier;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * <p> The describe </p>
 *
 * @author Li Xingping
 */
@Data
public class CreateUserCommand {

  @NotNull
  @TargetAggregateIdentifier
  private final UserId identifier;
  @NotBlank
  private final String cellNo;
  private String password;

  public CreateUserCommand(String cellNo, String password) {
    this.cellNo = cellNo;
    this.password = password;
    this.identifier = new UserId();
  }

}
