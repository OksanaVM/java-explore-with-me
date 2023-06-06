package ru.practicum.ewm.requests.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.ewm.requests.model.Request;
import ru.practicum.ewm.requests.model.RequestStat;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {

    List<Request> findAllByRequesterId(Long requesterId);

    List<Request> findAllByEventId(Long eventId);

    Request findByRequesterIdAndId(Long requesterId, Long id);

    @Query("SELECT new RequestStat(r.eventId, COUNT(r.eventId)) " +
            "FROM Request r " +
            "WHERE r.eventId IN :ids " +
            "GROUP BY r.eventId")
    List<RequestStat> getRequestsStats(@Param("ids") List<Long> ids);
}
