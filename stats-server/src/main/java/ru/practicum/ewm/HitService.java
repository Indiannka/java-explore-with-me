package ru.practicum.ewm;

import ru.practicum.ewm.model.EndpointHit;
import ru.practicum.ewm.model.StatsView;

import java.util.Collection;

public interface HitService {

    void saveStats(EndpointHit hit);

    Collection<StatsView> getStats(String start, String end, String[] uris, boolean unique);
}
