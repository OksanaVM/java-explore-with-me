package ru.practicum.ewm.requests.model;

import lombok.*;
import ru.practicum.ewm.events.dto.State;
import ru.practicum.ewm.users.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Requests",
        uniqueConstraints = {@UniqueConstraint(name = "UniqueEventIdRequesterId",
                columnNames = {"events_id", "requester_id"})})

public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "events_id")
    private Long eventId;
    @Column(name = "created")
    private LocalDateTime created;
    @ManyToOne
    @JoinColumn(name = "requester_id")
    private User requester;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private State status;
}