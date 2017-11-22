package com.believe.bike.api.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p> The describe </p>
 *
 * @author Li Xingping
 */
@Getter
@AllArgsConstructor
public enum PaymentChannel {

  ALI_PAY("com.believe.bike.paymentChannel.AliPay"),
  WX_PAY("com.believe.bike.paymentChannel.WxPay"),
  PLATFORM_PAY("com.believe.bike.paymentChannel.PlatformPay");

  private String code;
}
