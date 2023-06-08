package ru.practicum.location;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.location.Location;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long> {
    List<Location> findByLatAndLon(Float lat, Float lon);
}
