package ru.practicum.ewm;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.converter.HitsMapper;
import ru.practicum.ewm.model.EndpointHit;
import ru.practicum.ewm.model.StatsView;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class HitServiceImpl implements HitService {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final HitRepository hitrepository;
    private final HitsMapper hitsMapper;

    @Override
    public void saveStats(EndpointHit hit) {
        hitrepository.save(hitsMapper.convert(hit));
    }

    @Override
    public Collection<StatsView> getStats(String start, String end, String[] uris, boolean unique) {

        LocalDateTime startAt = LocalDateTime.parse(decode(start), formatter);
        LocalDateTime endAt = LocalDateTime.parse(decode(end), formatter);

       if (unique) {
           return hitrepository.getUniqueByIpStats(startAt, endAt, Arrays.asList(uris));
       }
           return hitrepository.getStats(startAt, endAt, Arrays.asList(uris));
    }

    private String decode(String text)  {
        return URLDecoder.decode(text, StandardCharsets.UTF_8);
    }
}