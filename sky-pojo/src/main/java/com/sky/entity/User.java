package com.sky.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    // WeChat user unique identifier
    private String openid;

    // Name
    private String name;

    // Phone number
    private String phone;

    // Gender 0 Female 1 Male
    private String sex;

    // ID number
    private String idNumber;

    // Avatar
    private String avatar;

    // Registration time
    private LocalDateTime createTime;
}
