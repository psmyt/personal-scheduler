package ru.personal.scheduler.gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ru.personal.scheduler.data.objects.Scheduled;
import ru.personal.scheduler.time.utils.Interval;

import java.time.Instant;

import static java.time.Instant.now;
import static ru.personal.scheduler.time.utils.TimeUtils.*;

public class NotificationWindow extends Stage {

    public NotificationWindow(Scheduled scheduled) {
        Text notificationText = new Text(
                scheduled.getStartDate().isBefore(now()) ?
                        String.format("Пропущено событие \"%s\" , \n %s часа назад (%s)",
                                scheduled.getDescription(),
                                (float) Interval.between(scheduled.getStartDate(), now())
                                        .lengthInSeconds() / SECONDS_IN_ONE_HOUR,
                                printDate(scheduled.getStartDate())) :
                        String.format("Событие \"%s\" через %s минут",
                                scheduled.getDescription(),
                                (float) Interval.between(now(), scheduled.getStartDate())
                                        .lengthInSeconds() / 60));
        BorderPane pane = new BorderPane(notificationText);
        BorderPane.setAlignment(notificationText, Pos.CENTER);
        pane.autosize();
        this.setScene(new Scene(pane));
        this.setAlwaysOnTop(true);
    }
}
