package ru.practicum.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.user.repository.UserRepository;
import ru.practicum.user.dto.NewUserDto;
import ru.practicum.user.model.MapperUser;
import ru.practicum.user.model.User;

import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.user.model.MapperUser.toUser;
import static ru.practicum.user.model.MapperUser.toUserDto;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    /**
     * Добавление нового пользователя
     */
    @Transactional
    public NewUserDto createUser(NewUserDto newUserDto) {
        User user = toUser(newUserDto);
        return toUserDto(repository.save(user));
    }

    /**
     * Получение информации о пользователях
     */
    public List<NewUserDto> getUsers(List<Long> ids, Integer from, Integer size) {
        PageRequest page = PageRequest.of(from, size);
        if (ids == null) {
            return repository.findAll(page).stream()
                    .map(MapperUser::toUserDto)
                    .collect(Collectors.toList());
        } else {
            return repository.findByIdIn(ids, page).stream()
                    .map(MapperUser::toUserDto)
                    .collect(Collectors.toList());
        }
    }

    /**
     * Удаление пользователя
     */
    @Transactional
    public void deleteUser(Long id) {
        repository.deleteById(id);
    }
}
