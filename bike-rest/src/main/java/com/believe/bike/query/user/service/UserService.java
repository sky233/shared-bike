package com.believe.bike.query.user.service;

import com.believe.bike.query.user.AccountEntry;
import com.believe.bike.query.user.UserEntry;

/**
 * <p> The describe </p>
 *
 * @author Li Xingping
 */
public interface UserService {

  UserEntry save(UserEntry userEntry);

  void saveAccount(AccountEntry accountEntry);

}
