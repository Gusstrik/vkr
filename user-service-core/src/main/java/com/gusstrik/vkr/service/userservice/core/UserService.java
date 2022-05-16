package com.gusstrik.vkr.service.userservice.core;

import com.gusstrik.vkr.common.dto.BaseDataResponse;
import com.gusstrik.vkr.common.dto.PagingRequestDto;
import com.gusstrik.vkr.common.dto.PagingResponseDto;
import com.gusstrik.vkr.service.userservice.dto.UserCreateRequest;
import com.gusstrik.vkr.service.userservice.dto.UserDto;
import com.gusstrik.vkr.service.userservice.dto.UserSearchFilter;
import org.keycloak.representations.idm.UserRepresentation;


import java.util.List;

public interface UserService {

    PagingResponseDto<UserDto> searchUser(PagingRequestDto<UserSearchFilter> searchFilter);

    BaseDataResponse<UserDto> createUser (UserCreateRequest createRequest);
}
