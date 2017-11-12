package com.believe.bike.command.rent;

import com.believe.bike.api.bike.BikeId;
import com.believe.bike.api.rent.RentBikeCompleteEvent;
import com.believe.bike.api.rent.RentBikeStartedEvent;
import com.believe.bike.api.rent.BikeRentId;
import com.believe.bike.api.user.UserId;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.eventhandling.saga.EndSaga;
import org.axonframework.eventhandling.saga.SagaEventHandler;
import org.axonframework.eventhandling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;

/**
 * @author Jettro Coenradie
 */
@Slf4j
@Saga
@Data
public class RentBikeTradeManagerSaga {

  private transient CommandBus commandBus;
  private BikeRentId bikeRentIdentifier;
  private UserId userIdentifier;
  private BikeId bikeIdIdentifier;

  private Instant startTime;
  private Instant endTime;

  @Autowired
  public void setCommandBus(CommandBus commandBus) {
    this.commandBus = commandBus;
  }

  @StartSaga
  @SagaEventHandler(associationProperty = "bikeRentIdentifier")
  public void handle(RentBikeStartedEvent event) {
    if (log.isDebugEnabled()) {
      log.debug(
        "A new Rent bike rent is started with identifier {}, for user with identifier {} and bike with identifier {}",
        event.getBikeRentIdentifier(),
        event.getUserIdentifier(),
        event.getBikeIdIdentifier());
    }
    setBikeRentIdentifier(event.getBikeRentIdentifier());
    setUserIdentifier(event.getUserIdentifier());
    setBikeIdIdentifier(event.getBikeIdIdentifier());
    setStartTime(event.getStartTime());
  }

  @SagaEventHandler(associationProperty = "bikeRentIdentifier")
  @EndSaga
  public void handle(RentBikeCompleteEvent event) {
    log.debug("Rent bike Transaction {} is executed", event.getBikeRentIdentifier());
//    AddItemsToPortfolioCommand addItemsCommand = new AddItemsToPortfolioCommand(getPortfolioIdentifier(),event.getAmountOfItems());
//    getCommandBus().dispatch(new GenericCommandMessage<>(addItemsCommand));
  }

}
