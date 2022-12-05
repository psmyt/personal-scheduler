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
import java.util.ArrayList;
import java.util.List;

import static ru.personal.scheduler.time.utils.TimeUtils.*;

public class Scheduled {

    private Long id;
    private Instant startDate;
    private Instant endDate;

    private String description;

    private Boolean notificationDelivered;

    private Scheduled(Builder builder) {
        this.id = builder.id;
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
        this.description = builder.description;
        this.notificationDelivered = builder.notificationDelivered;
    }

    public static List<Scheduled> findWithin(Interval scope) {
        List<Scheduled> scheduledList = new ArrayList<>();
        String sql = "select * from Scheduled where start_date > ? and end_date < ?";
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, scope.getStartTime().getEpochSecond());
            statement.setLong(2, scope.getEndTime().getEpochSecond());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                scheduledList.add(
                        Builder(resultSet.getLong("id"),
                                Instant.ofEpochSecond(resultSet.getLong("start_date")),
                                resultSet.getString("description"))
                                .endDate(Instant.ofEpochSecond(resultSet.getLong("end_date")))
                                .notificationDelivered(resultSet.getBoolean("notification_delivered"))
                                .build());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(scheduledList);
        return scheduledList;
    }

    public void persist() {
        if (this.id == null) {
            insert();
        } else update();
    }

    public void insert() {
        String sql = "insert into Scheduled(start_date, end_date, description, notification_delivered) values " +
                "(?, ?, ?, ?)";
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, this.startDate.getEpochSecond());
            statement.setLong(2, this.endDate.getEpochSecond());
            statement.setString(3, this.description);
            statement.setBoolean(4, this.notificationDelivered);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update() {
        String sql = "update Scheduled set " +
                "start_date = ?, " +
                "end_date = ?, " +
                "description = ? " +
                "notification_delivered = ? " +
                "where id = ?";
        try (Connection connection = DataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, this.startDate.getEpochSecond());
            statement.setLong(2, this.endDate.getEpochSecond());
            statement.setString(3, this.description);
            statement.setBoolean(4, this.notificationDelivered);
            statement.setLong(5, this.id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Boolean isNotificationDelivered() {
        return notificationDelivered;
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

    public static Builder Builder(long id, Instant startDate, String description) {
        return new Builder(id, startDate, description);
    }

    public static class Builder {
        private long id;
        private Instant startDate;
        private Instant endDate;
        private String description;
        private boolean notificationDelivered;


        private Builder(Instant startDate, String description) {
            this.startDate = startDate;
            this.description = description;
        }

        private Builder(long id, Instant startDate, String description) {
            this.id = id;
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
