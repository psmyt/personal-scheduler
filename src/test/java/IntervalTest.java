import org.junit.Assert;
import org.junit.Test;
import ru.personal.scheduler.Interval;

import java.time.Instant;
import java.util.List;

public class IntervalTest {

    public final long currentTimeMillis = System.currentTimeMillis();
    public final Interval lastTenMinutes = Interval.between(Instant.ofEpochMilli(currentTimeMillis - 10*60*1000),
            Instant.ofEpochMilli(currentTimeMillis));
    public final Interval lastFiveMinutes = Interval.between(Instant.ofEpochMilli(currentTimeMillis - 5*60*1000),
            Instant.ofEpochMilli(currentTimeMillis));
    public final Interval fiveMinutesBeforeLastFiveMinutes = Interval.between(Instant.ofEpochMilli(currentTimeMillis - 10*60*1000),
            lastFiveMinutes.getStartTime());
    public final Interval tenMinutesBeforeLastTenMinutes = Interval.between(Instant.ofEpochMilli(currentTimeMillis - 20*60*1000),
            lastTenMinutes.getStartTime());

    @Test
    public void splitting24HoursIn3() {
        Instant now = Instant.ofEpochMilli(System.currentTimeMillis());
        Instant yesterday = Instant.ofEpochMilli(System.currentTimeMillis() - 24 * 60 * 60 * 1000);
        Interval betweenYesterdayAndToday = Interval.between(yesterday, now);
        Instant eightHoursAgo = Instant.ofEpochMilli(System.currentTimeMillis() - 8 * 60 * 60 * 1000);
        Instant sixteenHoursAgo = Instant.ofEpochMilli(System.currentTimeMillis() - 16 * 60 * 60 * 1000);
        List<Interval> list = betweenYesterdayAndToday.split(eightHoursAgo, sixteenHoursAgo);
        System.out.println(list);
        System.out.println(betweenYesterdayAndToday.length());
        System.out.println(list.stream().mapToLong(Interval::length).sum());
        Assert.assertEquals(betweenYesterdayAndToday.length(),
                list.stream().mapToLong(Interval::length).sum());
    }

    @Test
    public void exceptionEmptyInterval() {
        long epochMillis = 1669629616465L;
        long epochMillisPlusOne = epochMillis + 534;
        var thrown = Assert.assertThrows(
                AssertionError.class,
                () -> Interval.between(
                        Instant.ofEpochMilli(epochMillis),
                        Instant.ofEpochMilli(epochMillisPlusOne)));
        Assert.assertTrue(thrown.getMessage().contains("Values should be different"));
    }

    @Test
    public void mergeTestPositive() {
        Assert.assertEquals(lastTenMinutes, lastFiveMinutes.mergeWith(lastTenMinutes));
        Assert.assertEquals(lastTenMinutes, lastTenMinutes.mergeWith(lastFiveMinutes));
        Assert.assertEquals(lastTenMinutes, lastTenMinutes.mergeWith(lastTenMinutes));
        Assert.assertEquals(lastTenMinutes, fiveMinutesBeforeLastFiveMinutes.mergeWith(lastFiveMinutes));
        Assert.assertEquals(lastTenMinutes, lastFiveMinutes.mergeWith(fiveMinutesBeforeLastFiveMinutes));
    }

    @Test
    public void mergeExceptionNoOverlap() {
        var thrown = Assert.assertThrows(IllegalArgumentException.class,
                () -> lastFiveMinutes.mergeWith(tenMinutesBeforeLastTenMinutes));
        System.out.println(thrown.getMessage());
        Assert.assertTrue(thrown.getMessage().contains("do not intersect or touch at any point"));
    }
}