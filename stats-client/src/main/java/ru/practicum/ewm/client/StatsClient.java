package ru.practicum.ewm.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.ewm.model.EndpointHit;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Service
@PropertySource("classpath:stats.properties")
public class StatsClient extends BaseClient {
    private static final String API_PREFIX_HIT = "/hit";
    private static final String API_PREFIX_STATS = "/stats";
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Autowired
    public StatsClient(@Value("${stats.server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> sendHit(EndpointHit endpointHit) {
        return post(API_PREFIX_HIT, endpointHit);
    }

    public ResponseEntity<Object> getStats(LocalDateTime start, LocalDateTime end, String[] uris, boolean unique) {
        Map<String, Object> parameters = Map.of(
                "start", encode(start.format(DateTimeFormatter.ofPattern(DATE_FORMAT))),
                "end", encode(end.format(DateTimeFormatter.ofPattern(DATE_FORMAT))),
                "uris", uris,
                "unique", unique);
        ResponseEntity<Object> response = get(API_PREFIX_STATS + "?start={start}&end={end}&uris={uris}&unique={unique}", parameters);
        return response;
    }

    private String encode(String text)  {
        return URLEncoder.encode(text, StandardCharsets.UTF_8);
    }
}