package ru.personal.scheduler;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ru.personal.scheduler.data.objects.Scheduled;
import ru.personal.scheduler.time.utils.Interval;
import ru.personal.scheduler.time.utils.TimeUtils;
import ru.personal.scheduler.view.ScheduleTable;
import ru.personal.scheduler.view.SqlLine;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Application extends javafx.application.Application {

    @Override
    public void start(Stage stage) {
        Stage sql = new Stage();
        Scene sqlScene = new Scene(SqlLine.getInstance());
        sql.setScene(sqlScene);
        sql.show();
        ScheduleTable scheduleTable = ScheduleTable.getInstance();
        BorderPane root = new BorderPane(scheduleTable);
        Scene scene = new Scene(root, 1100, 900);
        stage.setScene(scene);
        stage.show();
//        scheduleTable.setScope(Interval.between(Instant.now().minusSeconds(TimeUtils.SECONDS_IN_ONE_DAY * 10),
//                Instant.now()));
        scheduleTable.redrawSchedule();
    }

    public static void main(String[] args) {
        launch(args);
    }
}