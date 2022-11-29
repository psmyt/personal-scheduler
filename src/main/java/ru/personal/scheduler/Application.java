package ru.personal.scheduler;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ru.personal.scheduler.dto.Planned;
import ru.personal.scheduler.view.ScheduleTable;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Application extends javafx.application.Application {

    @Override
    public void start(Stage stage) {
        ScheduleTable scheduleTable = ScheduleTable.getInstance();
        BorderPane root = new BorderPane(scheduleTable);
        Scene scene = new Scene(root, 1920, 1000);
        stage.setScene(scene);
        stage.show();
        scheduleTable.redrawSchedule(
                Interval.between(
                        Instant.now(),
                        Instant.ofEpochMilli(System.currentTimeMillis() + 7*24*60*60*1000)));
    }

    public static void main(String[] args) {
        launch(args);
    }
}