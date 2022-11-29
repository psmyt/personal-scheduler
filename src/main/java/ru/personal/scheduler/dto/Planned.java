package ru.personal.scheduler.dto;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Planned {
    private UUID id;
    private Instant startDate;
    private Instant endDate;
    private String description;
    private boolean notificationDelivered;

    private Planned(Builder builder) {
        this.id = UUID.randomUUID();
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
        this.description = builder.description;
        this.notificationDelivered = builder.notificationDelivered;
    }

    public String getStartDay() {
        return startDate
                .atZone(ZoneId.systemDefault())
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
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

        public Planned build() {
            return new Planned(this);
        }
    }
}
