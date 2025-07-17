package com.sky.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "sky.wechat")
@Data
public class WeChatProperties {

    private String appid; // WeChat Mini Program AppID
    private String secret; // WeChat Mini Program Secret
    private String mchid; // Merchant ID
    private String mchSerialNo; // Merchant API Certificate Serial Number
    private String privateKeyFilePath; // Merchant Private Key File
    private String apiV3Key; // Certificate Decryption Key
    private String weChatPayCertFilePath; // Platform Certificate
    private String notifyUrl; // Payment Success Callback Address
    private String refundNotifyUrl; // Refund Success Callback Address

}
