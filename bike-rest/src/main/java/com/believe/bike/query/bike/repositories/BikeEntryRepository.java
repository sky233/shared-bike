package com.believe.bike.query.bike.repositories;

import com.believe.bike.query.bike.BikeEntry;
import com.believe.bike.core.ModelRepository;

/**
 * <p> The describe </p>
 *
 * @author Li Xingping
 */
public interface BikeEntryRepository extends ModelRepository<BikeEntry, String> {

  BikeEntry findByBikeNumber(String bikeNumber);
}
