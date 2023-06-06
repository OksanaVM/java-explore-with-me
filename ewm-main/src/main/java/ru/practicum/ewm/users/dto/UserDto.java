package ru.practicum.ewm.users.dto;

import lombok.Value;

@Value
public class UserDto {
    Long id;
    String name;
    String email;
}
