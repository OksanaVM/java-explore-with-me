package ru.practicum.ewm.location.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.location.dto.LocationDto;
import ru.practicum.ewm.location.dto.NewLocationDto;
import ru.practicum.ewm.location.model.Location;
import ru.practicum.ewm.location.repository.LocationRepository;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    @Override
    @Transactional
    public LocationDto create(NewLocationDto newLocationDto) {
        Location location = LocationMapper.toLocation(newLocationDto);

        return LocationMapper.toCreatedLocationDto(locationRepository.save(location));
    }
}
