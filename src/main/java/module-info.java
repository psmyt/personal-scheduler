module ru.personal.scheduler {
    requires javafx.controls;
    requires javafx.fxml;
    requires junit;


    opens ru.personal.scheduler to javafx.fxml;
    exports ru.personal.scheduler;
}