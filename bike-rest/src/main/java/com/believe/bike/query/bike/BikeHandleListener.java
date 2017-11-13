package com.believe.bike.query.bike;

import com.believe.bike.api.bike.BikeCreatedEvent;
import com.believe.bike.query.bike.repositories.BikeEntryRepository;
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
    bikeEntryRepository.save(bike);
  }

}
