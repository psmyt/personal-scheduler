package ru.personal.scheduler.time.utils;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.*;
import static ru.personal.scheduler.time.utils.TimeUtils.*;

class TimeUtilsTest {

    @Test
    public void testCurrentDayOfTheWeek() {
        System.out.println(currentDayOfTheWeek());
        assertTrue(currentDayOfTheWeek() <= 7
                && currentDayOfTheWeek() >= 1);
    }

    @Test
    public void testLocalDateToEpoch() {
    }
}