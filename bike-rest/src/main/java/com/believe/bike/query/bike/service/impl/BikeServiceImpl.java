package com.believe.bike.query.bike.service.impl;

import com.believe.bike.query.bike.BikeEntry;
import com.believe.bike.query.bike.repositories.BikeEntryRepository;
import com.believe.bike.query.bike.service.BikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p> The describe </p>
 *
 * @author Li Xingping
 */
@Service
@Transactional
public class BikeServiceImpl implements BikeService {

  private final BikeEntryRepository bikeEntryRepository;

  @Autowired
  public BikeServiceImpl(BikeEntryRepository bikeEntryRepository) {
    this.bikeEntryRepository = bikeEntryRepository;
  }

  @Override
  public BikeEntry save(BikeEntry bikeEntry) {
    return bikeEntryRepository.save(bikeEntry);
  }
}
