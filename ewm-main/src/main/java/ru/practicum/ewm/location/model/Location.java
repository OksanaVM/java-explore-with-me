package ru.practicum.ewm.location.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "location")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "latitude")
    private Float lat;
    @Column(name = "longitude")
    private Float lon;
}

