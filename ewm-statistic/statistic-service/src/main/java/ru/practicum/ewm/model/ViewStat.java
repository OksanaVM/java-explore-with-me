package ru.practicum.ewm.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ViewStat {
    @Id
    private String uri;
    private String app;
    private Long hits;
}
