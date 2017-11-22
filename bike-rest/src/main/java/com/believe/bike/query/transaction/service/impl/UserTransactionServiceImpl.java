package com.believe.bike.query.transaction.service.impl;

import com.believe.bike.query.transaction.PaymentTransaction;
import com.believe.bike.query.transaction.UserTransaction;
import com.believe.bike.query.transaction.repositories.PaymentTransactionRepository;
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
  private final PaymentTransactionRepository paymentRepository;

  @Autowired
  public UserTransactionServiceImpl(UserTransactionRepository repository, PaymentTransactionRepository paymentRepository) {
    this.repository = repository;
    this.paymentRepository = paymentRepository;
  }

  @Override
  public UserTransaction save(UserTransaction userEntry) {
    return repository.save(userEntry);
  }

  @Override
  public PaymentTransaction save(PaymentTransaction paymentTransaction) {
    return paymentRepository.save(paymentTransaction);
  }
}
