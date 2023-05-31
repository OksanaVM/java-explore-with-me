package ru.practicum.ewm;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EndpointHitRepository extends JpaRepository<EndpointHit, Long> {

    // Перегрузка метода getViewStats с учетом уникальных IP
    default List<ViewStat> getViewStats(List<String> uris, LocalDateTime start,
                                        LocalDateTime end, Boolean uniqueIp) {
        return uniqueIp ? getViewStatsByUniqIp(uris, start, end) : getViewStats(uris, start, end);
    }

    @Query("SELECT new ViewStat(e.uri, e.app, COUNT(DISTINCT e.ip)) " +
            "FROM EndpointHit e " +
            "WHERE e.timestamp BETWEEN :start AND :end " +
            "AND e.uri IN :uris " +
            "GROUP BY e.app, e.uri " +
            "ORDER BY COUNT(e.ip) DESC")
    List<ViewStat> getViewStatsByUniqIp(@Param("uris") List<String> uris,
                                        @Param("start") LocalDateTime start,
                                        @Param("end") LocalDateTime end);

    @Query("SELECT new ViewStat(e.uri, e.app, COUNT(e.ip)) " +
            "FROM EndpointHit e " +
            "WHERE e.timestamp BETWEEN :start AND :end " +
            "AND e.uri IN :uris " +
            "GROUP BY e.app, e.uri " +
            "ORDER BY COUNT(e.ip) DESC")
    List<ViewStat> getViewStats(@Param("uris") List<String> uris,
                                @Param("start") LocalDateTime start,
                                @Param("end") LocalDateTime end);

    @Query("SELECT new ViewStat(e.uri, e.app, COUNT(e.ip)) " +
            "FROM EndpointHit e " +
            "WHERE e.timestamp BETWEEN :start AND :end " +
            "GROUP BY e.app, e.uri " +
            "ORDER BY COUNT(e.ip) DESC")
    List<ViewStat> getViewStats(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);
}
