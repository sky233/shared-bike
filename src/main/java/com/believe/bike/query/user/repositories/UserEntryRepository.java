package com.believe.bike.query.user.repositories;

import com.believe.bike.core.ModelRepository;
import com.believe.bike.query.user.UserEntry;

/**
 * <p> The describe </p>
 *
 * @author Li Xingping
 */
public interface UserEntryRepository extends ModelRepository<UserEntry, String> {
  UserEntry findByCellNo(String cellNo);
}
