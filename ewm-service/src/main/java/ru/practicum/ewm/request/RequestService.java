package ru.practicum.ewm.request;

import ru.practicum.ewm.request.model.Request;

import java.util.Collection;

public interface RequestService {

    Collection<Request> getAll(Long userId);

    Request create(Long userId, Long eventId);

    Request cancel(Long userId, Long requestId);

    Collection<Request> getRequestsByEventOwner(Long userId, Long eventId);

    Request confirmByEventOwner(Long userId, Long eventId, Long requestId);

    Request rejectByEventOwner(Long userId, Long eventId, Long requestId);
}