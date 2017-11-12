package com.believe.bike.command.bike;

import com.believe.bike.api.bike.BikeCreatedEvent;
import com.believe.bike.api.bike.BikeId;
import com.believe.bike.api.bike.BikeStatus;
import com.believe.bike.core.model.Position;
import com.believe.bike.core.utils.GeneralUtils;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import javax.validation.constraints.NotNull;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

/**
 * <p> The describe </p>
 *
 * @author Li Xingping
 */
@Data
@NoArgsConstructor
@Aggregate
public class Bike {

  @NotNull
  @AggregateIdentifier
  private BikeId identifier;

  public BikeStatus status;

  public Bike(BikeId identifier, String bikeNumber, Position position) {
    apply(new BikeCreatedEvent(identifier, bikeNumber, BikeStatus.NOT_RENTED, GeneralUtils.randomNumeric(4), position));
  }

  public BikeId rentOut() {

    return this.identifier;
  }

  @EventSourcingHandler
  public void on(BikeCreatedEvent event) {
    this.identifier = event.getIdentifier();
    this.status = event.getBikeStatus();
  }

}
