package com.believe.bike.query.transaction.repositories;

import com.believe.bike.core.ModelRepository;
import com.believe.bike.query.transaction.PaymentTransaction;

/**
 * <p> The describe </p>
 *
 * @author Li Xingping
 */
public interface PaymentTransactionRepository extends ModelRepository<PaymentTransaction, String> {
  PaymentTransaction findByTradeNo(String tradeNo);
}
