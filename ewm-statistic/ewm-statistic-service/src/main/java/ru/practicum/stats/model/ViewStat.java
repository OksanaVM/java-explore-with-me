package ru.practicum.stats.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ViewStat {
    private String app;
    private String uri;
    private Long hits;
}
