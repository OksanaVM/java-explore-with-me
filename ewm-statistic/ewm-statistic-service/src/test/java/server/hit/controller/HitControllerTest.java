package server.hit.controller;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.practicum.dto.HitDto;
import ru.practicum.stats.controller.ServiceController;
import ru.practicum.stats.service.HitService;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureMockMvc
@SpringBootTest(classes = ServiceController.class)
class HitControllerTest {


    @MockBean
    HitService hitService;

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
    @SneakyThrows
    void addHitAppValidHitDtoTest() {
        hitDto = HitDto.builder()
                .app("myApp")
                .uri("/events/1")
                .ip("192.163.0.1")
                .timestamp("2022-09-06 11:00:00")
                .build();
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<HitDto>> violations = validator.validate(hitDto);
        assertThat(violations).hasSize(0);
    }

    @Test
    void addHitInvalidAppHitDtoTest() {
        HitDto hitDto = HitDto.builder()
                .app("")
                .uri("/events/1")
                .ip("192.163.0.1")
                .timestamp("2022-09-06 11:00:00")
                .build();
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<HitDto>> violations = validator.validate(hitDto);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("must not be blank");
    }

    @Test
    void addHitInvalidIpHitDtoTest() {
        HitDto hitDto = HitDto.builder()
                .app("myApp")
                .uri("/events/1")
                .ip("")
                .timestamp("2022-09-06 11:00:00")
                .build();
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<HitDto>> violations = validator.validate(hitDto);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("must not be blank");
    }

    @Test
    void addHitInvalidUriHitDtoTest() {
        HitDto hitDto = HitDto.builder()
                .app("myApp")
                .uri("")
                .ip("192.163.0.1")
                .timestamp("2022-09-06 11:00:00")
                .build();
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<HitDto>> violations = validator.validate(hitDto);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("must not be blank");
    }

}