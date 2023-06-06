package ru.practicum.ewm.compilations.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.ewm.compilations.model.Compilation;

import java.util.List;
import java.util.Optional;

public interface CompilationRepository extends JpaRepository<Compilation, Long> {

    @EntityGraph(value = "compilation-with-events")
    @Query("SELECT c FROM Compilation AS c " +
            "WHERE (:pinned IS NULL OR c.pinned = :pinned)")
    List<Compilation> getCompilations(@Param("pinned") Boolean pinned, Pageable pageable);

    @EntityGraph(value = "compilation-with-events")
    Optional<Compilation> findCompilationById(Long id);

    Integer deleteCompilationById(Long compilationId);
}
