package com.sky.dto;

import com.sky.entity.OrderDetail;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrdersDTO implements Serializable {

    private Long id;

    // Order number
    private String number;

    // Order status 1 Pending payment, 2 Pending order, 3 Received, 4 Delivering, 5 Completed, 6 Cancelled, 7 Refunded
    private Integer status;

    // User id
    private Long userId;

    // Address id
    private Long addressBookId;

    // Order time
    private LocalDateTime orderTime;

    // Checkout time
    private LocalDateTime checkoutTime;

    // Payment method 1 WeChat, 2 Alipay
    private Integer payMethod;

    // Actual amount received
    private BigDecimal amount;

    // Remark
    private String remark;

    // User name
    private String userName;

    // Phone number
    private String phone;

    // Address
    private String address;

    // Consignee
    private String consignee;

    private List<OrderDetail> orderDetails;

}
