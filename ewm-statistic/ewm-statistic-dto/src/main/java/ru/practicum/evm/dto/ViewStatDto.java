package ru.practicum.evm.dto;

import lombok.*;
import lombok.extern.jackson.Jacksonized;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ViewStatDto {
    String app;
    String uri;
    Long hits;
}
