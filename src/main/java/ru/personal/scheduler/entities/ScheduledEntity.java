package ru.personal.scheduler.entities;
import jakarta.persistence.*;

@Entity
@Table(name = "Scheduled")
public class ScheduledEntity {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "start_date", nullable = false)
    private long startDate;

    @Column(name = "end_date")
    private long endDate;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "notification_delivered")
    private boolean notificationDelivered;

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public long getEndDate() {
        return endDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getNotificationDelivered() {
        return notificationDelivered;
    }

    public void setNotificationDelivered(boolean notificationDelivered) {
        this.notificationDelivered = notificationDelivered;
    }
}
