package ru.practicum.ewm.location.service;

import ru.practicum.ewm.location.dto.LocationDto;
import ru.practicum.ewm.location.dto.NewLocationDto;

public interface LocationService {
    LocationDto create(NewLocationDto newLocationDto);
}
