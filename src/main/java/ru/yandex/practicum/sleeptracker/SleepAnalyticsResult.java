package ru.yandex.practicum.sleeptracker;

public class SleepAnalyticsResult {
    private final String description;
    private final Object value;

    public SleepAnalyticsResult(String description, Object value) {
        this.description = description;
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public Object getValue() {
        return value;
    }
}
