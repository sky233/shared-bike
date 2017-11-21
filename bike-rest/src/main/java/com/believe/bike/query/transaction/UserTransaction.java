package com.believe.bike.query.transaction;

import com.believe.bike.api.transaction.TransactionStartedEvent;
import com.believe.bike.api.transaction.TransactionStatus;
import com.believe.bike.api.transaction.TransactionType;
import com.believe.bike.core.model.Domain;
import com.believe.bike.query.user.UserEntry;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * <p> The describe </p>
 *
 * @author Li Xingping
 */
@NoArgsConstructor
@Data
@Entity
@Table(name = "user_transaction")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserTransaction extends Domain<String> {

  @JsonIgnore
  @ManyToOne
  @JoinColumn(nullable = false, name = "transactor_id")
  private UserEntry transactor;

  @Column(nullable = false, unique = true, name = "trade_no")
  private String tradeNo;

  @Enumerated(EnumType.STRING)
  private TransactionType type;

  @Enumerated(EnumType.STRING)
  private TransactionStatus status;

  @Column(nullable = false)
  private BigDecimal amount;

  private String remark;

  public UserTransaction(TransactionStartedEvent event, UserEntry transactor) {
    this.setId(event.getIdentifier().getIdentifier());
    this.transactor = transactor;
    this.tradeNo = event.getTradeNo();
    this.type = event.getType();
    this.amount = event.getAmount();
    this.status = event.getStatus();
    this.remark = event.getRemark();

  }
}
