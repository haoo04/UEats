package com.sky.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Order overview data
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderOverViewVO implements Serializable {
    // Number of orders to be received
    private Integer waitingOrders;

    // Number of orders to be delivered
    private Integer deliveredOrders;

    // Number of completed orders
    private Integer completedOrders;

    // Number of cancelled orders
    private Integer cancelledOrders;

    // All orders
    private Integer allOrders;
}
