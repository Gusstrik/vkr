package com.gusstrik.vkr.service.userservice.core.mapper;

import com.gusstrik.vkr.service.userservice.dto.UserDto;
import org.keycloak.representations.idm.UserRepresentation;

public class UserDtoMapper {
    public static UserDto toUserDto(UserRepresentation userRepresentation){
        UserDto userDto = new UserDto();
        userDto.setUsername(userRepresentation.getUsername());
        userDto.setFirstName(userRepresentation.getFirstName());
        userDto.setLastName(userRepresentation.getLastName());
        userDto.setEmail(userRepresentation.getEmail());
        return userDto;
    }
}
