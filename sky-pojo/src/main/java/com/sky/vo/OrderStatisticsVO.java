package com.sky.vo;

import lombok.Data;
import java.io.Serializable;

@Data
public class OrderStatisticsVO implements Serializable {
    // Number of orders to be received
    private Integer toBeConfirmed;

    // Number of orders to be delivered
    private Integer confirmed;

    // Number of orders in progress
    private Integer deliveryInProgress;
}
