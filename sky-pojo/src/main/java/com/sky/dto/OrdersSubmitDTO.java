package com.sky.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrdersSubmitDTO implements Serializable {
    // Address book id
    private Long addressBookId;
    // Payment method
    private int payMethod;
    // Remark
    private String remark;
    // Estimated delivery time
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime estimatedDeliveryTime;
    // Delivery status 1 Immediately send out 0 Select specific time
    private Integer deliveryStatus;
    // Tableware quantity
    private Integer tablewareNumber;
    // Tableware quantity status 1 Provide according to meal quantity 0 Select specific quantity
    private Integer tablewareStatus;
    // Packaging fee
    private Integer packAmount;
    // Total amount
    private BigDecimal amount;
}
