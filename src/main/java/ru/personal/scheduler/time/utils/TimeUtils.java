package ru.personal.scheduler.time.utils;

import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class TimeUtils {
    public static final int MILLISECONDS_IN_ONE_DAY = 24 * 60 * 60 * 1000;
    public static final int SECONDS_IN_ONE_DAY = 24 * 60 * 60;
    public static final int MILLISECONDS_IN_ONE_HOUR = 60 * 60 * 1000;

    public static int currentDayOfTheWeek() {
        return Instant.now().atZone(ZoneId.systemDefault()).getDayOfWeek().getValue();
    }

    public static String formatToHourMinute(Instant instant) {
        return instant.atZone(ZoneId.systemDefault())
                .format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public static long parseSecondsFromHourMinutes(String hourMinutes) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return LocalTime.parse(hourMinutes, formatter).getSecond();
    }

    public static Instant localStartOfTheDayTimeStamp(Instant instant) {
        int hoursSinceStartOfTheDayInSec =
                instant.atZone(ZoneId.systemDefault()).getHour() * 60 * 60;
        int minutesSinceStartOfTheHourInSec =
                instant.atZone(ZoneId.systemDefault()).getMinute() * 60;
        int secondsSinceStartOfTheMinuteInSec =
                instant.atZone(ZoneId.systemDefault()).getSecond();
        return instant.minusSeconds(hoursSinceStartOfTheDayInSec
                + minutesSinceStartOfTheHourInSec
                + secondsSinceStartOfTheMinuteInSec);
    }

}
