package com.believe.bike.rest.dto;

import com.believe.bike.core.validators.PhoneNumber;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * <p> The describe </p>
 *
 * @author Li Xingping
 */
@Data
public class UserDto {
  private String realName;
  @NotBlank
  @PhoneNumber
  private String cellNo;
  private String password;
}
