package com.gusstrik.vkr.service.userservice.dto;

import lombok.Data;

@Data
public class UserSearchFilter {

    private String username;
    private String firstName;
    private String lastName;
    private String email;
}
