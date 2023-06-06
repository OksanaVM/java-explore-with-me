package ru.practicum.evm;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.evm.dto.HitDto;
import ru.practicum.evm.dto.ViewStatDto;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class StatisticClient extends BaseClient {

    @Autowired
    public StatisticClient(@Value("${statistic-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ViewStatDto createHit(HitDto hitDto) {
        Gson gson = new Gson();
        ResponseEntity<Object> objectResponseEntity = post("/hit", hitDto);
        String json = gson.toJson(objectResponseEntity.getBody());

        return gson.fromJson(json, ViewStatDto.class);
    }

    public List<ViewStatDto> getStats(String start, String end, List<String> uris, Boolean unique) {
        Gson gson = new Gson();
        Map<String, Object> parameters = Map.of(
                "uris", String.join(",", uris),
                "unique", unique,
                "start", start,
                "end", end
        );
        ResponseEntity<Object> objectResponseEntity =
                get("/stats?start={start}&end={end}&uris={uris}&unique={unique}", parameters);
        String json = gson.toJson(objectResponseEntity.getBody());
        ViewStatDto[] viewStatDtoArray = gson.fromJson(json, ViewStatDto[].class);

        return Arrays.asList(viewStatDtoArray);
    }
}