package ru.practicum.ewm.configs;

public enum SortBy {
    CREATION_DATE("created"),
    EVENT_DATE("eventDate"),
    USERS("author"),
    VIEWS("views");

    final String value;

    SortBy(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}