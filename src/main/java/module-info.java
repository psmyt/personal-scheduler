module ru.personal.scheduler {
    requires javafx.controls;
    requires javafx.fxml;


    opens ru.personal.scheduler to javafx.fxml;
    exports ru.personal.scheduler;
}