package com.sky.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrdersConfirmDTO implements Serializable {

    private Long id;
    // Order status 1 Pending payment 2 Pending order 3 Received 4 Delivering 5 Completed 6 Cancelled 7 Refunded
    private Integer status;

}
