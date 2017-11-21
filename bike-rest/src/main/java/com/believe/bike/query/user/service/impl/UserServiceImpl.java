package com.believe.bike.query.user.service.impl;

import com.believe.bike.query.user.AccountEntry;
import com.believe.bike.query.user.UserEntry;
import com.believe.bike.query.user.repositories.AccountEntryRepository;
import com.believe.bike.query.user.repositories.UserEntryRepository;
import com.believe.bike.query.user.service.UserService;
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
public class UserServiceImpl implements UserService {

  private final UserEntryRepository repository;
  private final AccountEntryRepository accountEntryRepository;

  @Autowired
  public UserServiceImpl(UserEntryRepository repository, AccountEntryRepository accountEntryRepository) {
    this.repository = repository;
    this.accountEntryRepository = accountEntryRepository;
  }

  @Override
  public UserEntry save(UserEntry userEntry) {
    return repository.save(userEntry);
  }

  @Override
  public void saveAccount(AccountEntry accountEntry) {
    accountEntryRepository.save(accountEntry);
  }
}
