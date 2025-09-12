package org.playground.shoppingcart.mappers;

import org.mapstruct.Mapper;
import org.playground.shoppingcart.dtos.RegisterUserRequest;
import org.playground.shoppingcart.dtos.UserDto;
import org.playground.shoppingcart.entities.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);

    User toEntity(RegisterUserRequest request);
}
