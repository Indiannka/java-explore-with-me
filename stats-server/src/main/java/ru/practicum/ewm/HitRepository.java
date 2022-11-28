package ru.practicum.ewm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.model.Hit;
import ru.practicum.ewm.model.StatsView;

import java.time.LocalDateTime;
import java.util.List;

public interface HitRepository extends JpaRepository<Hit, Long> {

    @Query("  select h.app as app, h.uri AS uri, count(h.ip) as hits" +
           "    from Hit h " +
           "   where h.timestamp between ?1 and ?2" +
           "     and h.uri in (?3) " +
           "group by h.app, h.uri")
    List<StatsView> getStats(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query("  select h.app as app, h.uri AS uri, count(distinct h.ip) as hits" +
           "    from Hit h " +
           "   where h.timestamp between ?1 and ?2" +
           "     and h.uri in (?3) " +
           "group by h.app, h.uri")
    List<StatsView> getUniqueByIpStats(LocalDateTime start, LocalDateTime end, List<String> uris);
}