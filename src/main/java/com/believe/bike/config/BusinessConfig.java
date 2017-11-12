package com.believe.bike.config;

import com.believe.bike.command.bike.Bike;
import com.believe.bike.command.bike.BikeCommandHandler;
import com.believe.bike.command.rent.BikeRent;
import com.believe.bike.command.rent.BikeRentCommandHandler;
import com.believe.bike.command.user.User;
import com.believe.bike.command.user.UserCommandHandler;
import com.believe.bike.query.bike.repositories.BikeEntryRepository;
import com.believe.bike.query.rent.repositories.BikeRentEntryRepository;
import com.believe.bike.query.user.repositories.UserEntryRepository;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.spring.config.AxonConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p> The describe </p>
 *
 * @author Li Xingping
 */
@Configuration
public class BusinessConfig {

  @Autowired
  private AxonConfiguration axonConfiguration;

  @Autowired
  private EventBus eventBus;

  @Autowired
  private UserEntryRepository userEntryRepository;

  @Autowired
  private BikeEntryRepository bikeEntryRepository;

  @Autowired
  private BikeRentEntryRepository bikeRentEntryRepository;

  @Bean
  public UserCommandHandler userCommandHandler() {
    return new UserCommandHandler(axonConfiguration.repository(User.class), userEntryRepository, eventBus);
  }

  @Bean
  public BikeCommandHandler bikeCommandHandler() {
    return new BikeCommandHandler(axonConfiguration.repository(Bike.class), bikeEntryRepository);
  }

  @Bean
  public BikeRentCommandHandler bikeRentCommandHandler() {
    return new BikeRentCommandHandler(axonConfiguration.repository(BikeRent.class), bikeRentEntryRepository);
  }
}
