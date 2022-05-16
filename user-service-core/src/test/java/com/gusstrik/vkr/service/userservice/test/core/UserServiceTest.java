package com.gusstrik.vkr.service.userservice.test.core;

import com.gusstrik.vkr.common.dto.BaseDataResponse;
import com.gusstrik.vkr.common.dto.PagingRequestDto;
import com.gusstrik.vkr.service.userservice.core.UserService;
import com.gusstrik.vkr.service.userservice.core.config.UserServiceCoreConfig;
import com.gusstrik.vkr.service.userservice.dto.UserCreateRequest;
import com.gusstrik.vkr.service.userservice.dto.UserDto;
import com.gusstrik.vkr.service.userservice.dto.UserSearchFilter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.stream.Collectors;

@ExtendWith(SpringExtension.class)
@PropertySource("classpath:test-app.properties")
@Import(UserServiceCoreConfig.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void searchUserTest(){
        UserSearchFilter userSearchFilter = new UserSearchFilter();
        PagingRequestDto<UserSearchFilter> request = new PagingRequestDto<>();
        System.out.println(userService.searchUser(request).getData().stream().map(UserDto::getUsername).collect(Collectors.toList()));
    }

    @Test
    public void createUserTest(){
        UserCreateRequest createRequest = new UserCreateRequest();
        createRequest.setEmail("test@test.ru");
        createRequest.setUsername("user2");
        createRequest.setFirstName("Иван");
        createRequest.setLastName("Иванович");
        createRequest.setTemporaryPassword("gtn");
        BaseDataResponse<UserDto> response = userService.createUser(createRequest);
        System.out.println(response.getData());
    }
}
