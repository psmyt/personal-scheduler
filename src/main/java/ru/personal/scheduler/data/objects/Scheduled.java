package ru.personal.scheduler.data.objects;

import ru.personal.scheduler.DataSource;
import ru.personal.scheduler.time.utils.Interval;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import static ru.personal.scheduler.time.utils.TimeUtils.*;

public class Scheduled {

    private UUID id;
    private Instant startDate;
    private Instant endDate;
    private String description;
    private boolean notificationDelivered;

    private Scheduled(Builder builder) {
        this.id = UUID.randomUUID();
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
        this.description = builder.description;
        this.notificationDelivered = builder.notificationDelivered;
    }

    public static List<Scheduled> findWithin(Interval scope) {
//        DataSource.executeSql("select * from scheduled where start_time > ? and end_time < ?",
//                scope.getStartTime().getEpochSecond(),
//                scope.getEndTime().getEpochSecond());
        DataSource.executeSql("select * from scheduled");
        return null;
    }

    public String getStartDay() {
        return startDate
                .atZone(ZoneId.systemDefault())
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    @Override
    public String toString() {
        return String.format("Событие: %s\n%s - %s\n",
                description,
                formatToHourMinute(startDate),
                formatToHourMinute(endDate));
    }

    public Instant getStartDate() {
        return this.startDate;
    }

    public Interval getTimeWindow() {
        return Interval.between(this.startDate, this.endDate);
    }

    public Instant getEndDate() {
        return this.endDate;
    }

    public static Builder Builder(Instant startDate, String description) {
        return new Builder(startDate, description);
    }

    public static class Builder {
        private Instant startDate;
        private Instant endDate;
        private String description;
        private boolean notificationDelivered;


        private Builder(Instant startDate, String description) {
            this.startDate = startDate;
            this.description = description;
        }

        public Builder endDate(Instant endDate) {
            this.endDate = endDate;
            return this;
        }

        public Builder notificationDelivered(boolean bool) {
            this.notificationDelivered = bool;
            return this;
        }

        public Scheduled build() {
            return new Scheduled(this);
        }
    }
}
