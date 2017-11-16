package com.believe.bike.rest;

import com.believe.bike.api.bike.BikeId;
import com.believe.bike.api.bike.PutOnMarketCommand;
import com.believe.bike.query.bike.BikeEntry;
import com.believe.bike.query.bike.repositories.BikeEntryRepository;
import com.believe.bike.rest.dto.BikeDto;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p> The describe </p>
 *
 * @author Li Xingping
 */
@RestController
@RequestMapping("/bikes")
public class BikeController {

  @Autowired
  private CommandGateway commandGateway;

  @Autowired
  private BikeEntryRepository bikeEntryRepository;

  @GetMapping({"/"})
  public List<BikeEntry> bikes() {
    return bikeEntryRepository.findAll();
  }

  @GetMapping({"/of_pages"})
  public Page<BikeEntry> users(Pageable pageable) {
    return bikeEntryRepository.findAll(pageable);
  }

  @GetMapping({"/{identifier}"})
  public BikeEntry bikes(@PathVariable("identifier") String identifier) {
    return bikeEntryRepository.findOne(identifier);
  }

  @PostMapping
  public String bikes(@RequestBody BikeDto bikeDto) {
    BikeId identifier = new BikeId();
    commandGateway.sendAndWait(new PutOnMarketCommand(identifier, bikeDto.getBikeNumber(), bikeDto.getPosition()));
    return identifier.getIdentifier();
  }

}
