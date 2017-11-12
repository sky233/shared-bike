package com.believe.bike.query.rent;

import com.believe.bike.api.rent.RentTxStatus;
import com.believe.bike.core.model.Domain;
import com.believe.bike.query.user.UserEntry;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * <p> The describe </p>
 *
 * @author Li Xingping
 */
@Data
@Entity
@Table(name = "bike_rent")
@EqualsAndHashCode(callSuper = true, exclude = {"renter"})
@ToString(callSuper = true, exclude = {"renter"})
public class BikeRentEntry extends Domain<String> {

  @JsonIgnore
  @ManyToOne
  @JoinColumn(nullable = false, name = "renter_id")
  private UserEntry renter;

  @NotNull
  @Enumerated(EnumType.STRING)
  private RentTxStatus status;

  @Column(nullable = false, name = "bike_id")
  private String bikeId;

}
