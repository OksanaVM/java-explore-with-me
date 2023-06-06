package ru.practicum.ewm.compilations.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.ewm.events.model.Event;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "compilations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@NamedEntityGraph(
        name = "compilation-with-events",
        attributeNodes = @NamedAttributeNode(value = "events", subgraph = "event"),
        subgraphs = @NamedSubgraph(name = "event", attributeNodes = {
                @NamedAttributeNode("category"),
                @NamedAttributeNode("initiator"),
                @NamedAttributeNode("location")
        })
)

public class Compilation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "pinned", nullable = false)
    private Boolean pinned;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "compilation_events",
            joinColumns = @JoinColumn(name = "compilation_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    private Set<Event> events;
}
