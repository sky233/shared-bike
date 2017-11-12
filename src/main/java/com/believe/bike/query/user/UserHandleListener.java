package com.believe.bike.query.user;

import com.believe.bike.api.user.UserCreatedEvent;
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
public class UserHandleListener {
  @Autowired
  private UserEntryRepository userEntryRepository;

  @EventHandler
  public void handle(final UserCreatedEvent event, @Timestamp Instant timestamp, @SequenceNumber Long version) {
    log.info("UserCreatedEvent: [{}] ", event.getIdentifier());
    UserEntry bike = new UserEntry();
    bike.setId(event.getIdentifier().getIdentifier());
    bike.setCellNo(event.getCellNo());
    bike.setPassword(event.getPasswordHash());
    bike.setAggregateVersion(version);

    bike.setCreatedDate(timestamp);
    bike.setLastModifiedDate(timestamp);
    userEntryRepository.save(bike);
  }

}
