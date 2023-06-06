package ru.practicum.ewm.compilations.model;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "compilation_events")
@IdClass(CombinedCompilationEventKeyId.class)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CompilationEvent {

    @Id
    @Column(name = "compilation_id")
    private Long compilationId;
    @Id
    @Column(name = "event_id")
    private Long eventId;
}
