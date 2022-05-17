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
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.RealmRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
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
    public BaseDataResponse<UserDto> saveUser(UserCreateRequest createRequest) {
        List<UserRepresentation> search = keycloak.realm(UserServiceCoreConfig.realm).users().search(createRequest.getUsername());
        BaseDataResponse dataResponse = new BaseDataResponse();
        if (CollectionUtils.isEmpty(search)) {
            log.debug("User " + createRequest.getUsername() + " will be created");
            Response response = keycloak.realm(UserServiceCoreConfig.realm).users().create(UserDtoMapper.toUserRepresentation(createRequest));
            if (response.getStatus() > 201) {
                dataResponse.setSuccess(false);
                OperationError operationError = new OperationError();
                operationError.setMessage(response.getStatusInfo().getReasonPhrase());
            } else {
                dataResponse.setSuccess(true);
                dataResponse.setData(createRequest);
            }
        } else {
            log.debug("User " + createRequest.getUsername() + " will be updated");
            UserRepresentation user = search.stream().findFirst().get();
            user.setFirstName(createRequest.getFirstName());
            user.setLastName(createRequest.getLastName());
            user.setEmail(createRequest.getEmail());
            keycloak.realm(UserServiceCoreConfig.realm).users().get(user.getId()).update(user);
            dataResponse.setSuccess(true);
            dataResponse.setData(createRequest);
        }
        return dataResponse;
    }
}
