package com.esprit.workshop.controllers;

import com.esprit.workshop.entites.Cours;
import com.esprit.workshop.services.ServiceCours;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AdminCourses implements Initializable {

    /*axepted couses table*/
    @FXML
    private TableView<Cours> acceptedCourses;

    @FXML
    private TableColumn<Cours, String> aNiveau;

    @FXML
    private TableColumn<Cours, Integer> aPrix;

    @FXML
    private TableColumn<Cours, String> aTitre;

    /*declined couses table*/
    @FXML
    private TableView<Cours> declinedCourses;

    @FXML
    private TableColumn<Cours, String> dNiveau;

    @FXML
    private TableColumn<Cours, Integer> dPrix;

    @FXML
    private TableColumn<Cours, String> dTitre;

    /*all couses table for admin*/
    @FXML
    private TableView<Cours> forAdminAllCourses;

    @FXML
    private TableColumn<Cours, String> niveau;

    @FXML
    private TableColumn<Cours, Integer> prix;

    @FXML
    private TableColumn<Cours, String> titre;

    @FXML
    private TableColumn<Cours, Void> actionBtns;

    List<Cours> courses = null;
    List<Cours> coursesAxepted = null;
    List<Cours> coursesDeclined = null;
    ServiceCours sc = new ServiceCours();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        afficherCoursesA_axepter();
        afficherCoursAxepter();
        afficherCoursRefuser();

    }

    void afficherCoursesA_axepter() {
        try {
            courses = sc.afficherCoursWaiting();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //afImage.setCellValueFactory(new PropertyValueFactory<>("image"));
        prix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        titre.setCellValueFactory(new PropertyValueFactory<>("titre"));
        niveau.setCellValueFactory(new PropertyValueFactory<>("niveau"));
        actionBtns.setCellFactory(column -> new TableCell<Cours, Void>() {
            private final Button axepter = new Button("axepter");
            private final Button refuser = new Button("refuser");

            {
                axepter.getStyleClass().add("axepterClass");
                refuser.getStyleClass().add("refuserClass");

                axepter.setOnAction(event -> {
                    Cours c = getTableView().getItems().get(getIndex());
                    try {
                        sc.accepterCours(c.getId());
                        refreshTables();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                });
                refuser.setOnAction(event -> {
                    Cours c = getTableView().getItems().get(getIndex());
                    try {
                        sc.refuserCours(c.getId());
                        refreshTables();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox hbox = new HBox(axepter, refuser);
                    hbox.setSpacing(10);
                    setGraphic(hbox);
                }
            }
        });


        forAdminAllCourses.setItems(FXCollections.observableList(courses));
    }

    void afficherCoursAxepter() {
        try {
            courses = sc.afficherCoursAxepter();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //afImage.setCellValueFactory(new PropertyValueFactory<>("image"));
        aPrix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        aTitre.setCellValueFactory(new PropertyValueFactory<>("titre"));
        aNiveau.setCellValueFactory(new PropertyValueFactory<>("niveau"));
        acceptedCourses.setItems(FXCollections.observableList(courses));
    }

    void afficherCoursRefuser() {
        try {
            courses = sc.afficherCoursRefuser();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //afImage.setCellValueFactory(new PropertyValueFactory<>("image"));
        dPrix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        dTitre.setCellValueFactory(new PropertyValueFactory<>("titre"));
        dNiveau.setCellValueFactory(new PropertyValueFactory<>("niveau"));
        declinedCourses.setItems(FXCollections.observableList(courses));
    }

    void refreshTables(){
        afficherCoursesA_axepter();
        afficherCoursAxepter();
        afficherCoursRefuser();
    }

}
