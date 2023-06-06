package ru.practicum.ewm.requests.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RequestStat {
    @Id
    private Long eventId;
    private Long requests;
}
