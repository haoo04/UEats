package com.sky.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Data overview
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusinessDataVO implements Serializable {

    private Double turnover;// Turnover

    private Integer validOrderCount;// Valid order count

    private Double orderCompletionRate;// Order completion rate

    private Double unitPrice;// Average customer unit price

    private Integer newUsers;// New user count

}
