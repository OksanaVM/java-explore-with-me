package ru.practicum.ewm.users.service;

import ru.practicum.ewm.users.dto.NewUserDto;

import java.util.List;

public interface UserService {
    NewUserDto createUser(NewUserDto newUserDto);

    List<NewUserDto> getUsers(List<Long> ids, Integer from, Integer size);

    void deleteUser(Long id);
}
