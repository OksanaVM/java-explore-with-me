package ru.practicum.ewm.users.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.users.dto.NewUserDto;
import ru.practicum.ewm.users.dto.UserDto;
import ru.practicum.ewm.users.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UsersController {
    private final UserService userService;

    @PostMapping("/admin/users")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@RequestBody @Valid NewUserDto newUserDto) {
        return userService.create(newUserDto);
    }

    @GetMapping("/admin/users")
    public List<UserDto> getUsers(
            @RequestParam(value = "ids", required = false) List<Long> ids,
            @RequestParam(value = "from", required = false, defaultValue = "0") Integer from,
            @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
        return userService.getUsers(ids, from, size);
    }

    @DeleteMapping("/admin/users/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategoryById(@PathVariable("userId") Long userId) {
        userService.deleteUserById(userId);
    }
}
