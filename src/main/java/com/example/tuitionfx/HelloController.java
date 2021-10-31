package com.example.tuitionfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class HelloController {

    @FXML
    private RadioButton CS;

    @FXML
    private BorderPane borderPane;

    @FXML
    private ToggleGroup major;

    @FXML
    private HBox majors;

    @FXML
    private TextField studentName;

    @FXML
    private TextArea textArea;

    @FXML
    void addStudent(ActionEvent event) {

    }

    @FXML
    void removeStudent(ActionEvent event) {

    }

}
