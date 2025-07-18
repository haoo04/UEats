package com.sky.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrdersCancelDTO implements Serializable {

    private Long id;
    // Order cancellation reason
    private String cancelReason;

}
