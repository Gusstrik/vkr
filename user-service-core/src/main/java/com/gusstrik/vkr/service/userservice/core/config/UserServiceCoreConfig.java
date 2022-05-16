package com.gusstrik.vkr.service.userservice.core.config;

import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.authorization.client.AuthzClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


import java.io.IOException;
import java.io.InputStream;

import static com.gusstrik.vkr.common.utils.FileUtils.getInputStream;

@Configuration
@Slf4j
@ComponentScan(basePackages = {"com.gusstrik.vkr.service.userservice.core.impl"})
public class UserServiceCoreConfig {

    public static String realm;

    @Bean
    public Keycloak initKeycloakClient(@Value("${keycloak.admin.path}") String filePath) {
        AuthzClient authzClient = null;
        try (InputStream configInput = getInputStream(filePath)) {
            authzClient = AuthzClient.create(configInput);
        } catch (IOException e) {
            log.error("Error reading keycloak admin config", e);
        }
        realm = authzClient.getConfiguration().getRealm();
        return Keycloak.getInstance(authzClient.getConfiguration().getAuthServerUrl()
                , authzClient.getConfiguration().getRealm()
                , authzClient.getConfiguration().getResource()
                , authzClient.obtainAccessToken().getToken());
    }
}
