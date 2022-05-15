package com.gusstrik.vkr.service.userservice.core.impl;

import com.gusstrik.vkr.service.userservice.core.UserService;
import org.keycloak.admin.client.Keycloak;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {\

    private final Keycloak keycloak;

    public UserServiceImpl(Keycloak keycloak) {
        this.keycloak = keycloak;
    }


    @Override
    public void createUser() {
        keycloak.realm("VKR").users().search();
    }
}
