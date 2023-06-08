package ru.practicum.ewm.requests.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.requests.model.Request;


import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {

    List<Request> findAllByRequesterId(Long requesterId);

    List<Request> findAllByEventId(Long eventId);

    Request findByRequesterIdAndId(Long requesterId, Long id);

   }
