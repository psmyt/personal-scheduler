package ru.personal.scheduler.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ru.personal.scheduler.data.objects.Scheduled;
import ru.personal.scheduler.time.utils.TimeUtils;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class EditWindow extends GridPane {

    Scheduled scheduledItem;
    private TextArea description;
    private DatePicker startDatePicker;
    private TextField startLocalTime;
    private DatePicker endDatePicker;
    private TextField endLocalTime;

    public EditWindow(Scheduled scheduledItem) {
        this.scheduledItem = scheduledItem;
        this.setAlignment(Pos.CENTER);
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(25, 25, 25, 25));

        Label descriptionLabel = new Label("Description:");
        this.add(descriptionLabel, 0, 0);
        TextArea descriptionText = new TextArea(scheduledItem.getDescription());
        descriptionText.setMinSize(200, 100);
        this.add(descriptionText, 1, 0);
        this.description = descriptionText;

        Label startTimeLabel = new Label("Starts at:");
        this.add(startTimeLabel, 0, 1);
        DatePicker startTimePicker = new DatePicker(
                LocalDate.ofInstant(
                        scheduledItem.getStartDate(),
                        ZoneId.systemDefault()));
        this.add(startTimePicker, 1, 1);
        this.startDatePicker = startTimePicker;
        TextField startTimeHours = new TextField(TimeUtils.formatToHourMinute(scheduledItem.getStartDate()));
        this.add(startTimeHours, 2, 1);
        this.startLocalTime = startTimeHours;

        Label endTimeLabel = new Label("Ends at:");
        this.add(endTimeLabel, 0, 2);
        DatePicker endTimePicker = new DatePicker(
                LocalDate.ofInstant(
                        scheduledItem.getEndDate(),
                        ZoneId.systemDefault()));
        this.add(endTimePicker, 1, 2);
        this.endDatePicker = endTimePicker;
        TextField endTimeHours = new TextField(TimeUtils.formatToHourMinute(scheduledItem.getEndDate()));
        this.add(endTimeHours, 2, 2);
        this.endLocalTime = endTimeHours;

        Button saveButton = new Button("Save");
        saveButton.setOnAction(saveButtonAction());
        this.add(saveButton, 0, 3);

        Button deleteButton = new Button("Delete");
        if (scheduledItem.getId() != 0) {
            this.add(deleteButton, 3, 3);
            deleteButton.setOnAction( event -> {
                scheduledItem.delete();
                ScheduleTable.getInstance().redrawSchedule();
                ((Stage) (deleteButton.getParent().getScene().getWindow())).close();
            });
        }
    }

    private EventHandler<ActionEvent> saveButtonAction() {
        return event -> {
            persistForm();
            ScheduleTable.getInstance().redrawSchedule();
        };
    }

    private void persistForm() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        Scheduled
                .Builder(scheduledItem.getId(),
                        Instant.from(
                                LocalTime.parse(startLocalTime.getText(), formatter)
                                        .atDate(startDatePicker.getValue())
                                        .atZone(ZoneId.systemDefault())
                        ),
                        description.getText())
                .endDate(Instant.from(
                        LocalTime.parse(endLocalTime.getText(), formatter)
                                .atDate(endDatePicker.getValue())
                                .atZone(ZoneId.systemDefault())
                ))
                .notificationDelivered(scheduledItem.isNotificationDelivered())
                .build()
                .persist();
    }
}
