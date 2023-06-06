package ru.practicum.evm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.evm.model.Hit;
import ru.practicum.evm.model.ViewStat;

import java.time.LocalDateTime;
import java.util.List;

public interface HitRepository extends JpaRepository<Hit, Long> {

    @Query("SELECT new ViewStat(h.uri, h.app, COUNT(DISTINCT h.ip)) " +
            "FROM Hit h " +
            "WHERE h.timestamp BETWEEN :start AND :end " +
            "AND h.uri IN :uris " +
            "GROUP BY h.app, h.uri " +
            "ORDER BY COUNT(h.ip) DESC")
    List<ViewStat> getViewStatsByUniqIp(@Param("uris") List<String> uris,
                                        @Param("start") LocalDateTime start,
                                        @Param("end") LocalDateTime end);

    @Query("SELECT new ViewStat(h.uri, h.app, COUNT(h.ip)) " +
            "FROM Hit h " +
            "WHERE h.timestamp BETWEEN :start AND :end " +
            "AND h.uri IN :uris " +
            "GROUP BY h.app, h.uri " +
            "ORDER BY COUNT(h.ip) DESC")
    List<ViewStat> getViewStats(@Param("uris") List<String> uris,
                                @Param("start") LocalDateTime start,
                                @Param("end") LocalDateTime end);

    @Query("SELECT new ViewStat(h.uri, h.app, COUNT(h.ip)) " +
            "FROM Hit h " +
            "WHERE h.timestamp BETWEEN :start AND :end " +
            "GROUP BY h.app, h.uri " +
            "ORDER BY COUNT(h.ip) DESC")
    List<ViewStat> getViewStats(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);

    default List<ViewStat> getViewStats(List<String> uris, LocalDateTime start,
                                        LocalDateTime end, Boolean uniqueIp) {
        return uniqueIp ? getViewStatsByUniqIp(uris, start, end) : getViewStats(uris, start, end);
    }

}
