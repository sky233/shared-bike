package com.believe.bike.command.rent;

import com.believe.bike.api.bike.BikeId;
import com.believe.bike.api.rent.BikeRentId;
import com.believe.bike.api.rent.RentBikeStartedEvent;
import com.believe.bike.api.rent.RentTxStatus;
import com.believe.bike.api.user.UserId;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import javax.validation.constraints.NotNull;
import java.time.Instant;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;


/**
 * <p> The describe </p>
 *
 * @author Li Xingping
 */
@Data
@NoArgsConstructor
@Aggregate
public class BikeRent {

  @NotNull
  @AggregateIdentifier
  private BikeRentId identifier;

  private RentTxStatus status;

  public BikeRent(BikeRentId bikeRentId, UserId userId, BikeId bikeId) {
    apply(new RentBikeStartedEvent(bikeRentId, userId, bikeId, Instant.now()));
  }

  @EventSourcingHandler
  public void on(RentBikeStartedEvent event) {
    this.identifier = event.getBikeRentIdentifier();
    this.status = RentTxStatus.PENDING;
  }

}
