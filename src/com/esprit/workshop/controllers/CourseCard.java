package com.esprit.workshop.controllers;

import com.esprit.workshop.entites.Cours;
import com.esprit.workshop.services.GamerService;
import com.esprit.workshop.services.ServiceCours;
import com.esprit.workshop.services.UserCourses;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

public class CourseCard {

    @FXML
    private AnchorPane cardContainer;

    @FXML
    private Label coursePrix;
    @FXML
    private Label courseTitre;
    @FXML
    private ImageView CourseImage;

    @FXML
    private Button acheter;

    @FXML
    private Button addToWishlist;
    @FXML
    private Button removeFromWishlist;

    AllCourses allCourses = new AllCourses();
    UserCourses uc = new UserCourses();
    GamerService gs = new GamerService();
    ServiceCours sc = new ServiceCours();

    private Image img;
    Cours c;
    int userLoggedIn = 26;

    public void initialize(Cours data) throws SQLException {
        c = data;
        courseTitre.setText(data.getTitre());
        coursePrix.setText(String.valueOf(data.getPrix()));

        if (uc.isInWishList(userLoggedIn, c.getId())) {
            addToWishlist.setVisible(false);
            removeFromWishlist.setVisible(true);
        } else {
            removeFromWishlist.setVisible(false);

        }
        try {
            img = new Image("C:/Users/moham/OneDrive/Bureau/pidev/SuieDesktop/src/com/esprit/workshop/uploads/picture/" + data.getImage());
            System.out.println(data.getImage());
            CourseImage.setImage(img);
        } catch (Exception e) {
            System.out.println("Error loading image: " + e.getMessage());
        }

    }

    @FXML
    void acheterHandler(ActionEvent event) throws SQLException, IOException {
        if (uc.buyCourse(userLoggedIn, c.getCoach().getId(), c.getId(), c.getPrix()))
            allCourses.generateCoursePDF(c, gs.getGamerById(userLoggedIn));
        refresh(event);
    }

    @FXML
    void addToWishlistHandler(ActionEvent event) throws SQLException, IOException {
        uc.addToWishlist(userLoggedIn, c.getId());
        refresh(event);
    }

    @FXML
    void removeFromWishlist(ActionEvent event) throws SQLException, IOException {
        uc.deleteFromWishlist(userLoggedIn, c.getId());
        refresh(event);
    }

    @FXML
    void openVideo(MouseEvent event) {
        try {
            Cours course = sc.getCourseById(c.getId());
            String path = "C:/Users/moham/OneDrive/Bureau/pidev/SuieDesktop/src/com/esprit/workshop/uploads/video/" + course.getVideo(); // Assuming this is the path of the video file
            System.out.println(path);
            File file = new File(path);
            Desktop.getDesktop().open(file); // Open the file with the default player
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
    public void refresh(ActionEvent event) throws IOException {
        URL url = getClass().getResource("./../gui/allCourses.fxml");
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

}


