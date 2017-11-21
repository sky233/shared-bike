package com.believe.bike.query.transaction.service.impl;

import com.believe.bike.query.transaction.UserTransaction;
import com.believe.bike.query.transaction.repositories.UserTransactionRepository;
import com.believe.bike.query.transaction.service.UserTransactionService;
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
public class UserTransactionServiceImpl implements UserTransactionService {

  private final UserTransactionRepository repository;

  @Autowired
  public UserTransactionServiceImpl(UserTransactionRepository repository) {
    this.repository = repository;
  }

  @Override
  public UserTransaction save(UserTransaction userEntry) {
    return repository.save(userEntry);
  }
}
