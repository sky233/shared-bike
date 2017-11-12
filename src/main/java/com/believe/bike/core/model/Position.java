package com.believe.bike.core.model;

import lombok.Data;

import javax.persistence.Embeddable;
import java.math.BigDecimal;

/**
 * <p> The describe </p>
 *
 * @author Li Xingping
 */
@Data
@Embeddable
public class Position {

  //经度
  private BigDecimal longitude;
  //纬度
  private BigDecimal latitude;
}
