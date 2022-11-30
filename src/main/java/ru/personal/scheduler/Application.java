package ru.personal.scheduler;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import ru.personal.scheduler.dto.Scheduled;
import ru.personal.scheduler.view.ScheduleTable;
import ru.personal.scheduler.view.SqlLine;

import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static ru.personal.scheduler.time.utils.TimeUtils.*;

public class Application extends javafx.application.Application {

    @Override
    public void start(Stage stage) {
        SqlLine sqlLine = SqlLine.getInstance();
        Scene sqlScene = new Scene(sqlLine);
        Stage sqlWindow = new Stage();
        sqlWindow.setAlwaysOnTop(true);
        sqlWindow.setScene(sqlScene);
        sqlWindow.show();
        ScheduleTable scheduleTable = ScheduleTable.getInstance();
        BorderPane root = new BorderPane(scheduleTable);
        Scene scene = new Scene(root, 1100, 900);
        stage.setScene(scene);
        stage.show();
        List<Scheduled> scheduledList = new ArrayList<>();
        scheduledList.add(
                Scheduled.Builder(Instant.now(), "Запланированное собрание какое-то")
                        .endDate(Instant.ofEpochMilli(System.currentTimeMillis() + 60 * 60 * 1000))
                .build());
        scheduledList.add(
                Scheduled.Builder(Instant.now().plusSeconds(50 * 60 * 60), "Еще событие какое-то будет")
                        .endDate(Instant.ofEpochMilli(System.currentTimeMillis() + 51 * 60 * 60 * 1000))
                        .build());
        System.out.println(scheduledList.get(0).getTimeWindow());
        scheduleTable.redrawSchedule(scheduledList);
//        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2),
//                event -> {
//                    scheduleTable.setScope(Interval.between(
//                            Instant.ofEpochMilli(System.currentTimeMillis() - (4 * MILLISECONDS_IN_ONE_DAY)),
//                            Instant.now().plusSeconds(SECONDS_IN_ONE_DAY*2)
//                    ));
//                    scheduleTable.redrawSchedule(scheduledList);
//                }));
//        timeline.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}