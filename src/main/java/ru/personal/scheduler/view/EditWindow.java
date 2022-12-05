package ru.personal.scheduler.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import ru.personal.scheduler.data.objects.Scheduled;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class EditWindow extends GridPane {
    public EditWindow(Scheduled scheduledItem) {
        this.setAlignment(Pos.CENTER);
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(25, 25, 25, 25));

        Label descriptionLabel = new Label("Description:");
        this.add(descriptionLabel, 0, 0);
        TextField descriptionText = new TextField(scheduledItem.getDescription());
        descriptionText.setMinSize(200, 100);
        this.add(descriptionText, 1, 0);

        Label startTimeLabel = new Label("Starts at:");
        this.add(startTimeLabel, 0, 1);
        DatePicker startTimePicker = new DatePicker(
                LocalDate.ofInstant(
                        scheduledItem.getStartDate(),
                        ZoneId.systemDefault()));
        this.add(startTimePicker, 1, 1);

        Label endTimeLabel = new Label("Ends at:");
        this.add(endTimeLabel, 0, 2);
        DatePicker endTimePicker = new DatePicker(
                LocalDate.ofInstant(
                        scheduledItem.getEndDate(),
                        ZoneId.systemDefault()));
        this.add(endTimePicker, 1, 2);

        Button saveButton = new Button("Save");
        saveButton.setOnAction(event -> {
            Scheduled updatedItem = Scheduled.Builder(
                    scheduledItem.getId(),
                    Instant.from(startTimePicker.getValue().atStartOfDay(ZoneId.systemDefault())),
                    descriptionText.getText())
                    .endDate(Instant.from(endTimePicker.getValue().atStartOfDay(ZoneId.systemDefault())))
                    .notificationDelivered(scheduledItem.isNotificationDelivered())
                    .build();
            updatedItem.persist();
        });
        this.add(saveButton, 0, 3);
    }
}
