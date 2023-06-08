package ru.practicum.evm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.evm.model.EndpointHit;
import ru.practicum.evm.model.ViewStat;

import java.time.LocalDateTime;
import java.util.List;

public interface HitRepository extends JpaRepository<EndpointHit, Long> {

//    @Query("SELECT new ViewStat(h.uri, h.app, COUNT(DISTINCT h.ip)) " +
//            "FROM EndpointHit h " +
//            "WHERE h.timestamp BETWEEN :start AND :end " +
//            "AND h.uri IN :uris " +
//            "GROUP BY h.app, h.uri " +
//            "ORDER BY COUNT(h.ip) DESC")
//    List<ViewStat> getViewStatsByUniqIp(@Param("uris") List<String> uris,
//                                        @Param("start") LocalDateTime start,
//                                        @Param("end") LocalDateTime end);
//
//    @Query("SELECT new ViewStat(h.uri, h.app, COUNT(h.ip)) " +
//            "FROM EndpointHit h " +
//            "WHERE h.timestamp BETWEEN :start AND :end " +
//            "AND h.uri IN :uris " +
//            "GROUP BY h.app, h.uri " +
//            "ORDER BY COUNT(h.ip) DESC")
//    List<ViewStat> getViewStats(@Param("uris") List<String> uris,
//                                @Param("start") LocalDateTime start,
//                                @Param("end") LocalDateTime end);
//
//    @Query("SELECT new ViewStat(h.uri, h.app, COUNT(h.ip)) " +
//            "FROM EndpointHit h " +
//            "WHERE h.timestamp BETWEEN :start AND :end " +
//            "GROUP BY h.app, h.uri " +
//            "ORDER BY COUNT(h.ip) DESC")
//    List<ViewStat> getViewStats(
//            @Param("start") LocalDateTime start,
//            @Param("end") LocalDateTime end);

//    default List<ViewStat> getViewStats(List<String> uris, LocalDateTime start,
//                                        LocalDateTime end, Boolean uniqueIp) {
//        return uniqueIp ? getViewStatsByUniqIp(uris, start, end) : getViewStats(uris, start, end);
//    }
    @Query(" select new ru.practicum.evm.model.ViewStat(app, uri, count(ip) as hits) from EndpointHit " +
            "where timestamp between ?1 and ?2 and uri in(?3) " +
            "group by app, uri order by hits desc")
    List<ViewStat> getAllStatisticsWithUris(LocalDateTime start, LocalDateTime end, List<String> uri);

    @Query(" select new ru.practicum.evm.model.ViewStat(app, uri, count(distinct ip) as hits) from EndpointHit " +
            "where timestamp between ?1 and ?2 and uri in(?3) " +
            "group by app, uri order by hits desc")
    List<ViewStat> getStatisticsWithUniqueIpAndUris(LocalDateTime start, LocalDateTime end, List<String> uri);

    @Query(" select new ru.practicum.evm.model.ViewStat(app, uri, count(distinct ip) as hits) from EndpointHit " +
            "where timestamp between ?1 and ?2 " +
            "group by app, uri order by hits desc")
    List<ViewStat> getStatisticsWithUniqueIp(LocalDateTime start, LocalDateTime end);

    @Query(" select new ru.practicum.evm.model.ViewStat(app, uri, count(ip) as hits) from EndpointHit " +
            "where timestamp between ?1 and ?2 " +
            "group by app, uri order by hits desc")
    List<ViewStat> getAllStatistics(LocalDateTime start, LocalDateTime end);
}
