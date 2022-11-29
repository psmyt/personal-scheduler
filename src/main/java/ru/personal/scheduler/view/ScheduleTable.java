package ru.personal.scheduler.view;

import javafx.scene.Group;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Pair;
import ru.personal.scheduler.Interval;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class ScheduleTable extends StackPane {
    private static final Map<Integer, String> hourDictionary = getHourDictionary();

    private static Map<Integer, String> getHourDictionary() {
        var dictionary = new HashMap<Integer, String>();
        dictionary.put(0, "00:00");
        dictionary.put(1, "01:00");
        dictionary.put(2, "02:00");
        dictionary.put(3, "03:00");
        dictionary.put(4, "04:00");
        dictionary.put(5, "05:00");
        dictionary.put(6, "06:00");
        dictionary.put(7, "07:00");
        dictionary.put(8, "08:00");
        dictionary.put(9, "09:00");
        dictionary.put(10, "10:00");
        dictionary.put(11, "11:00");
        dictionary.put(12, "12:00");
        dictionary.put(13, "13:00");
        dictionary.put(14, "14:00");
        dictionary.put(15, "15:00");
        dictionary.put(16, "16:00");
        dictionary.put(17, "17:00");
        dictionary.put(18, "18:00");
        dictionary.put(19, "19:00");
        dictionary.put(20, "20:00");
        dictionary.put(21, "21:00");
        dictionary.put(22, "22:00");
        dictionary.put(23, "23:00");
        return dictionary;
    }

    private static final int DEFAULT_SCHEDULE_WIDTH = 1000;
    private static final int DEFAULT_SCHEDULE_HEIGHT = 700;

    private static final long DEFAULT_SCHEDULE_SCOPE_LENGTH =
            7 * 24 * 60 * 60;
    private long scheduleScopeLength = DEFAULT_SCHEDULE_SCOPE_LENGTH;
    private int scheduleWidth = DEFAULT_SCHEDULE_WIDTH;
    private int scheduleHeight = DEFAULT_SCHEDULE_HEIGHT;

    private static Group scheduleContent = new Group();
    private static final ScheduleTable scheduleTable = new ScheduleTable(scheduleContent);

    private ScheduleTable(Group group) {
        super(group);
    }

    public static ScheduleTable getInstance() {
        return scheduleTable;
    }

    public Pair<Float, Float> getCoordinatesOfTimePoint(Instant point) {

        return null;
    }

    public void redrawSchedule(Interval scope) {
        this.getChildren().remove(scheduleContent);
        scheduleContent = new Group();
        int numOfDays = (int) (scope.length() / (24 * 60 * 60));
        float columnWidth = (float) scheduleWidth / numOfDays;
        float columnHeight = (float) scheduleHeight / 24;
        for (int i = 0; i < numOfDays; i++) {
            for (int j = 0; j < 24; j++) {
                Rectangle newRectangle = new Rectangle();
                newRectangle.setX(i * columnWidth);
                newRectangle.setY(j * columnHeight);
                newRectangle.setWidth(columnWidth - 5);
                newRectangle.setHeight(columnHeight - 2);
                newRectangle.setFill(Color.BEIGE);
                Text text = new Text(String.valueOf(hourDictionary.get(j)));
                text.setX(i * columnWidth);
                text.setY(text.getTabSize() + j * columnHeight);
                scheduleContent.getChildren().add(newRectangle);
                scheduleContent.getChildren().add(text);
            }
        }
        this.getChildren().add(scheduleContent);
    }
}
