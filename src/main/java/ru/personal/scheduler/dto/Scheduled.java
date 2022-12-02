package ru.personal.scheduler.dto;

import ru.personal.scheduler.time.utils.Interval;
import ru.personal.scheduler.entities.ScheduledEntity;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static ru.personal.scheduler.time.utils.TimeUtils.*;

public class Scheduled {
    private Instant startDate;
    private Instant endDate;
    private String description;
    private boolean notificationDelivered;

    private Scheduled(Builder builder) {
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
        this.description = builder.description;
        this.notificationDelivered = builder.notificationDelivered;
    }

    public ScheduledEntity toEntity() {
        ScheduledEntity entity = new ScheduledEntity();
        entity.setStartDate(startDate.getEpochSecond());
        entity.setDescription(description);
        entity.setEndDate(endDate.getEpochSecond());
        entity.setNotificationDelivered(notificationDelivered);
        return entity;
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
        return new Builder(startDate,description);
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
