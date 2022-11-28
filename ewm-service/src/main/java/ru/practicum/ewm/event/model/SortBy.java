package ru.practicum.ewm.event.model;

public enum SortBy {

    EVENT_DATE("eventDate"),
    VIEWS("views");

    final String value;

    SortBy(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}