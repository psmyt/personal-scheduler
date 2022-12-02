package ru.personal.scheduler.view;

import javafx.scene.Group;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;

import static ru.personal.scheduler.SqlConsole.executeSqlQuery;

public class SqlLine extends GridPane {
    private static Group root;

    static {
        root = new Group();
        TextArea output = new TextArea("the response will appear here");
        output.setEditable(false);
        output.setFocusTraversable(false);
        output.setMouseTransparent(true);
        output.setPrefSize(300, 300);
        output.setLayoutX(400);
        TextField input = new TextField();
        input.setLayoutY(100);
        input.setLayoutX(200);
        input.setPromptText("enter SQL");
        input.setOnKeyPressed(k ->
        {
            if (k.getCode().equals(KeyCode.ENTER)) {
                String response = executeSqlQuery(input.getCharacters());
                output.setText(response);
                input.clear();
            }
        });
        root.getChildren().add(output);
        root.getChildren().add(input);
    }

    private static final SqlLine sqlLine = new SqlLine(root);

    private SqlLine(Group group) {
        super();
        this.getChildren().add(group);
    }

    public static SqlLine getInstance() {
        return sqlLine;
    }

}
