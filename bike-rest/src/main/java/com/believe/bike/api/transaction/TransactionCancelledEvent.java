package com.believe.bike.api.transaction;

import com.believe.bike.api.user.UserId;
import lombok.Value;

import java.io.Serializable;

/**
 * <p> The describe </p>
 *
 * @author Li Xingping
 */
@Value
public class TransactionCancelledEvent implements Serializable {

  private TransactionId identifier;
  private String tradeNo;
  private UserId userId;
  private String remark;

}
