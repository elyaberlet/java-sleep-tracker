package ru.yandex.practicum.sleeptracker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SleepTrackerApp {

    private final List<Function<List<SleepingSession>, SleepAnalyticsResult>> functions = new ArrayList<>();

    public SleepTrackerApp() {
        functions.add(new CountSessionsFunction());
        functions.add(new MinDurationFunction());
        functions.add(new MaxDurationFunction());
        functions.add(new AverageSleepFunction());
        functions.add(new BadQualityCountFunction());
        functions.add(new InsomniaNightsFunction());
        functions.add(new ChronotypeFinder());
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Укажите путь к файлу с логом сна");
        }

        String filePath = args[0];
        System.out.println("Загружаем файл " + filePath);

        try {
            List<SleepingSession> sessions = loadSessions(filePath);
            SleepTrackerApp app = new SleepTrackerApp();
            app.runFunctions(sessions);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка чтения файла: " + e.getMessage());
        }
    }
    public static List<SleepingSession> loadSessions(String filePath) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");
        return Files.lines(Paths.get(filePath)).map(line -> {
            String[] parts = line.split(";");
            LocalDateTime start = LocalDateTime.parse(parts[0], formatter);
            LocalDateTime end = LocalDateTime.parse(parts[1], formatter);
            SleepQuality quality = SleepQuality.valueOf(parts[2]);
            return new SleepingSession(start, end, quality);
        }).collect(Collectors.toList());
    }

    public void runFunctions(List<SleepingSession> sessions) {
        functions.forEach(func -> {
            SleepAnalyticsResult result = func.apply(sessions);
            System.out.println(result.getDescription() + result.getValue());
        });
    }
    }
