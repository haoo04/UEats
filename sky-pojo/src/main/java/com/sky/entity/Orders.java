package com.sky.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Order
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Orders implements Serializable {

    /**
     * Order status 1 Pending payment 2 Pending order 3 Received 4 Delivering 5 Completed 6 Cancelled 7 Refunded
     */
    public static final Integer PENDING_PAYMENT = 1;
    public static final Integer TO_BE_CONFIRMED = 2;
    public static final Integer CONFIRMED = 3;
    public static final Integer DELIVERY_IN_PROGRESS = 4;
    public static final Integer COMPLETED = 5;
    public static final Integer CANCELLED = 6;

    /**
     * Payment status 0 Unpaid 1 Paid 2 Refunded
     */
    public static final Integer UN_PAID = 0;
    public static final Integer PAID = 1;
    public static final Integer REFUND = 2;

    private static final long serialVersionUID = 1L;

    private Long id;

    // Order number
    private String number;

    // Order status 1 Pending payment 2 Pending order 3 Received 4 Delivering 5 Completed 6 Cancelled 7 Refunded
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

    // Payment status 0 Unpaid 1 Paid 2 Refunded
    private Integer payStatus;

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

    // Order cancellation reason
    private String cancelReason;

    // Order rejection reason
    private String rejectionReason;

    // Order cancellation time
    private LocalDateTime cancelTime;

    // Estimated delivery time
    private LocalDateTime estimatedDeliveryTime;

    // Delivery status 1 Immediately send out 0 Select specific time
    private Integer deliveryStatus;

    // Delivery time
    private LocalDateTime deliveryTime;

    // Packaging fee
    private int packAmount;

    // Tableware quantity
    private int tablewareNumber;

    // Tableware quantity status 1 Provide according to meal quantity 0 Select specific quantity
    private Integer tablewareStatus;
}
