package ru.practicum.ewm.events.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import ru.practicum.ewm.categories.model.Category;
import ru.practicum.ewm.events.dto.State;
import ru.practicum.ewm.location.model.Location;
import ru.practicum.ewm.users.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@NamedEntityGraph(
        name = "event",
        attributeNodes = {
                @NamedAttributeNode(value = "category"),
                @NamedAttributeNode(value = "initiator"),
                @NamedAttributeNode(value = "location"),
        }
)
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "annotation", nullable = false)
    private String annotation;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
    @Column(name = "confirmed_requests", nullable = false)
    private int confirmedRequests;
    @Column(name = "created_on", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdOn;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "event_date", nullable = false)
    private LocalDateTime eventDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "initiator_id")
    private User initiator;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;
    @Column(name = "paid", nullable = false)
    private Boolean paid;
    @Column(name = "participant_limit", nullable = false)
    private Integer participantLimit;
    @Column(name = "published_on", nullable = false)
    private LocalDateTime publishedOn;
    @Column(name = "request_moderation", nullable = false)
    private Boolean requestModeration;
    @Column(name = "state", nullable = false)
    private State state;
    @Column(name = "title", nullable = false)
    private String title;
}
