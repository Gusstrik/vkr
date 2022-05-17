package com.gusstrik.vkr.service.userservice.core.mapper;

import com.gusstrik.vkr.service.userservice.dto.UserCreateRequest;
import com.gusstrik.vkr.service.userservice.dto.UserDto;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;

public class UserDtoMapper {
    public static UserDto toUserDto(UserRepresentation userRepresentation) {
        UserDto userDto = new UserDto();
        userDto.setUsername(userRepresentation.getUsername());
        userDto.setFirstName(userRepresentation.getFirstName());
        userDto.setLastName(userRepresentation.getLastName());
        userDto.setEmail(userRepresentation.getEmail());
        return userDto;
    }

    public static UserRepresentation toUserRepresentation(UserCreateRequest createRequest) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setEmail(createRequest.getEmail());
        userRepresentation.setFirstName(createRequest.getFirstName());
        userRepresentation.setLastName(createRequest.getLastName());
        userRepresentation.setUsername(createRequest.getUsername());
        if (!ObjectUtils.isEmpty(createRequest.getTemporaryPassword())) {
            CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
            credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
            credentialRepresentation.setTemporary(true);
            credentialRepresentation.setValue(createRequest.getTemporaryPassword());
            userRepresentation.setCredentials(Arrays.asList(credentialRepresentation));
        }
        return userRepresentation;
    }
}
