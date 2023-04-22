/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.workshop.tests;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 *
 * @author FGH
 */
public class NewFXMain extends Application {
    
    @Override
    public void start(Stage primaryStage) {
       
        try {
            
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("./../gui/CoachCourses.fxml")));
            
            Scene scene = new Scene(root, 1300, 700);
        
            primaryStage.setTitle("siuesport");
            primaryStage.setScene(scene);
            primaryStage.show();

        
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
