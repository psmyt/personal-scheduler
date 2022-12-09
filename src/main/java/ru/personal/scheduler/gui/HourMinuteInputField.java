package ru.personal.scheduler.gui;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.DateStringConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class HourMinuteInputField extends TextField {

    private static final SimpleDateFormat format = new SimpleDateFormat("HH:mm");

    HourMinuteInputField(String startingTime) {
        super(startingTime);
        try {
            this.setTextFormatter(new TextFormatter<>(new DateStringConverter(format), format.parse(startingTime),
                    change -> {
                        String newText = change.getControlNewText();
                        if (newText.matches("[0-9:]*")) {
                            return change;
                        }
                        else return null;
                    }));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

}
