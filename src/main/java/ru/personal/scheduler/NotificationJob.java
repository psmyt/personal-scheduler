package ru.personal.scheduler;

import javafx.application.Platform;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import ru.personal.scheduler.data.objects.Scheduled;
import ru.personal.scheduler.gui.NotificationWindow;

import java.time.Instant;
import java.util.List;

public class NotificationJob extends ScheduledService<Void> {

    @Override
    protected Task<Void> createTask() {
        return new Task<>() {
            @Override
            protected Void call() {
                List<Scheduled> scheduledList = Scheduled.thatStartBefore(
                        Instant.now().plusSeconds(15 * 60)
                );
                scheduledList.forEach(scheduled -> Platform.runLater(
                        () -> new NotificationWindow(scheduled).show()));
                return null;
            }
        };
    }
}
