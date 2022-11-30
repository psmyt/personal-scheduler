module ru.personal.scheduler {
    requires javafx.controls;
    requires javafx.fxml;
    requires junit;
    requires java.sql;


    opens ru.personal.scheduler to javafx.fxml;
    exports ru.personal.scheduler;
}