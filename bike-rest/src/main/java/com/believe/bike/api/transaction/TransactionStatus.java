package com.believe.bike.api.transaction;

import lombok.Getter;

/**
 * <p> The transaction status. </p>
 *
 * @author Li Xingping
 */
@Getter
public enum TransactionStatus {

  PENDING,

  REJECT,

  COMPLETE,

  CANCEL
}
