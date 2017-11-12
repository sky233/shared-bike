package com.believe.bike.query.bike;

import com.believe.bike.core.model.Record;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * <p> The describe </p>
 *
 * @author Li Xingping
 */
@Data
@Entity
@Table(name = "bike_rented_record")
@EqualsAndHashCode(callSuper = true, exclude = {"bike"})
@ToString(callSuper = true, exclude = {"bike"})
public class BikeRentedRecord extends Record<String> {

  private String renter;

  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "bike_id")
  private BikeEntry bike;

}
