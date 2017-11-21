package com.believe.bike.api.bike;

import com.believe.bike.api.user.UserId;
import com.believe.bike.core.model.Position;
import lombok.Value;

import java.io.Serializable;

/**
 * <p> The describe </p>
 *
 * @author Li Xingping
 */
@Value
public class BikeRentOutEvent implements Serializable {

  private BikeId identifier;
  private UserId renter;
  private String bikeNumber;
  private BikeStatus bikeStatus;
  private Position position;

}
