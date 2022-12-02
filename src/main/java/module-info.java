module ru.personal.scheduler {
    requires javafx.controls;
    requires javafx.fxml;
    requires junit;
    requires java.sql;
    requires java.persistence;
    requires org.hibernate.orm.core;
//    requires com.fasterxml.classmate;
    requires java.naming;
    requires jakarta.persistence;

    opens ru.personal.scheduler.entities to org.hibernate.orm.core;
    exports ru.personal.scheduler.entities to org.hibernate.orm.core;
}