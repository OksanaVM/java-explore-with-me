package ru.practicum.stats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.stats.model.Hit;
import ru.practicum.stats.model.ViewStat;

import java.time.LocalDateTime;
import java.util.List;

public interface HitRepository extends JpaRepository<Hit, Long> {

    @Query(" select new ru.practicum.stats.model.ViewStat(app, uri, count(distinct ip) as hits) from Hit " +
            "where timestamp between ?1 and ?2 and uri in(?3) " +
            "group by app, uri order by hits desc")
    List<ViewStat> getStatisticsWithUniqueIpAndUris(LocalDateTime start, LocalDateTime end, List<String> uri);

    @Query(" select new ru.practicum.stats.model.ViewStat(app, uri, count(ip) as hits) from Hit " +
            "where timestamp between ?1 and ?2 and uri in(?3) " +
            "group by app, uri order by hits desc")
    List<ViewStat> getAllStatisticsWithUris(LocalDateTime start, LocalDateTime end, List<String> uri);

    @Query(" select new ru.practicum.stats.model.ViewStat(app, uri, count(distinct ip) as hits) from Hit " +
            "where timestamp between ?1 and ?2 " +
            "group by app, uri order by hits desc")
    List<ViewStat> getStatisticsWithUniqueIp(LocalDateTime start, LocalDateTime end);

    @Query(" select new ru.practicum.stats.model.ViewStat(app, uri, count(ip) as hits) from Hit " +
            "where timestamp between ?1 and ?2 " +
            "group by app, uri order by hits desc")
    List<ViewStat> getAllStatistics(LocalDateTime start, LocalDateTime end);

}