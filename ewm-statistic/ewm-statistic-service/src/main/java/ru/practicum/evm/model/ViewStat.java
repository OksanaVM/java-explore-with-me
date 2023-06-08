package ru.practicum.evm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ViewStat {
    private String uri;
    private String app;
    private Long hits;
}
