package com.believe.bike.api.rent;

import com.believe.bike.api.bike.BikeId;
import com.believe.bike.api.user.UserId;
import lombok.Getter;

import java.time.Instant;

/**
 * <p> The describe </p>
 *
 * @author Li Xingping
 */
@Getter
public class RentBikeCompleteEvent extends AbstractRentStartedEvent {

  private final BikeId bikeIdIdentifier;
  private final Instant endTime;

  protected RentBikeCompleteEvent(BikeRentId bikeRentIdentifier, UserId userIdentifier, BikeId bikeIdIdentifier, Instant endTime) {
    super(bikeRentIdentifier, userIdentifier);
    this.bikeIdIdentifier = bikeIdIdentifier;
    this.endTime = endTime;
  }
}
