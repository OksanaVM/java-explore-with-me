package server.hit.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.dto.HitDto;
import ru.practicum.stats.ExploreWithMeStatistic;
import ru.practicum.stats.mapper.HitMapper;
import ru.practicum.stats.model.Hit;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = ExploreWithMeStatistic.class)
class HitMapperTest {
    private HitDto hitDto;

    @BeforeEach
    private void init() {
        hitDto = HitDto.builder()
                .app("ewm-main-service")
                .uri("/events/1")
                .ip("192.163.0.1")
                .timestamp("2022-09-06 11:00:00")
                .build();
    }

    @Test
    @DisplayName("Тест преобразования hitDto -> hit")
    void toHitValidDataTest() {
        Hit hit = HitMapper.toEndpointHit(hitDto);
        assertThat(hit.getUri().equals(hitDto.getUri())).isTrue();
        assertThat(hit.getApp().equals(hitDto.getApp())).isTrue();
        assertThat(hit.getIp().equals(hitDto.getIp())).isTrue();
        assertThat(hit.getTimestamp().equals(LocalDateTime.of(2022, 9, 06, 11, 00, 00))).isTrue();
    }
//
//    @Test
//    @DisplayName("Тест преобразования hitDto -> hit, невалидная дата")
//    void toHitNotValidDateTest() {
//        hitDto = HitDto.builder()
//                .app("ewm-main-service")
//                .uri("/events/1")
//                .ip("192.163.0.1")
//                .timestamp("rrtt-09-@@ 11:00:aa")
//                .build();
//        assertThrows(StartEndRangeException.class,
//                () -> HitMapper.toEndpointHit(hitDto));
//
//    }
}