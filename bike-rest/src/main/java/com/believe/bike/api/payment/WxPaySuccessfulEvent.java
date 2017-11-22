package com.believe.bike.api.payment;

import com.believe.bike.api.transaction.TransactionId;
import com.believe.bike.api.user.UserId;
import lombok.Value;

import java.math.BigDecimal;

/**
 * <p> https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_7 </p>
 * <p>
 * <xml>
 * <appid><![CDATA[wx2421b1c4370ec43b]]></appid>
 * <attach><![CDATA[Test]]></attach>
 * <bank_type><![CDATA[CFT]]></bank_type>
 * <fee_type><![CDATA[CNY]]></fee_type>
 * <is_subscribe><![CDATA[Y]]></is_subscribe>
 * <mch_id><![CDATA[10000100]]></mch_id>
 * <nonce_str><![CDATA[5d2b6c2a8db53831f7eda20af46e531c]]></nonce_str>
 * <openid><![CDATA[oUpF8uMEb4qRXf22hE3X68TekukE]]></openid>
 * <out_trade_no><![CDATA[1409811653]]></out_trade_no>
 * <result_code><![CDATA[SUCCESS]]></result_code>
 * <return_code><![CDATA[SUCCESS]]></return_code>
 * <sign><![CDATA[B552ED6B279343CB493C5DD0D78AB241]]></sign>
 * <sub_mch_id><![CDATA[10000100]]></sub_mch_id>
 * <time_end><![CDATA[20140903131540]]></time_end>
 * <total_fee>1</total_fee>
 * <trade_type><![CDATA[JSAPI]]></trade_type>
 * <transaction_id><![CDATA[1004400740201409030005092168]]></transaction_id>
 * </xml>
 *
 * @author Li Xingping
 */
@Value
public class WxPaySuccessfulEvent {

  private PaymentId paymentId;
  private TransactionId transactionId;
  private UserId userId;

  /**
   * Wechat pay return.
   */
  private String out_trade_no;
  private String transaction_id;
  private String bank_type;
  private String openid;
  private String time_end;
  private BigDecimal total_fee;

}
