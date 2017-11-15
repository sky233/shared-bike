package com.believe.bike.rest.dto;

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
  private String cellNo;
  private String password;
}
