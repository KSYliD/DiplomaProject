package com.example.diploma.mapper;

import com.example.diploma.dto.UserDto;
import com.example.diploma.dto.auth.RegisterRequest;
import com.example.diploma.model.user.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User userDtoToUser(UserDto userDto);
    UserDto userToUserDto(User user);
    User registerRequestToUser(RegisterRequest registerRequest);
}
