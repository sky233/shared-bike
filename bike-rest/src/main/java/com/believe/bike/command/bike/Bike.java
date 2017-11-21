package com.believe.bike.command.bike;

import com.believe.bike.api.bike.*;
import com.believe.bike.core.exception.DomainException;
import com.believe.bike.core.exception.ErrorCode;
import com.believe.bike.core.model.Position;
import com.believe.bike.core.utils.GeneralUtils;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
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

  /**
   * 租出
   */
  @CommandHandler
  public void rentOut(RentOutBikeCommand command) {
    if (BikeStatus.RENTED.equals(this.status)) {
      throw new DomainException(ErrorCode.BIKE_STATE_INVALID, "com.believe.bike.InvalidState");
    }
    apply(new BikeRentOutEvent(command.getIdentifier(), command.getRenter(), command.getBikeNumber(), BikeStatus.RENTED, command.getPosition()));
  }

  /**
   * 租还
   */
  @CommandHandler
  public void rentBack(RentBackBikeCommand command) {
    if (BikeStatus.NOT_RENTED.equals(this.status)) {
      throw new DomainException(ErrorCode.BIKE_STATE_INVALID, "com.believe.bike.InvalidState");
    }
    apply(new BikeRentBackEvent(command.getIdentifier(), command.getRenter(), command.getBikeNumber(), BikeStatus.RENTED, command.getPosition()));
  }

  @EventSourcingHandler
  public void on(BikeCreatedEvent event) {
    this.identifier = event.getIdentifier();
    this.status = event.getBikeStatus();
  }

  @EventSourcingHandler
  public void on(BikeRentOutEvent event) {
    this.status = event.getBikeStatus();
  }

  @EventSourcingHandler
  public void on(BikeRentBackEvent event) {
    this.status = event.getBikeStatus();
  }

}
