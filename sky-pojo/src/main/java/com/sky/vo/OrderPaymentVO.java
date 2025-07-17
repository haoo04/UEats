package com.sky.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderPaymentVO implements Serializable {

    private String nonceStr; // Random string
    private String paySign; // Signature
    private String timeStamp; // Timestamp
    private String signType; // Signature algorithm
    private String packageStr; // prepay_id parameter value returned by the unified order interface

}
