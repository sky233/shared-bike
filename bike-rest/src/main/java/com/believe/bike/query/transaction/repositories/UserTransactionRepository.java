package com.believe.bike.query.transaction.repositories;

import com.believe.bike.core.ModelRepository;
import com.believe.bike.query.transaction.UserTransaction;

/**
 * <p> The describe </p>
 *
 * @author Li Xingping
 */
public interface UserTransactionRepository extends ModelRepository<UserTransaction, String> {
  UserTransaction findByTradeNo(String tradeNo);
}
