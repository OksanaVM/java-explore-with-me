package ru.practicum.ewm.users.service;

import ru.practicum.ewm.users.dto.NewUserDto;
import ru.practicum.ewm.users.dto.UserDto;
import ru.practicum.ewm.users.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {
    private UserMapper() {
    }

    public static User toUser(NewUserDto newUserDto) {
        User user = new User();

        user.setName(newUserDto.getName());
        user.setEmail(newUserDto.getEmail());

        return user;
    }

    public static UserDto toUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail());
    }

    public static List<UserDto> toUserDto(List<User> users) {
        return users
                .stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }
}
