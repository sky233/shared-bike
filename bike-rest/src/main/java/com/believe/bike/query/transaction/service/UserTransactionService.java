package com.believe.bike.query.transaction.service;

import com.believe.bike.query.transaction.PaymentTransaction;
import com.believe.bike.query.transaction.UserTransaction;

/**
 * <p> The describe </p>
 *
 * @author Li Xingping
 */
public interface UserTransactionService {
  UserTransaction save(UserTransaction userEntry);

  PaymentTransaction save(PaymentTransaction paymentTransaction);
}
