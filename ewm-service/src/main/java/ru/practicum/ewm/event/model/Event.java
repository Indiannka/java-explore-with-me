package ru.practicum.ewm.event.model;

import lombok.*;
import org.hibernate.Hibernate;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.user.model.User;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@Table(name = "events")
@AllArgsConstructor
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long id;

    @Size(min = 20, max = 2000)
    private String annotation;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "confirmed_requests")
    private Integer confirmedRequests;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Size(min = 20, max = 2000)
    private String description;

    @Column(name = "event_date")
    private LocalDateTime eventDate;

    @ManyToOne
    @JoinColumn(name = "initiator_id")
    private User initiator;

    @Column(name = "lon")
    private double lon;

    @Column(name = "lat")
    private double lat;

    private boolean paid;

    @JoinColumn(name = "participant_limit")
    private Integer participantLimit;

    @Column(name = "published_on")
    private LocalDateTime publishedOn;

    @Column(name = "request_moderation")
    private boolean requestModeration;

    @Enumerated(EnumType.STRING)
    private State state;

    @Size(min = 3, max = 120)
    private String title;

    private Integer views;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Event event = (Event) o;
        return id != null && Objects.equals(id, event.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}