package com.believe.bike.query.bike;

import com.believe.bike.api.bike.BikeStatus;
import com.believe.bike.core.model.Domain;
import com.believe.bike.core.model.Position;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * <p> The describe </p>
 *
 * @author Li Xingping
 */
@Data
@Entity
@Table(name = "bike")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BikeEntry extends Domain<String> {

  @NotBlank
  @Column(unique = true, nullable = false)
  private String bikeNumber;

  @NotBlank
  @Column(nullable = false, name = "unlock_password")
  private String unlockPassword;

  @NotNull
  @Enumerated(EnumType.STRING)
  private BikeStatus status;

  @Embedded
  @AttributeOverrides({
    @AttributeOverride(name = "lng", column = @Column(name = "lng", scale = 10, precision = 15)),
    @AttributeOverride(name = "lat", column = @Column(name = "lat", scale = 10, precision = 15))
  })
  private Position position;

}
