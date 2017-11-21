package com.believe.bike.query.bike;

import com.believe.bike.api.bike.BikeCreatedEvent;
import com.believe.bike.api.bike.BikeRentBackEvent;
import com.believe.bike.api.bike.BikeRentOutEvent;
import com.believe.bike.api.bike.BikeStatus;
import com.believe.bike.core.exception.DomainException;
import com.believe.bike.core.exception.ErrorCode;
import com.believe.bike.core.model.Position;
import com.believe.bike.query.bike.repositories.BikeEntryRepository;
import com.believe.bike.query.bike.service.BikeService;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.Timestamp;
import org.axonframework.eventsourcing.SequenceNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;

/**
 * <p> The describe </p>
 *
 * @author Li Xingping
 */
@Slf4j
@Component
public class BikeHandleListener {
  @Autowired
  private BikeEntryRepository bikeEntryRepository;
  @Autowired
  private BikeService bikeService;

  @EventHandler
  public void handle(final BikeCreatedEvent event, @Timestamp Instant timestamp, @SequenceNumber Long version) {
    log.info("BikeCreatedEvent: [{}] ", event.getIdentifier());
    BikeEntry bike = new BikeEntry();
    bike.setId(event.getIdentifier().getIdentifier());
    bike.setBikeNumber(event.getBikeNumber());
    bike.setStatus(event.getBikeStatus());
    bike.setUnlockPassword(event.getUnlockPassword());
    bike.setPosition(event.getPosition());

    bike.setAggregateVersion(version);
    bike.setCreatedDate(timestamp);
    bike.setLastModifiedDate(timestamp);
    bikeService.save(bike);
  }

  @EventHandler
  public void handle(final BikeRentOutEvent event, @Timestamp Instant timestamp, @SequenceNumber Long version) {
    log.info("BikeRentOutEvent: [{}] ", event.getIdentifier());
    this.update(
      get(event.getIdentifier().getIdentifier()),
      event.getBikeStatus(),
      event.getPosition(),
      version,
      timestamp
    );
  }

  @EventHandler
  public void handle(final BikeRentBackEvent event, @Timestamp Instant timestamp, @SequenceNumber Long version) {
    log.info("BikeRentBackEvent: [{}] ", event.getIdentifier());
    this.update(
      get(event.getIdentifier().getIdentifier()),
      event.getBikeStatus(),
      event.getPosition(),
      version,
      timestamp
    );
  }

  private BikeEntry get(String identifier) {
    return bikeEntryRepository.findOneById(identifier).orElseThrow(() -> new DomainException(ErrorCode.BIKE_NOT_FOUND));
  }

  private void update(BikeEntry bike, BikeStatus status, Position position, Long version, Instant timestamp) {
    bike.setStatus(status);
    bike.setPosition(position);
    bike.setAggregateVersion(version);
    bike.setLastModifiedDate(timestamp);
    bikeService.save(bike);
  }

}
