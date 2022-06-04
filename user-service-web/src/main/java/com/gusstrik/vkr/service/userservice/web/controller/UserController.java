package com.gusstrik.vkr.service.userservice.web.controller;

import com.gusstrik.vkr.common.dto.BaseDataResponse;
import com.gusstrik.vkr.common.dto.PagingRequestDto;
import com.gusstrik.vkr.common.dto.PagingResponseDto;
import com.gusstrik.vkr.service.userservice.core.UserService;
import com.gusstrik.vkr.service.userservice.dto.UserCreateRequest;
import com.gusstrik.vkr.service.userservice.dto.UserDto;
import com.gusstrik.vkr.service.userservice.dto.UserSearchFilter;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/save",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseDataResponse<UserDto> saveUser (@RequestBody UserCreateRequest createRequest){
        return userService.saveUser(createRequest);
    }

    @PostMapping(value = "/search",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public PagingResponseDto<UserDto> searchUser (@RequestBody PagingRequestDto<UserSearchFilter> requestDto){
        return userService.searchUser(requestDto);
    }

    @PostMapping(value = "/delete",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseDataResponse<?> deleteUser (@RequestParam("username") String username){
        return userService.deleteUser(username);
    }
}
