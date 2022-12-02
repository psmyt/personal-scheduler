package ru.personal.scheduler.time.utils;

import org.junit.Assert;
import java.time.Instant;

import static ru.personal.scheduler.time.utils.TimeUtils.*;
import java.util.*;

public class Interval {
    private final Instant startTime;
    private final Instant endTime;

    private Interval(Instant startTime, Instant endTime) {
        Assert.assertNotNull(startTime);
        Assert.assertNotNull(endTime);
        Assert.assertNotEquals(startTime.getEpochSecond(), endTime.getEpochSecond());
        Assert.assertTrue(startTime.isBefore(endTime));
        this.startTime = Instant.ofEpochSecond(startTime.getEpochSecond());
        this.endTime = Instant.ofEpochSecond(endTime.getEpochSecond());
    }

    public static Interval between(Instant startTime, Instant endTime) {
        return new Interval(startTime, endTime);
    }

    @Override
    public String toString() {
        return "[" + this.startTime + " - " + this.endTime + "]";
    }

    @Override
    public int hashCode() {
        return this.startTime.hashCode() * 31 + this.endTime.hashCode();
    }

    @Override
    public boolean equals(Object interval) {
        if (!(interval instanceof Interval)) throw new IllegalArgumentException(interval + " is not an instance of Interval");
        return this.startTime.equals(((Interval) interval).startTime)
                && this.endTime.equals(((Interval) interval).endTime);
    }

    public Instant getStartTime() {
        return this.startTime;
    }

    public Interval truncToLocalDate() {
        return Interval.between(
                localStartOfTheDayTimeStamp(this.startTime),
                localStartOfTheDayTimeStamp(this.endTime));
    }

    public Instant getEndTime() {
        return this.endTime;
    }

    public long lengthInSeconds() {
        return this.endTime.getEpochSecond() - this.startTime.getEpochSecond();
    }

    public boolean contains(Interval interval) {
        return this.startTime.isBefore(interval.startTime) &&
                this.endTime.isAfter(interval.endTime);
    }

    public boolean contains(Instant instant) {
        return this.startTime.isBefore(instant) &&
                this.endTime.isAfter(instant);
    }

    public Interval mergeWith(Interval interval) {
        Instant[] allTimePoints = {
                interval.startTime,
                interval.endTime,
                this.startTime,
                this.endTime};
        Arrays.sort(allTimePoints, Instant::compareTo);
        if (Interval.between(allTimePoints[0], allTimePoints[3]).lengthInSeconds() > interval.lengthInSeconds() + this.lengthInSeconds()) {
            throw new IllegalArgumentException(String.format("intervals %s and %s do not intersect or touch at any point",
                    this, interval));
        }
        return Interval.between(allTimePoints[0],allTimePoints[3]);
    }

    public List<Interval> split(Instant... splitPoints) {
        Arrays.stream(splitPoints).forEach(x -> assertContains(this, x));
        List<Instant> splitPointsDistinctSorted = Arrays.stream(splitPoints)
                .map(Instant::getEpochSecond)
                .distinct()
                .map(Instant::ofEpochSecond)
                .sorted(Instant::compareTo)
                .toList();
        List<Interval> result = new ArrayList<>();
        result.add(this);
        for (Instant instant : splitPointsDistinctSorted) {
            List<Interval> list = split(result.get(result.size() - 1), instant);
            result.remove(result.size() - 1);
            result.addAll(list);
        }
        return result;
    }

    public static List<Interval> split(Interval interval, Instant instant) {
        assertContains(interval, instant);
        List<Interval> result = new ArrayList<>();
        result.add(new Interval(interval.startTime, instant));
        result.add(new Interval(instant, interval.endTime));
        return result;
    }

    public static void assertContains(Interval interval, Instant splitPoint) {
        try {
            Assert.assertTrue(interval.contains(splitPoint));
        } catch (AssertionError e) {
            throw new AssertionError(
                    String.format("Instant %s \nis not part of the interval %s",
                            splitPoint, interval));
        }
    }
}
