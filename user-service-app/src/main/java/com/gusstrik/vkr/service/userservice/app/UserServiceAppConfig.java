package com.gusstrik.vkr.service.userservice.app;

import com.gusstrik.vkr.service.userservice.core.config.UserServiceCoreConfig;
import com.gusstrik.vkr.service.userservice.web.config.UserServiceWebConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({UserServiceWebConfig.class, UserServiceCoreConfig.class})
public class UserServiceAppConfig {
}
