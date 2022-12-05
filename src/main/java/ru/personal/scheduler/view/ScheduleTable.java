package ru.personal.scheduler.view;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;
import org.junit.Assert;
import ru.personal.scheduler.time.utils.Interval;
import ru.personal.scheduler.data.objects.Scheduled;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.personal.scheduler.time.utils.TimeUtils.*;

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

    private Interval scope;

    private static final int DEFAULT_SCHEDULE_WIDTH = 1000;
    private static final int DEFAULT_SCHEDULE_HEIGHT = 700;
    private int scheduleWidth = DEFAULT_SCHEDULE_WIDTH;
    private int scheduleHeight = DEFAULT_SCHEDULE_HEIGHT;

    private static Group scheduleRoot = new Group();
    private static Group scheduleGrid = new Group();
    private static Group scheduleTexts = new Group();
    private static Group scheduleContents = new Group();
    private static final ScheduleTable scheduleTable = new ScheduleTable(scheduleRoot);


    private ScheduleTable(Group group) {
        super(group);
        this.scope = calculateDefaultScope();
    }

    private Interval calculateDefaultScope() {
        int today = currentDayOfTheWeek() - 1; // number from 0 to 6 represents current day of the week
        return Interval.between(
                Instant.ofEpochMilli(System.currentTimeMillis() - (long) today * MILLISECONDS_IN_ONE_DAY),
                Instant.ofEpochMilli(System.currentTimeMillis() + (long) (7 - today) * MILLISECONDS_IN_ONE_DAY)
        ).truncToLocalDate();
    }

    public static ScheduleTable getInstance() {
        return scheduleTable;
    }

    public void setScope(Interval newScope) {
        this.scope = newScope.truncToLocalDate();
    }

    public Pair<Float, Float> getUnadjustedCoordinatesOfTimePoint(Instant point) {
        point = point.truncatedTo(ChronoUnit.SECONDS);
        Assert.assertTrue(scope.contains(point));
        var fromScopeBeginningToPoint = Interval.between(scope.getStartTime(), point);
        float y = fromScopeBeginningToPoint.lengthInSeconds() % SECONDS_IN_ONE_DAY;
        float x = (fromScopeBeginningToPoint.lengthInSeconds() - y) / SECONDS_IN_ONE_DAY;
        return new Pair<>(x, y);
    }

    public void composeContents(List<Scheduled> scheduled, float columnWidth, float columnHeight) {
        scheduleContents = new Group();
        float heightPerSec = columnHeight / SECONDS_IN_ONE_DAY;
        scheduled.forEach(
                s -> {
                    Pair<Float, Float> coords = getUnadjustedCoordinatesOfTimePoint(s.getStartDate());
                    Rectangle rectangle = new Rectangle();
                    rectangle.setX(coords.getKey() * columnWidth);
                    rectangle.setY(coords.getValue() * heightPerSec);
                    rectangle.setWidth(columnWidth - 5);
                    rectangle.setHeight(
                            Interval.between(s.getStartDate(), s.getEndDate())
                                    .lengthInSeconds() * heightPerSec);
                    rectangle.setFill(Color.RED);
                    Tooltip tooltip = new Tooltip(s.toString());
                    tooltip.setShowDelay(Duration.ZERO);
                    Tooltip.install(rectangle, tooltip);
                    rectangle.onMouseClickedProperty().set(event ->
                    {
                        Stage scheduledItemWindow = new Stage();
                        scheduledItemWindow.setAlwaysOnTop(true);
                        Scene scene = new Scene(new EditWindow(s));
                        scheduledItemWindow.setScene(scene);
                        scheduledItemWindow.show();
                    });
                    scheduleContents.getChildren().add(rectangle);
                }
        );
    }

    public void composeGrid(int numOfColumns, int numOfRows, float columnWidth, float hourHeight) {
        scheduleGrid = new Group();
        for (int i = 0; i < numOfColumns; i++) {
            for (int j = 0; j < numOfRows; j++) {
                Rectangle newRectangle = new Rectangle();
                newRectangle.setX(i * columnWidth);
                newRectangle.setY(j * hourHeight);
                newRectangle.setWidth(columnWidth - 5);
                newRectangle.setHeight(hourHeight - 2);
                newRectangle.setFill(Color.BEIGE);
                scheduleGrid.getChildren().add(newRectangle);
            }
        }
    }

    public void composeTexts(int numOfColumns, int numOfRows, float columnWidth, float hourHeight) {
        scheduleTexts = new Group();
        for (int i = 0; i < numOfColumns; i++) {
            String date = Instant.ofEpochSecond(scope.getStartTime().getEpochSecond() + (long) i * SECONDS_IN_ONE_DAY)
                    .atZone(ZoneId.systemDefault())
                    .format(DateTimeFormatter.ofPattern("EEEE, '\n'dd MMMM yyyy"));
            Text dateLabel = new Text(date);
            dateLabel.setX(i * columnWidth + columnWidth / 4);
            dateLabel.setY(-20);
            scheduleTexts.getChildren().add(dateLabel);
            for (int j = 0; j < numOfRows; j++) {
                Text hourMark = new Text(String.valueOf(hourDictionary.get(j)));
                hourMark.setX(i * columnWidth);
                hourMark.setY(hourMark.getTabSize() + j * hourHeight);
                scheduleTexts.getChildren().add(hourMark);
            }
        }
    }

    public void redrawSchedule() {
        List<Scheduled> scheduledList = Scheduled.findWithin(scope);
        this.getChildren().remove(scheduleRoot);
        scheduleRoot = new Group();
        int numOfDays = (int) scope.lengthInSeconds() / SECONDS_IN_ONE_DAY;
        float columnWidth = (float) scheduleWidth / numOfDays;
        float heightOfOneHour = (float) scheduleHeight / 24;
        composeGrid(numOfDays, 24, columnWidth, heightOfOneHour);
        scheduleRoot.getChildren().add(scheduleGrid);
        composeContents(scheduledList, columnWidth, scheduleHeight);
        scheduleRoot.getChildren().add(scheduleContents);
        composeTexts(numOfDays, 24, columnWidth, heightOfOneHour);
        scheduleRoot.getChildren().add(scheduleTexts);
        Button newScheduled = new Button("New Scheduled");
        newScheduled.setAlignment(Pos.TOP_LEFT);
        newScheduled.setOnAction(event -> {
            Stage scheduledItemWindow = new Stage();
            scheduledItemWindow.setAlwaysOnTop(true);
            Scene scene = new Scene(new EditWindow(
                    Scheduled.Builder(Instant.now(), "enter description")
                            .endDate(Instant.now().plusSeconds(60*60))
                            .notificationDelivered(false)
                            .build()
            ));
            scheduledItemWindow.setScene(scene);
            scheduledItemWindow.show();
        });
        scheduleRoot.getChildren().add(newScheduled);
        this.getChildren().add(scheduleRoot);
    }
}
