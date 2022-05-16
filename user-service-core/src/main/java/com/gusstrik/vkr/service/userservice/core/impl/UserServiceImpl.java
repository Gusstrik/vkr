package com.gusstrik.vkr.service.userservice.core.impl;

import com.gusstrik.vkr.common.dto.BaseDataResponse;
import com.gusstrik.vkr.common.dto.OperationError;
import com.gusstrik.vkr.common.dto.PagingRequestDto;
import com.gusstrik.vkr.common.dto.PagingResponseDto;
import com.gusstrik.vkr.service.userservice.core.UserService;
import com.gusstrik.vkr.service.userservice.core.config.UserServiceCoreConfig;
import com.gusstrik.vkr.service.userservice.core.mapper.UserDtoMapper;
import com.gusstrik.vkr.service.userservice.dto.UserCreateRequest;
import com.gusstrik.vkr.service.userservice.dto.UserDto;
import com.gusstrik.vkr.service.userservice.dto.UserSearchFilter;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final Keycloak keycloak;

    public UserServiceImpl(Keycloak keycloak) {
        this.keycloak = keycloak;
    }


    @Override
    public PagingResponseDto<UserDto> searchUser(PagingRequestDto<UserSearchFilter> searchFilter) {
        UserSearchFilter filter = searchFilter.getData();
        List<UserRepresentation> result;
        if (filter == null)
            result = keycloak.realm(UserServiceCoreConfig.realm).users().search(null
                    , null
                    , null
                    , null, searchFilter.getPage() * searchFilter.getLimit(), searchFilter.getLimit());
        else
            result = keycloak.realm(UserServiceCoreConfig.realm).users().search(filter.getUsername()
                    , filter.getFirstName()
                    , filter.getLastName()
                    , filter.getEmail(), searchFilter.getPage() * searchFilter.getLimit(), searchFilter.getLimit());
        PagingResponseDto<UserDto> responseDto = new PagingResponseDto<>();
        responseDto.setData(result.stream().map(UserDtoMapper::toUserDto).collect(Collectors.toList()));
        responseDto.setSuccess(true);
        responseDto.setPage(searchFilter.getPage());
        responseDto.setLimit(searchFilter.getLimit());
        return responseDto;
    }

    @Override
    public BaseDataResponse<UserDto> createUser(UserCreateRequest createRequest) {
        Response response = keycloak.realm(UserServiceCoreConfig.realm).users().create(UserDtoMapper.toUserRepresentation(createRequest));
        BaseDataResponse dataResponse = new BaseDataResponse();
        if (response.getStatus()>201){
            dataResponse.setSuccess(false);
            OperationError operationError = new OperationError();
            operationError.setMessage(response.getStatusInfo().getReasonPhrase());
        }else{
            dataResponse.setSuccess(true);
            dataResponse.setData(createRequest);
        }
        return dataResponse;
    }
}
