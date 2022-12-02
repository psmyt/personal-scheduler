package ru.personal.scheduler;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ru.personal.scheduler.data.objects.Scheduled;
import ru.personal.scheduler.view.ScheduleTable;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Application extends javafx.application.Application {

    @Override
    public void start(Stage stage) {
        ScheduleTable scheduleTable = ScheduleTable.getInstance();
        BorderPane root = new BorderPane(scheduleTable);
        Scene scene = new Scene(root, 1100, 900);
        stage.setScene(scene);
        stage.show();
        scheduleTable.redrawSchedule();
    }

    public static void main(String[] args) {
        launch(args);
    }
}