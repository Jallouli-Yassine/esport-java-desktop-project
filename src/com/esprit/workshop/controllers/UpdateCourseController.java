package com.esprit.workshop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class UpdateCourseController {

    @FXML
    private TextField nom;

    @FXML
    private Button updateFnct;

    @FXML
    void update(ActionEvent event) {
        System.out.println(nom.getText());
    }

}
