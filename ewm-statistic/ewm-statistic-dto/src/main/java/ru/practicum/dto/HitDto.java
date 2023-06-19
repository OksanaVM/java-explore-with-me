package ru.practicum.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HitDto {
    @NotBlank
    @Size(max = 256)
    private String app;
    @NotBlank
    @Size(max = 512)
    private String uri;
    @NotBlank
    @Size(max = 64)
    private String ip;
    private String timestamp;
}
