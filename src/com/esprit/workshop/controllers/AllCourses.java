package com.esprit.workshop.controllers;

import com.esprit.workshop.entites.Cours;
import com.esprit.workshop.entites.Gamer;
import com.esprit.workshop.entites.Summoner;
import com.esprit.workshop.services.GamerService;
import com.esprit.workshop.services.ServiceCours;
import com.esprit.workshop.services.UserCourses;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

/*leage api imports*/


//import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;


public class AllCourses implements Initializable {
    @FXML
    private Button btnLOL;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField inputLOL;

    @FXML
    private Label rankLabel;
    /*Course Card*/
    @FXML
    private HBox container;

    @FXML
    private TextField searchInput;

    /*LEAGUE API*/
    @FXML
    private TextField PlayerName;


    List<Cours> courses = null;

    ServiceCours sc = new ServiceCours();
    UserCourses uc = new UserCourses();
    GamerService gs = new GamerService();
    int userLoggedIn = 26;
    String summonerName = "";
    public static Summoner s = new Summoner();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            searchCourses();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        container.setSpacing(20); // set the spacing to 10 pixels
        try {
            courses = sc.afficherCoursAxepter();
            for (Cours c : courses) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("./../gui/CourseCard.fxml"));
                AnchorPane cardRoot = loader.load();
                CourseCard cardController = loader.getController();
                cardController.initialize(c);
                container.getChildren().add(cardRoot);
            }
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }

    }

    @FXML
    void leagueData(ActionEvent event) {
        summonerName = PlayerName.getText();
        System.out.println("summonerName : " + summonerName);
        if (!summonerName.equals("")) {
            String apiKey = "RGAPI-be2945fc-3963-426b-92ca-83cfd082f978";

            try {
                // Construct the API request URL
                String urlString = "https://euw1.api.riotgames.com/lol/summoner/v4/summoners/by-name/" + summonerName + "?api_key=" + apiKey;
                URL url = new URL(urlString);

                // Send the API request
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");

                // Parse the API response
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                JSONObject jsonObject = new JSONObject(response.toString());
                // Extract information from the response
                String summonerName = jsonObject.getString("name");
                String summonerElo = getSummonerElo(apiKey, jsonObject.getString("id"));
                Integer summonerLevel = jsonObject.getInt("summonerLevel");
                Integer profileIconId = jsonObject.getInt("profileIconId");

                s.setRank(summonerElo);
                s.setSummonerName(summonerName);
                s.setSummonerLevel(summonerLevel);
                s.setProfileIconId(profileIconId);
                //System.out.println("SUMMONER : " + s);

                LeagueData leagueData = new LeagueData();
                leagueData.setSummoner(s);
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("./../gui/LeagueData.fxml"));
                fxmlLoader.setController(leagueData);
                Parent root = fxmlLoader.load();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();


            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    private static String getSummonerElo(String apiKey, String summonerId) throws Exception {
        String urlString = "https://euw1.api.riotgames.com/lol/league/v4/entries/by-summoner/" + summonerId + "?api_key=" + apiKey;
        URL url = new URL(urlString);

        // Send the API request
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        // Parse the API response
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        // Extract information from the response
        JSONArray jsonArray = new JSONArray(response.toString());

        JSONObject jsonObject = null;
        if (jsonArray.length() == 1) {
            jsonObject = jsonArray.getJSONObject(0);
        } else if (jsonArray.length() >= 2) {
            jsonObject = jsonArray.getJSONObject(1);
        }


        String summonerElo = jsonObject.getString("tier") + " " + jsonObject.getString("rank");
        Integer wins = jsonObject.getInt("wins");
        Integer losses = jsonObject.getInt("losses");
        String queueType = jsonObject.getString("queueType");
        String tier = jsonObject.getString("tier");
        s.setWins(wins);
        s.setLosses(losses);
        s.setQueueType(queueType);
        s.setTier(tier);
        return summonerElo;
    }

    private void searchCourses() throws SQLException {
        container.getChildren().clear(); // vider le container
        container.setSpacing(20); // set the spacing to 10 pixels
        searchInput.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("old : " + oldValue);
            System.out.println("new : " + newValue);
            System.out.println("observable : " + observable);
            try {
                courses = sc.searchCourses(newValue);
                container.getChildren().clear(); // clear the container
                for (Cours c : courses) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("./../gui/CourseCard.fxml"));
                    AnchorPane cardRoot = loader.load();
                    CourseCard cardController = loader.getController();
                    cardController.initialize(c);
                    container.getChildren().add(cardRoot);
                }
            } catch (SQLException | IOException throwables) {
                throwables.printStackTrace();
            }
        });
    }


    @FXML
    void clearFilter(ActionEvent event) {
        container.getChildren().clear(); // vider le container
        container.setSpacing(20); // set the spacing to 10 pixels
        try {
            courses = sc.afficherCoursAxepter();
            for (Cours c : courses) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("./../gui/CourseCard.fxml"));
                AnchorPane cardRoot = loader.load();
                CourseCard cardController = loader.getController();
                cardController.initialize(c);
                container.getChildren().add(cardRoot);
            }
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }


    }

    @FXML
    void afficherBuyed(ActionEvent event) throws SQLException {
        container.getChildren().clear(); // vider le container
        courses = null;
        container.setSpacing(20); // set the spacing to 10 pixels
        try {
            courses = uc.afficherCoursAcheter(userLoggedIn);
            for (Cours c : courses) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("./../gui/CourseCard.fxml"));
                AnchorPane cardRoot = loader.load();
                CourseCard cardController = loader.getController();
                cardController.initialize(c);
                container.getChildren().add(cardRoot);
            }
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }


    }

    @FXML
    void afficherWishlist(ActionEvent event) {
        container.getChildren().clear(); // vider le container

        courses = null;
        container.setSpacing(20); // set the spacing to 10 pixels
        try {

            courses = uc.afficherWishlist(userLoggedIn);
            for (Cours c : courses) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("./../gui/CourseCard.fxml"));
                AnchorPane cardRoot = loader.load();
                CourseCard cardController = loader.getController();
                cardController.initialize(c);
                container.getChildren().add(cardRoot);
            }
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }
    }


    public void acheterCours(int idCours, int prixCourse, int coachId) throws SQLException, IOException {
        if (uc.buyCourse(userLoggedIn, coachId, idCours, prixCourse)) {
            //afficherFavori();
            Cours course = sc.getCourseById(idCours); // Retrieve course data from database
            Gamer gamer = gs.getGamerById(26); // Retrieve course data from database
            System.out.println(course);
            generateCoursePDF(course, gamer); // Generate PDF with course data
        }

    }

    /*
    private void handleSearch() {
        String name = inputLOL.getText();
        if (name.isEmpty()) {
            errorLabel.setText("Please enter a summoner name.");
            return;
        }

        try {
            Summoner summoner = getSummonerByName(name);
            List<League> leagues = getLeaguesBySummonerId(summoner.getId());
            String rank = leagues.isEmpty() ? "Unranked" : leagues.get(0).getTier();

            List<Course> courses = getCoursesByRankAndGame(rank, "LEAGUE OF LEGENDS");
            courseListView.getItems().setAll(courses);

            errorLabel.setText("");
            rankLabel.setText("Rank: " + rank);
        } catch (IOException e) {
            errorLabel.setText("Error connecting to Riot API.");
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            errorLabel.setText("Error parsing JSON data from Riot API.");
            e.printStackTrace();
        }
    }

    private Summoner getSummonerByName(String name) throws IOException {
        String uri = "https://euw1.api.riotgames.com/lol/summoner/v4/summoners/by-name/" + URLEncoder.encode(name, "UTF-8") + "?api_key=" + DAK;
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(uri)).build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new IOException("Error getting summoner data. Status code: " + response.statusCode());
        }
        return gson.fromJson(response.body(), Summoner.class);
    }

    private List<League> getLeaguesBySummonerId(String summonerId) throws IOException {
        String uri = "https://euw1.api.riotgames.com/lol/league/v4/entries/by-summoner/" + summonerId + "?api_key=" + DAK;
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(uri)).build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new IOException("Error getting league data. Status code: " + response.statusCode());
        }
        return gson.fromJson(response.body(), new TypeToken<List<League>>() {
        }.getType());
    }

    private List<Course> getCoursesByRankAndGame(String rank, String game) {
        CourseRepository courseRepository = new CourseRepository();
        if (rank != null) {
            return courseRepository.findByRankAndGame(rank, game);
        } else {
            return courseRepository.findByGame(game);
        }
    }
*/
    public void generateCoursePDF(Cours course, Gamer gamer) throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        // Load the image from URL and save to a file

        URL urlLogo = new URL("https://scontent.ftun10-1.fna.fbcdn.net/v/t1.15752-9/334880930_1535330956988909_4636024716374936235_n.png?stp=dst-png_p1080x2048&_nc_cat=101&ccb=1-7&_nc_sid=ae9488&_nc_ohc=tMtzk0ewdvUAX90fjLN&_nc_ht=scontent.ftun10-1.fna&oh=03_AdSlfYn0BXY-sDdnIFS-gPbqH9_2IUfOdomp9PxM_wWAyA&oe=6464A956");
        URL urlPrice = new URL("https://scontent.ftun10-1.fna.fbcdn.net/v/t1.15752-9/334872631_757189845663698_3410744926867954078_n.png?_nc_cat=101&ccb=1-7&_nc_sid=ae9488&_nc_ohc=_pj1fsUMaScAX9gjOvn&_nc_ht=scontent.ftun10-1.fna&oh=03_AdQeL__Pq2yyhdlXf99S74MI-ciKJypTSLwYKunPPD5Wxw&oe=6464B8FB");

        BufferedImage CoursePicture = ImageIO.read(new File("C:/Users/moham/OneDrive/Bureau/pidev/SuieDesktop/src/com/esprit/workshop/uploads/picture/" + course.getImage()));
        BufferedImage imageLogo = ImageIO.read(urlLogo);
        BufferedImage imagePrice = ImageIO.read(urlPrice);

        // PDImageXObject mayekbl ken file mch buffered iamge
        PDImageXObject pdCoursePicture = LosslessFactory.createFromImage(document, CoursePicture);
        PDImageXObject pdImage = LosslessFactory.createFromImage(document, imageLogo);
        PDImageXObject pdImagePrice = LosslessFactory.createFromImage(document, imagePrice);

        // Draw the image on the PDF page
        contentStream.drawImage(pdCoursePicture, 400, 500, 220, 190);


        // Add text content
        contentStream.beginText();
        // Create a Color object for the desired text color
        Color textColor = Color.decode("#23002d");

// Set the text color using setNonStrokingColor(Color)
        contentStream.setNonStrokingColor(textColor);

        contentStream.setFont(PDType1Font.COURIER_BOLD, 25);
        contentStream.newLineAtOffset(220, 700);
        contentStream.showText("FACTURE PDF");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setNonStrokingColor(0, 0, 0); // reset color to black
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
        contentStream.newLineAtOffset(100, 650);
        contentStream.showText("Course Title: " + course.getTitre());

        contentStream.newLineAtOffset(0, -50);
        contentStream.showText("Course Description: " + course.getDescription());

        contentStream.newLineAtOffset(0, -50);
        contentStream.showText("Course Price: " + course.getPrix());

        contentStream.newLineAtOffset(0, -50);
        contentStream.showText("Coach of the course: " + course.getCoach().getNom());

        contentStream.newLineAtOffset(0, -50);
        contentStream.showText("Gamer name: " + gamer.getNom());

        contentStream.endText();
        if (course.getPrix() >= 10 && course.getPrix() < 100)
            contentStream.drawImage(pdImagePrice, 215, 547, 20, 20);

        if (course.getPrix() >= 100)
            contentStream.drawImage(pdImagePrice, 220, 547, 20, 20);

        if (course.getPrix() < 10)
            contentStream.drawImage(pdImagePrice, 205, 547, 20, 20);

        contentStream.beginText();

// Set the text color using setNonStrokingColor(Color)
        contentStream.setNonStrokingColor(textColor);

        contentStream.setFont(PDType1Font.COURIER_BOLD_OBLIQUE, 14);
        contentStream.newLineAtOffset(200, 390);
        contentStream.showText("Thank you for your purchase!");
        contentStream.endText();
        contentStream.drawImage(pdImage, 290, 335, 45, 45);

        contentStream.close();
        document.save("C:/Users/moham/OneDrive/Bureau/pidev/SuieDesktop/src/com/esprit/workshop/uploads/PDFs/" + course.getTitre() + "_" + gamer.getNom() + ".pdf");
        document.close();
    }

    /*
        void afficherFavori() {
            try {
                coursesF = uc.afficherWishlist(userLoggedIn);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            afPrixF.setCellValueFactory(new PropertyValueFactory<>("prix"));
            afTitreF.setCellValueFactory(new PropertyValueFactory<>("titre"));
            afNiveauF.setCellValueFactory(new PropertyValueFactory<>("niveau"));
            allCoursesFavori.setItems(FXCollections.observableList(coursesF));
        }

        void afficherAcheter() {
            try {
                coursesAcheter = uc.afficherCoursAcheter(userLoggedIn);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            PrixAcheter.setCellValueFactory(new PropertyValueFactory<>("prix"));
            TitreAcheter.setCellValueFactory(new PropertyValueFactory<>("titre"));
            NiveauAcheter.setCellValueFactory(new PropertyValueFactory<>("niveau"));
            allCoursesAcheter.setItems(FXCollections.observableList(coursesAcheter));
        }


        void afficherCourses() {
            try {
                courses = sc.afficherToutesCours();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            //afImage.setCellValueFactory(new PropertyValueFactory<>("image"));
            afPrix.setCellValueFactory(new PropertyValueFactory<>("prix"));
            afTitre.setCellValueFactory(new PropertyValueFactory<>("titre"));
            afNiveau.setCellValueFactory(new PropertyValueFactory<>("niveau"));
            allCourses.setItems(FXCollections.observableList(courses));
        }
    */
    @FXML
    void Back(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("./../gui/CoachCourses.fxml")));

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
