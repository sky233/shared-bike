package com.believe.bike.query.rent;

import com.believe.bike.api.rent.RentBikeStartedEvent;
import com.believe.bike.core.exception.DomainException;
import com.believe.bike.query.rent.repositories.BikeRentEntryRepository;
import com.believe.bike.query.user.repositories.UserEntryRepository;
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
public class BikeRentHandleListener {
  @Autowired
  private BikeRentEntryRepository bikeRentEntryRepository;

  @Autowired
  private UserEntryRepository userEntryRepository;

  @EventHandler
  public void handle(final RentBikeStartedEvent event, @Timestamp Instant timestamp, @SequenceNumber Long version) {
    log.info("RentBikeStartedEvent: [{}] ", event.getBikeRentIdentifier());
    BikeRentEntry bikeRent = new BikeRentEntry();
    bikeRent.setId(event.getBikeRentIdentifier().getIdentifier());

    bikeRent.setRenter(userEntryRepository.findOneById(event.getUserIdentifier().getIdentifier()).orElseThrow(
      () -> new DomainException("用户不存在")
    ));

    bikeRent.setAggregateVersion(version);
    bikeRent.setCreatedDate(timestamp);
    bikeRent.setLastModifiedDate(timestamp);
    bikeRentEntryRepository.save(bikeRent);
  }

}
