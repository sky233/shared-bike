package com.believe.bike.api.bike;

import com.believe.bike.api.user.UserId;
import com.believe.bike.core.model.Position;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.TargetAggregateIdentifier;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * <p> The describe </p>
 *
 * @author Li Xingping
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentOutBikeCommand {

  @NotNull
  @TargetAggregateIdentifier
  private BikeId identifier;

  @NotNull
  private UserId renter;

  @NotBlank
  private String bikeNumber;

  private Position position;

}
