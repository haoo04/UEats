package com.sky.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Address book
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressBook implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    // User id
    private Long userId;

    // Consignee
    private String consignee;

    // Phone number
    private String phone;

    // Gender 0 Female 1 Male
    private String sex;

    // Provincial division code
    private String provinceCode;

    // Provincial name
    private String provinceName;

    // City division code
    private String cityCode;

    // City name
    private String cityName;

    // District division code
    private String districtCode;

    // District name
    private String districtName;

    // Detailed address
    private String detail;

    // Label
    private String label;

    // Whether it is default 0 No 1 Yes
    private Integer isDefault;
}
