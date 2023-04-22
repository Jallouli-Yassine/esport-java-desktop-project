/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.workshop.controllers;

import com.esprit.workshop.entites.Coach;
import com.esprit.workshop.entites.Cours;
import com.esprit.workshop.entites.Jeux;
import com.esprit.workshop.services.CoachService;
import com.esprit.workshop.services.ServiceCours;
import com.esprit.workshop.services.ServiceJeux;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.UUID;


public class CoachCourses implements Initializable {
    @FXML
    private Button reset;

    @FXML
    private Button delete;

    @FXML
    private ComboBox<Jeux> allGames;

    @FXML
    private Button btnAjouter;

    @FXML
    private TextField courseDescription;

    @FXML
    private TextField courseImage;

    @FXML
    private TextField coursePrice;

    @FXML
    private TextField courseTitle;

    @FXML
    private TextField courseVideo;

    @FXML
    private ComboBox<String> niveau;

    @FXML
    private Button updatebtn;

    @FXML
    private ImageView upload;

    @FXML
    private ImageView uploadV;

    @FXML
    private ImageView afficherimage;

    @FXML
    private TableView<Cours> CoursesTable;

    @FXML
    private TableColumn<Cours, String> afImage = new TableColumn<Cours, String>();

    @FXML
    private TableColumn<Cours, String> afNiveau = new TableColumn<Cours, String>();

    @FXML
    private TableColumn<Cours, Integer> afPrix = new TableColumn<Cours, Integer>();


    @FXML
    private TableColumn<Cours, String> afTitre = new TableColumn<Cours, String>();

    @FXML
    private TableColumn<Cours, Void> state;

    @FXML
    private MediaView mediaView;


    private File selectedImageFile;
    private File selectedVideoFile;
    ServiceCours sc = new ServiceCours();
    CoachService cs = new CoachService();
    List<Cours> courses = null;
    String imageName;
    String videoName;
    Image img;
    Image imgUpload;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {


        afficherAll();
        CoursesTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) { // vérifie que l'utilisateur a cliqué une fois
                Cours coursSelectionne = CoursesTable.getSelectionModel().getSelectedItem(); // récupère l'objet Groupe sélectionné dans le TableView
                if (coursSelectionne != null) {


                    courseTitle.setText(coursSelectionne.getTitre()); // affiche le nom du groupe dans un TextField
                    //System.out.println(coursSelectionne.getImage());

                    courseDescription.setText(coursSelectionne.getDescription());
                    coursePrice.setText(String.valueOf(coursSelectionne.getPrix()));
                    courseImage.setText(coursSelectionne.getImage());
                    courseVideo.setText(coursSelectionne.getVideo());
                    niveau.setValue(coursSelectionne.getNiveau());
                    allGames.setValue(coursSelectionne.getJeux());
                    try {
                        img = new Image("C:/Users/moham/OneDrive/Bureau/pidev/SuieDesktop/src/com/esprit/workshop/uploads/picture/" + coursSelectionne.getImage());
                        System.out.println(coursSelectionne.getImage());
                        afficherimage.setImage(img);
                    } catch (Exception e) {
                        System.out.println("Error loading image: " + e.getMessage());
                    }


                }
            }
        });


        // Add items to the ComboBox
        ObservableList<Jeux> items = allGames.getItems();
        try {
            List<Jeux> jeuxList = new ServiceJeux().selectAll();
            items.addAll(jeuxList);
            // Set the cell factory to display the name of each Jeux entity in the ComboBox
            allGames.setCellFactory(param -> new ListCell<Jeux>() {
                @Override
                protected void updateItem(Jeux item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.getNomGame());
                    }
                }
            });

// Set the button cell to display the name of the selected Jeux entity
            allGames.setButtonCell(new ListCell<Jeux>() {
                @Override
                protected void updateItem(Jeux item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.getNomGame());
                    }
                }
            });
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


// Set the default value for the ComboBox
        //allGames.setValue(1);


        //**********************
        ObservableList<String> nv = niveau.getItems();
        nv.add("debutant");
        nv.add("intermediare");
        nv.add("avancée");


// Set the default value for the ComboBox
        niveau.setValue("un niveau");
    }

    @FXML
    private void AjouterCours(ActionEvent event) throws IOException, SQLException {

        if (courseTitle.getText().isEmpty() || coursePrice.getText().isEmpty() || courseDescription.getText().isEmpty() || courseImage.getText().isEmpty() || courseVideo.getText().isEmpty() || niveau.getSelectionModel().isEmpty() || allGames.getSelectionModel().isEmpty()) {
            Alert al = new Alert(Alert.AlertType.WARNING);
            al.setTitle("Erreur de donnee");
            al.setContentText("Veuillez verifier les données !");
            al.show();
        } else {
            if (coursePrice.getText().matches("[0-9]+") && courseTitle.getText().matches("^(?=.*[a-zA-Z])[a-zA-Z0-9]+$")) {
                Path destination = Paths.get("C:/Users/moham/OneDrive/Bureau/pidev/SuieDesktop/src/com/esprit/workshop/uploads/picture/" + imageName);

                Files.copy(selectedImageFile.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);

                Path destinationVideo = Paths.get("C:/Users/moham/OneDrive/Bureau/pidev/SuieDesktop/src/com/esprit/workshop/uploads/video/" + videoName);

                Files.copy(selectedVideoFile.toPath(), destinationVideo, StandardCopyOption.REPLACE_EXISTING);


                //Coach coach = new Coach(24,"John", "Doe", "johndoe@email.com", "password", new Date(), 4.5f, "123456789", "About me", "profile_photo.jpg", "cover_photo.jpg", "dehe.pdf");
                Jeux jeux = allGames.getValue();
                String nv = niveau.getValue();
                Coach coach = cs.getCoachById(24);
                System.out.println(coach);
                Cours c = new Cours(Integer.parseInt(coursePrice.getText()), 0, coach, jeux, courseTitle.getText(), courseDescription.getText(), courseVideo.getText(), courseImage.getText(), nv);
                ServiceCours sc = new ServiceCours();

                try {
                    sc.ajouterCours(c);
                    courses.clear();
                    afficherAll();
                    resetForm();
                } catch (SQLException ex) {
                    Alert al = new Alert(Alert.AlertType.ERROR);
                    al.setTitle("Erreur de donnee");
                    al.setContentText(ex.getMessage());
                    al.show();
                }

            } else {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle("Erreur de donnee");
                al.setContentText("verifier vos donner");
                al.show();
            }

        }

    }

    public void modifier(ActionEvent event) throws IOException {
        if (courseTitle.getText().isEmpty() || coursePrice.getText().isEmpty() || courseDescription.getText().isEmpty() || courseImage.getText().isEmpty() || courseVideo.getText().isEmpty() || niveau.getSelectionModel().isEmpty() || allGames.getSelectionModel().isEmpty()) {
            Alert al = new Alert(Alert.AlertType.WARNING);
            al.setTitle("Erreur de donnee");
            al.setContentText("Veuillez verifier les données !");
            al.show();
        } else {
            if (coursePrice.getText().matches("[0-9]+") && courseTitle.getText().matches("^(?=.*[a-zA-Z])[a-zA-Z0-9]+$")) {
                Path destination = Paths.get("C:/Users/moham/OneDrive/Bureau/pidev/SuieDesktop/src/com/esprit/workshop/uploads/picture/" + imageName);

                Files.copy(selectedImageFile.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);

                Path destinationVideo = Paths.get("C:/Users/moham/OneDrive/Bureau/pidev/SuieDesktop/src/com/esprit/workshop/uploads/video/" + videoName);

                Files.copy(selectedVideoFile.toPath(), destinationVideo, StandardCopyOption.REPLACE_EXISTING);

                int coachLoggedIn = 24;
                Coach coach = new Coach();
                Jeux jeux = allGames.getValue();
                String nv = niveau.getValue();

                int id = CoursesTable.getSelectionModel().getSelectedItems().get(0).getId();

                Cours c = new Cours(Integer.parseInt(coursePrice.getText()), 0, coach, jeux, courseTitle.getText(), courseDescription.getText(), courseVideo.getText(), courseImage.getText(), nv);

                try {
                    sc.modifierCours(c, id);
                    courses.clear();
                    afficherAll();
                    resetForm();
                } catch (SQLException ex) {
                    Alert al = new Alert(Alert.AlertType.ERROR);
                    al.setTitle("Erreur de donnee");
                    al.setContentText(ex.getMessage());
                    al.show();
                }

            }
        }


    }

    @FXML
    void supprimer(ActionEvent event) throws SQLException {
        try {
            // delete the course
            sc.supprimerCours(CoursesTable.getSelectionModel().getSelectedItems().get(0).getId());
            courses.remove(CoursesTable.getSelectionModel().getSelectedItems().get(0));
            courses.clear();
            afficherAll();
            resetForm();
            System.out.println("Course deleted successfully");
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Echeck");
            alert.setHeaderText("Cours a supprimé");
            alert.setContentText("Cannot delete the course because it has already been purchased by some users. !");
            alert.showAndWait();
        }


    }

    @FXML
    void ajouterAuFavori(MouseEvent event) throws IOException {

    }

    private void afficherAll() {
        try {
            courses = sc.afficherCours();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        afImage.setCellValueFactory(new PropertyValueFactory<>("description"));
        afPrix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        afTitre.setCellValueFactory(new PropertyValueFactory<>("titre"));
        afNiveau.setCellValueFactory(new PropertyValueFactory<>("niveau"));
        state.setCellFactory(column -> new TableCell<Cours, Void>() {
            ImageView imageView;
            Image imageAccepter = new Image("C:/Users/moham/OneDrive/Bureau/pidev/SuieDesktop/src/com/esprit/workshop/assets/accept.png",30,30,false,false);
            Image imageRefuser = new Image("C:/Users/moham/OneDrive/Bureau/pidev/SuieDesktop/src/com/esprit/workshop/assets/close.png",30,30,false,false);
            Image imageWait = new Image("C:/Users/moham/OneDrive/Bureau/pidev/SuieDesktop/src/com/esprit/workshop/assets/waiting.png",30,30,false,false);

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    Cours cours = getTableView().getItems().get(getIndex());
                    imageView = new ImageView();
                    if (cours.getEtat() == 1) {
                        imageView.setImage(imageAccepter);
                    } else if (cours.getEtat() == 0) {
                        imageView.setImage(imageWait);
                    } else {
                        imageView.setImage(imageRefuser);
                    }
                    setGraphic(imageView);
                }
            }
        });
        CoursesTable.setItems(FXCollections.observableList(courses));
    }

    @FXML
    void uploadCoursePicture(MouseEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));
        selectedImageFile = fileChooser.showOpenDialog(upload.getScene().getWindow());
        if (selectedImageFile != null) {
            Image image = new Image(selectedImageFile.toURI().toString());
            upload.setImage(image);

            // Générer un nom de fichier unique pour l'image
            String uniqueID = UUID.randomUUID().toString();
            String extension = selectedImageFile.getName().substring(selectedImageFile.getName().lastIndexOf(".")); // Récupérer

            imageName = uniqueID + extension;
            courseImage.setText(imageName);

        }
    }

    @FXML
    void uploadCourseVideo(MouseEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une vidéo");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Fichiers vidéo", "*.mp4", "*.avi", "*.mkv"));
        selectedVideoFile = fileChooser.showOpenDialog(upload.getScene().getWindow());
        if (selectedVideoFile != null) {
            String extension = selectedVideoFile.getName().substring(selectedVideoFile.getName().lastIndexOf(".")); // Récupérer l'extension
            String uniqueID = UUID.randomUUID().toString();
            videoName = uniqueID + extension;
            courseVideo.setText(videoName);
        }
    }

    // Function to reset form fields
    private void resetForm() {
        courseTitle.clear();
        courseDescription.clear();
        coursePrice.clear();
        courseVideo.clear();
        courseImage.clear();
        niveau.getSelectionModel().clearSelection();
        allGames.getSelectionModel().clearSelection();
        // Reset image view
        img = null;
        imgUpload = new Image("C:/Users/moham/OneDrive/Bureau/pidev/SuieDesktop/src/com/esprit/workshop/assets/download.png");
        afficherimage.setImage(img);
        upload.setImage(imgUpload);

    }

    @FXML
    void reset(ActionEvent event) {
        resetForm();
    }

    @FXML
    void goTo(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("./../gui/allCourses.fxml")));

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    /*
    @FXML
    void gotoUpdate(ActionEvent event) {
        // create a new instance of the FXMLLoader
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateCourse.fxml"));

// load the new scene
        Parent root = null;
        try {
            root = loader.load();
            // get the controller for the new scene
            UpdateCourseController controller = loader.getController();

// set any necessary properties on the controller
            //controller.setProperty("value");

// get the current stage
            Scene oldScene = btnAjouter.getScene();
            Stage stage = (Stage) oldScene.getRoot().getScene().getWindow();

// set the new scene on the stage
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
    */
}
