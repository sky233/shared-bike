package com.believe.bike.api.bike;

import com.believe.bike.core.model.Position;
import lombok.Value;

import java.io.Serializable;

/**
 * <p> The describe </p>
 *
 * @author Li Xingping
 */
@Value
public class BikeCreatedEvent implements Serializable {

  private BikeId identifier;
  private String bikeNumber;
  private BikeStatus bikeStatus;
  private String unlockPassword;
  private Position position;

}
