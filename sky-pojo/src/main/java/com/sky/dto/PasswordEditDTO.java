package com.sky.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PasswordEditDTO implements Serializable {

    // Employee id
    private Long empId;

    // Old password
    private String oldPassword;

    // New password
    private String newPassword;

}
