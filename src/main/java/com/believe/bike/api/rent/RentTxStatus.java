package com.believe.bike.api.rent;

import lombok.Getter;

/**
 * <p> The bike Rent transaction status. </p>
 *
 * @author Li Xingping
 */
@Getter
public enum RentTxStatus {

  PENDING,

  UN_PAID,

  COMPLETE,

  CANCEL
}
