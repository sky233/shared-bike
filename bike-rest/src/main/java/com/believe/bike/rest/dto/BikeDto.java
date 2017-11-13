package com.believe.bike.rest.dto;

import com.believe.bike.core.model.Position;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * <p> The describe </p>
 *
 * @author Li Xingping
 */
@Data
public class BikeDto {

  @NotBlank
  private String bikeNumber;
  private Position position;

}
