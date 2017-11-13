package com.believe.bike.command.rent;

import com.believe.bike.query.rent.repositories.BikeRentEntryRepository;
import org.axonframework.commandhandling.model.Repository;

/**
 * <p> The describe </p>
 *
 * @author Li Xingping
 */
public class BikeRentCommandHandler {

  private final Repository<BikeRent> repository;
  private final BikeRentEntryRepository bikeRentEntryRepository;

  public BikeRentCommandHandler(Repository<BikeRent> repository, BikeRentEntryRepository bikeRentEntryRepository) {
    this.repository = repository;
    this.bikeRentEntryRepository = bikeRentEntryRepository;
  }

}
