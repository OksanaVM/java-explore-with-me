package ru.practicum.ewm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.practicum.client.StatisticClient;

@Configuration
public class StatisticConfig {

    @Value("${statistic-server.url}")
    private String serverUrl;

    @Bean
    public StatisticClient statisticClient(RestTemplateBuilder builder) {
        return new StatisticClient(serverUrl, builder);
    }
}