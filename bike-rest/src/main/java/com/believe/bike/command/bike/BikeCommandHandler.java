package com.believe.bike.command.bike;

import com.believe.bike.api.bike.BikeId;
import com.believe.bike.api.bike.PutOnMarketCommand;
import com.believe.bike.core.exception.DomainException;
import com.believe.bike.core.exception.ErrorCode;
import com.believe.bike.query.bike.BikeEntry;
import com.believe.bike.query.bike.repositories.BikeEntryRepository;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.Repository;

/**
 * <p> The describe </p>
 *
 * @author Li Xingping
 */
public class BikeCommandHandler {

  private BikeEntryRepository bikeEntryRepository;

  private Repository<Bike> repository;

  public BikeCommandHandler(Repository<Bike> repository, BikeEntryRepository bikeEntryRepository) {
    this.repository = repository;
    this.bikeEntryRepository = bikeEntryRepository;
  }

  @CommandHandler
  public BikeId handleCreateBike(PutOnMarketCommand command) throws Exception {
    BikeId identifier = command.getIdentifier();
    BikeEntry userEntry = bikeEntryRepository.findByBikeNumber(command.getBikeNumber());
    if (null != userEntry) {
      throw new DomainException(ErrorCode.VIOLATION_CONSTRAINT, "com.believe.bike.error.bikeNumber.repeated", command.getBikeNumber());
    }
    repository.newInstance(() -> new Bike(identifier, command.getBikeNumber(), command.getPosition()));
    return identifier;
  }
}
