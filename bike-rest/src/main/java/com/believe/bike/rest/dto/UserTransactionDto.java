package com.believe.bike.rest.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * <p> The describe </p>
 *
 * @author Li Xingping
 */
@Data
public class UserTransactionDto {

  /**
   * The trade number in internal.
   */
  private String tradeNo;
  @NotBlank
  private String userId;
  @NotNull
  private BigDecimal amount;
  private String remark;

}
