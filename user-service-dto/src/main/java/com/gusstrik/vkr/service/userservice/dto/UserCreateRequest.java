package com.gusstrik.vkr.service.userservice.dto;

import lombok.Data;

@Data
public class UserCreateRequest extends UserDto{
    private String temporaryPassword;
}
