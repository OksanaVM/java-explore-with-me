package ru.practicum.ewm.events.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.ewm.events.dto.State;
import ru.practicum.ewm.events.model.Event;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
    @EntityGraph(value = "event")
    List<Event> findAllByInitiatorId(Long initiatorId, Pageable pageable);

    @EntityGraph(value = "event")
    Event findByInitiatorIdAndId(Long initiatorId, Long id);

    @EntityGraph(value = "event")
    List<Event> findAllByCategoryId(Long categoryId);

    @EntityGraph(value = "event")
    @Query("SELECT e FROM Event AS e " +
            "WHERE (:users IS NULL OR e.initiator.id IN :users) " +
            "AND (:states IS NULL OR e.state IN :states) " +
            "AND (:categories IS NULL OR e.category.id IN :categories) " +
            "AND (coalesce(:rangeStart, :rangeEnd) IS NULL " +
            "OR e.eventDate BETWEEN coalesce(:rangeStart, e.eventDate) AND coalesce(:rangeEnd, e.eventDate))")
    List<Event> getEvents(@Param("rangeStart") LocalDateTime rangeStart,
                          @Param("rangeEnd") LocalDateTime rangeEnd,
                          @Param("users") List<Long> users,
                          @Param("states") List<State> states,
                          @Param("categories") List<Long> categories,
                          Pageable pageable);

    @EntityGraph(value = "event")
    @Query("SELECT e FROM Event AS e " +
            "WHERE e.state = :state " +
            "AND (:text IS NULL " +
            "OR LOWER(e.description) LIKE LOWER(CONCAT('%', :text, '%')) " +
            "OR LOWER(e.annotation) LIKE LOWER(CONCAT('%', :text, '%'))) " +
            "AND (:categories IS NULL OR e.category.id IN (:categories)) " +
            "AND (:paid IS NULL OR e.paid = :paid) " +
            "AND ((coalesce(:rangeStart, :rangeEnd) IS NULL AND e.eventDate > now()) " +
            "OR e.eventDate BETWEEN coalesce(:rangeStart, e.eventDate) AND coalesce(:rangeEnd, e.eventDate))")
    List<Event> getPublicEvents(Pageable pageable,
                                @Param("state") State state,
                                @Param("text") String text,
                                @Param("categories") List<Long> categories,
                                @Param("paid") Boolean paid,
                                @Param("rangeStart") LocalDateTime rangeStart,
                                @Param("rangeEnd") LocalDateTime rangeEnd);

    @EntityGraph(value = "event")
    Event findEventByIdAndState(Long id, State state);

    @EntityGraph(value = "event")
    Optional<Event> findEventById(Long id);


    @Query("select e from Event e " +
            "where (:users is null or e.initiator.id in :users) " +
            "and (:states is null or e.state in :states) " +
            "and (:categories is null or e.category.id in :categories) " +
            "and (cast(:rangeStart as java.time.LocalDateTime) is null or e.eventDate >= :rangeStart) " +
            "and (cast(:rangeEnd as java.time.LocalDateTime) is null or e.eventDate <= :rangeEnd)")
    List<Event> getEventsWithUsersStatesCategoriesDateTime(@Param("users") List<Long> users,
                                                           @Param("states") List<State> states,
                                                           @Param("categories") List<Long> categories,
                                                           @Param("rangeStart") LocalDateTime rangeStart,
                                                           @Param("rangeEnd") LocalDateTime rangeEnd,
                                                           Pageable pageable);
    @Query("select e from Event e " +
            "where ((:text is null or upper(e.annotation) like upper(concat('%', :text, '%'))) " +
            "or (:text is null or upper(e.description) like upper(concat('%', :text, '%')))) " +
            "and (:state is null or e.state = :state) " +
            "and (:categories is null or e.category.id in :categories) " +
            "and (:paid is null or e.paid = :paid) " +
            "and (cast(:rangeStart as java.time.LocalDateTime) is null or e.eventDate >= :rangeStart) " +
            "and (cast(:rangeEnd as java.time.LocalDateTime) is null or e.eventDate <= :rangeEnd)" +
            "and (e.participantLimit > e.confirmedRequests)" +
            "order by e.eventDate desc")
    List<Event> getAvailableEventsWithFiltersDateSorted(@Param("text") String text,
                                                        @Param("state") State state,
                                                        @Param("categories") List<Long> categories,
                                                        @Param("paid") Boolean paid,
                                                        @Param("rangeStart") LocalDateTime rangeStart,
                                                        @Param("rangeEnd") LocalDateTime rangeEnd,
                                                        Pageable pageable);

    @Query("select e from Event e " +
            "where ((:text is null or upper(e.annotation) like upper(concat('%', :text, '%'))) " +
            "or (:text is null or upper(e.description) like upper(concat('%', :text, '%')))) " +
            "and (:state is null or e.state = :state) " +
            "and (:categories is null or e.category.id in :categories) " +
            "and (:paid is null or e.paid = :paid) " +
            "and (cast(:rangeStart as java.time.LocalDateTime) is null or e.eventDate >= :rangeStart) " +
            "and (cast(:rangeEnd as java.time.LocalDateTime) is null or e.eventDate <= :rangeEnd)" +
            "and (e.participantLimit > e.confirmedRequests)")
    List<Event> getAvailableEventsWithFilters(@Param("text") String text,
                                              @Param("state") State state,
                                              @Param("categories") List<Long> categories,
                                              @Param("paid") Boolean paid,
                                              @Param("rangeStart") LocalDateTime rangeStart,
                                              @Param("rangeEnd") LocalDateTime rangeEnd,
                                              Pageable pageable);

    @Query("select e from Event e " +
            "where ((:text is null or upper(e.annotation) like upper(concat('%', :text, '%'))) " +
            "or (:text is null or upper(e.description) like upper(concat('%', :text, '%')))) " +
            "and (:state is null or e.state = :state) " +
            "and (:categories is null or e.category.id in :categories) " +
            "and (:paid is null or e.paid = :paid) " +
            "and (cast(:rangeStart as java.time.LocalDateTime) is null or e.eventDate >= :rangeStart) " +
            "and (cast(:rangeEnd as java.time.LocalDateTime) is null or e.eventDate <= :rangeEnd)" +
            "order by e.eventDate desc")
    List<Event> getAllEventsWithFiltersDateSorted(@Param("text") String text,
                                                  @Param("state") State state,
                                                  @Param("categories") List<Long> categories,
                                                  @Param("paid") Boolean paid,
                                                  @Param("rangeStart") LocalDateTime rangeStart,
                                                  @Param("rangeEnd") LocalDateTime rangeEnd,
                                                  Pageable pageable);

    @Query("select e from Event e " +
            "where ((:text is null or upper(e.annotation) like upper(concat('%', :text, '%'))) " +
            "or (:text is null or upper(e.description) like upper(concat('%', :text, '%')))) " +
            "and (:state is null or e.state = :state) " +
            "and (:categories is null or e.category.id in :categories) " +
            "and (:paid is null or e.paid = :paid) " +
            "and (cast(:rangeStart as java.time.LocalDateTime) is null or e.eventDate >= :rangeStart) " +
            "and (cast(:rangeEnd as java.time.LocalDateTime) is null or e.eventDate <= :rangeEnd)")
    List<Event> getAllEventsWithFilters(@Param("text") String text,
                                        @Param("state") State state,
                                        @Param("categories") List<Long> categories,
                                        @Param("paid") Boolean paid,
                                        @Param("rangeStart") LocalDateTime rangeStart,
                                        @Param("rangeEnd") LocalDateTime rangeEnd,
                                        Pageable pageable);
}
