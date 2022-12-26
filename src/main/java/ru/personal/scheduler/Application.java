package ru.personal.scheduler;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import ru.personal.scheduler.exceptions.BusinessException;
import ru.personal.scheduler.gui.ScheduleTable;
import ru.personal.scheduler.gui.SqlLine;

import java.util.Arrays;

public class Application extends javafx.application.Application {

    @Override
    public void start(Stage mainStage) {
        NotificationJob notificationJob = new NotificationJob();
        notificationJob.setPeriod(Duration.seconds(30));
        notificationJob.start();
        Thread.setDefaultUncaughtExceptionHandler(Application::displayError);
        Stage sql = new Stage();
        Scene sqlScene = new Scene(SqlLine.getInstance());
        sql.setScene(sqlScene);
        sql.show();
        ScheduleTable scheduleTable = ScheduleTable.getInstance();
        BorderPane root = new BorderPane(scheduleTable);
        Scene mainScene = new Scene(root, 1100, 900);
        mainStage.setScene(mainScene);
        mainStage.show();
        mainStage.setOnCloseRequest( event -> {
            Platform.exit();
            System.exit(0);
        });
        scheduleTable.redrawSchedule();
    }

    private static void displayError(Thread thread, Throwable throwable) {
        if (throwable instanceof BusinessException) {
            Stage errorWindow = new Stage();
            Text errorText = new Text(throwable.getMessage());
            BorderPane pane = new BorderPane(errorText);
            BorderPane.setAlignment(errorText, Pos.CENTER);
            pane.autosize();
            Scene errorScene = new Scene(pane);
            errorWindow.setScene(errorScene);
            errorWindow.setAlwaysOnTop(true);
            errorWindow.show();
        } else {
            try {
                throw throwable;
            } catch (Throwable e) {
                throw new RuntimeException(e.getMessage() + Arrays.toString(e.getStackTrace()));
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}