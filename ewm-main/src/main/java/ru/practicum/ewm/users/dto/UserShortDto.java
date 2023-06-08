package ru.practicum.ewm.users.dto;

import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class UserShortDto {
    Long id;
    String name;
}