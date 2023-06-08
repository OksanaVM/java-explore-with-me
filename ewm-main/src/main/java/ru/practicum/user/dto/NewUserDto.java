package ru.practicum.user.dto;

import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Builder
public class NewUserDto {
    private Long id;
    @NotBlank
    @Length(min = 2, max = 250)
    private String name;
    @Email
    @NotBlank
    @Length(min = 6, max = 254)
    private String email;
}
