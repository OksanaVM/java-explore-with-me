package ru.practicum.evm.dto;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class OutputHitDto {
    Long id;
    String app;
    String uri;
    String ip;
    LocalDateTime timestamp;
}