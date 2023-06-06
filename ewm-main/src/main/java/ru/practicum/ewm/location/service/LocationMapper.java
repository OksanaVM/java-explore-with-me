package ru.practicum.ewm.location.service;

import ru.practicum.ewm.location.dto.LocationDto;
import ru.practicum.ewm.location.dto.NewLocationDto;
import ru.practicum.ewm.location.model.Location;

public class LocationMapper {

    private LocationMapper() {
    }

    public static Location toLocation(NewLocationDto newLocationDto) {
        Location location = new Location();

        location.setLat(newLocationDto.getLat());
        location.setLon(newLocationDto.getLon());

        return location;
    }

    public static LocationDto toCreatedLocationDto(Location location) {
        return new LocationDto(location.getId(), location.getLat(), location.getLon());
    }
}
