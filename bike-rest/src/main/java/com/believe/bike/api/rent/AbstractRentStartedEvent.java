package com.believe.bike.api.rent;

import com.believe.bike.api.user.UserId;
import lombok.Getter;

/**
 * <p> The describe </p>
 *
 * @author Li Xingping
 */
@Getter
public abstract class AbstractRentStartedEvent {
  private final BikeRentId bikeRentIdentifier;
  private final UserId userIdentifier;

  protected AbstractRentStartedEvent(BikeRentId bikeRentIdentifier, UserId userIdentifier) {
    this.bikeRentIdentifier = bikeRentIdentifier;
    this.userIdentifier = userIdentifier;
  }
}
