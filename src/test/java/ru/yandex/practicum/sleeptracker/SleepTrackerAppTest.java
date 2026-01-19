package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SleepTrackerAppTest {

    @Test
    public void testTwoSessions() {
        CountSessionsFunction func = new CountSessionsFunction();
        List<SleepingSession> sessions = new ArrayList<>();
        SleepingSession s1 = new SleepingSession(LocalDateTime.of(2026, 1, 1, 22, 0),
                LocalDateTime.of(2026, 1, 2, 6, 0),
                SleepQuality.GOOD);

        SleepingSession s2 = new SleepingSession(LocalDateTime.of(2026, 1, 2, 23, 0),
                LocalDateTime.of(2026, 1, 2, 7, 0),
                SleepQuality.GOOD);

        sessions.add(s1);
        sessions.add(s2);

        SleepAnalyticsResult result = func.apply(sessions);
        assertEquals("Количество сессий сна: ", result.getDescription());
        assertEquals(2, result.getValue());
    }

    @Test
    public void testEmptyList() {
        List<SleepingSession> sessions = new ArrayList<>();
        CountSessionsFunction func = new CountSessionsFunction();
        SleepAnalyticsResult result = func.apply(sessions);
        assertEquals("Количество сессий сна: ", result.getDescription());
        assertEquals(0, result.getValue());
    }

    @Test
    public void testMaxSleepDuration() {
        List<SleepingSession> sessions = new ArrayList<>();
        SleepingSession s1 = new SleepingSession(LocalDateTime.of(2026, 1, 1, 22, 0),
                LocalDateTime.of(2026, 1, 2, 0, 0),
                SleepQuality.GOOD);

        SleepingSession s2 = new SleepingSession(LocalDateTime.of(2026, 1, 2, 0, 0),
                LocalDateTime.of(2026, 1, 2, 1, 0),
                SleepQuality.GOOD);

        sessions.add(s1);
        sessions.add(s2);

        MaxDurationFunction func = new MaxDurationFunction();
        SleepAnalyticsResult result = func.apply(sessions);
        assertEquals("Максимальная продолжительность сна в минутах: ", result.getDescription());
        assertEquals(120, result.getValue());
    }

    @Test
    void testMaxDurationEmptyList() {
        MaxDurationFunction func = new MaxDurationFunction();
        SleepAnalyticsResult result = func.apply(List.of());
        assertEquals("Максимальная продолжительность сна в минутах: ", result.getDescription());
        assertEquals(0, result.getValue());
    }

    @Test
    public void testMinimalSleepDuration() {
        List<SleepingSession> sessions = new ArrayList<>();
        SleepingSession s1 = new SleepingSession(LocalDateTime.of(2026, 1, 1, 22, 0),
                LocalDateTime.of(2026, 1, 2, 6, 0),
                SleepQuality.GOOD);

        SleepingSession s2 = new SleepingSession(LocalDateTime.of(2026, 1, 2, 0, 0),
                LocalDateTime.of(2026, 1, 2, 1, 0),
                SleepQuality.GOOD);

        sessions.add(s1);
        sessions.add(s2);

        MinDurationFunction func = new MinDurationFunction();
        SleepAnalyticsResult result = func.apply(sessions);
        assertEquals("Минимальная продолжительность сна в минутах: ", result.getDescription());
        assertEquals(60, result.getValue());
    }

    @Test
    void testMinDurationEmptyList() {
        MinDurationFunction func = new MinDurationFunction();
        SleepAnalyticsResult result = func.apply(List.of());
        assertEquals("Минимальная продолжительность сна в минутах: ", result.getDescription());
        assertEquals(0, result.getValue());
    }

    @Test
    void testAverageDuration() {
        List<SleepingSession> sessions = new ArrayList<>();
        SleepingSession s1 = new SleepingSession(LocalDateTime.of(2026, 1, 1, 22, 0),
                LocalDateTime.of(2026, 1, 2, 0, 0),
                SleepQuality.GOOD);

        SleepingSession s2 = new SleepingSession(LocalDateTime.of(2026, 1, 2, 0, 0),
                LocalDateTime.of(2026, 1, 2, 1, 0),
                SleepQuality.GOOD);

        sessions.add(s1);
        sessions.add(s2);

        AverageSleepFunction func = new AverageSleepFunction();
        SleepAnalyticsResult result = func.apply(sessions);
        assertEquals("Средняя продолжительность сна в минутах: ", result.getDescription());
        assertEquals(90, result.getValue());
    }

    @Test
    void testNoBadQualitySessions() {
        BadQualityCountFunction func = new BadQualityCountFunction();

        SleepingSession s1 = new SleepingSession(
                LocalDateTime.of(2026, 1, 1, 22, 0),
                LocalDateTime.of(2026, 1, 2, 6, 0),
                SleepQuality.GOOD);

        SleepingSession s2 = new SleepingSession(
                LocalDateTime.of(2026, 1, 2, 23, 0),
                LocalDateTime.of(2026, 1, 3, 7, 0),
                SleepQuality.NORMAL);

        SleepAnalyticsResult result = func.apply(List.of(s1, s2));

        assertEquals("Количество сессий с плохим качеством сна: ", result.getDescription());
        assertEquals(0, result.getValue());
    }

    @Test
    void testMultipleBadQualitySessions() {
        BadQualityCountFunction func = new BadQualityCountFunction();

        SleepingSession s1 = new SleepingSession(
                LocalDateTime.of(2026, 1, 3, 23, 0),
                LocalDateTime.of(2026, 1, 4, 7, 0),
                SleepQuality.BAD);

        SleepingSession s2 = new SleepingSession(
                LocalDateTime.of(2026, 1, 4, 14, 0),
                LocalDateTime.of(2026, 1, 4, 15, 0),
                SleepQuality.BAD);

        SleepingSession s3 = new SleepingSession(
                LocalDateTime.of(2026, 1, 5, 22, 0),
                LocalDateTime.of(2026, 1, 6, 6, 0),
                SleepQuality.GOOD);

        SleepAnalyticsResult result = func.apply(List.of(s1, s2, s3));

        assertEquals("Количество сессий с плохим качеством сна: ", result.getDescription());
        assertEquals(2, result.getValue());
    }

    @Test
    void testNoInsomniaNights() {
        InsomniaNightsFunction func = new InsomniaNightsFunction();
        SleepingSession s1 = new SleepingSession(
                LocalDateTime.of(2026, 1, 3, 23, 0),
                LocalDateTime.of(2026, 1, 4, 7, 0),
                SleepQuality.BAD);

        SleepingSession s2 = new SleepingSession(
                LocalDateTime.of(2026, 1, 4, 22, 0),
                LocalDateTime.of(2026, 1, 5, 8, 0),
                SleepQuality.BAD);

        SleepingSession s3 = new SleepingSession(
                LocalDateTime.of(2026, 1, 5, 22, 0),
                LocalDateTime.of(2026, 1, 6, 6, 0),
                SleepQuality.GOOD);

        SleepAnalyticsResult result = func.apply(List.of(s1, s2, s3));
        assertEquals("Количество бессонных ночей: ", result.getDescription());
        assertEquals(0, result.getValue());
    }

    @Test
    void testFiveInsomniaNightsScenario() {
        InsomniaNightsFunction func = new InsomniaNightsFunction();

        SleepingSession s1 = new SleepingSession(
                LocalDateTime.of(2026, 1, 1, 7, 0),
                LocalDateTime.of(2026, 1, 1, 9, 0),
                SleepQuality.NORMAL);

        SleepingSession s2 = new SleepingSession(
                LocalDateTime.of(2026, 1, 2, 23, 0),
                LocalDateTime.of(2026, 1, 3, 7, 0),
                SleepQuality.GOOD);

        SleepingSession s3 = new SleepingSession(
                LocalDateTime.of(2026, 1, 7, 23, 0),
                LocalDateTime.of(2026, 1, 8, 7, 0),
                SleepQuality.GOOD);

        SleepAnalyticsResult result = func.apply(List.of(s1, s2, s3));

        assertEquals("Количество бессонных ночей: ", result.getDescription());
        assertEquals(6, result.getValue());
    }

    @Test
    void noInsomniaNightsEmptyList() {
        InsomniaNightsFunction func = new InsomniaNightsFunction();
        SleepAnalyticsResult result = func.apply(List.of());
        assertEquals("Количество бессонных ночей: ", result.getDescription());
        assertEquals(0, result.getValue());
    }


    @Test
    void testEmptyListReturnDove() {
        ChronotypeFinder func = new ChronotypeFinder();
        SleepAnalyticsResult result = func.apply(List.of());
        assertEquals("Ваш тип: ", result.getDescription());
        assertEquals(Chronotype.DOVE.getRussianName(), result.getValue());
    }

    @Test
    void testOwlChronotype() {
        SleepingSession s1 = new SleepingSession(
                LocalDateTime.of(2026, 1, 1, 23, 30),
                LocalDateTime.of(2026, 1, 2, 9, 30),
                SleepQuality.GOOD);

        String result = ChronotypeFinder.findChronotype(List.of(s1));
        assertEquals(Chronotype.OWL.getRussianName(), result);
    }

    @Test
    void testLarkChronotype() {
        SleepingSession s1 = new SleepingSession(
                LocalDateTime.of(2026, 1, 1, 21, 30),
                LocalDateTime.of(2026, 1, 2, 6, 30),
                SleepQuality.GOOD);

        String result = ChronotypeFinder.findChronotype(List.of(s1));
        assertEquals(Chronotype.LARK.getRussianName(), result);
    }

    @Test
    void testDoveChronotype() {
        SleepingSession s1 = new SleepingSession(
                LocalDateTime.of(2026, 1, 1, 22, 30),
                LocalDateTime.of(2026, 1, 2, 8, 0),
                SleepQuality.NORMAL);
        String result = ChronotypeFinder.findChronotype(List.of(s1));
        assertEquals(Chronotype.DOVE.getRussianName(), result);
    }
}


