package com.believe.bike.api.payment;

import lombok.Getter;

/**
 * <p> The transaction status. </p>
 *
 * @author Li Xingping
 */
@Getter
public enum PaymentStatus {

  PENDING,

  COMPLETE,

  CANCEL
}
