package com.esprit.workshop.services;

import com.esprit.workshop.entites.Cours;
import com.esprit.workshop.entites.Jeux;
import com.esprit.workshop.utils.MyConnexion;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserCourses implements UserCoursesInterface {

    private Connection cnx;

    public UserCourses() {
        cnx = MyConnexion.getInstance().getCnx();
    }

    @Override
    public void addToWishlist(int userId, int courseId) throws SQLException {
        // Check if course is already in user's wishlist with favori flag set to true
        String checkQuery = "SELECT * FROM user_courses WHERE id_gamer_id = ? AND id_cours_id = ? AND favori = 1";
        PreparedStatement checkStmt = cnx.prepareStatement(checkQuery);
        checkStmt.setInt(1, userId);
        checkStmt.setInt(2, courseId);
        ResultSet checkRs = checkStmt.executeQuery();
        boolean courseInWishlist = checkRs.next();

        // Check if course has already been purchased
        String checkPurchaseQuery = "SELECT * FROM user_courses WHERE id_gamer_id = ? AND id_cours_id = ? AND acheter = 1";
        PreparedStatement checkPurchaseStmt = cnx.prepareStatement(checkPurchaseQuery);
        checkPurchaseStmt.setInt(1, userId);
        checkPurchaseStmt.setInt(2, courseId);
        ResultSet checkPurchaseRs = checkPurchaseStmt.executeQuery();
        boolean coursePurchased = checkPurchaseRs.next();

        if (courseInWishlist) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("echec");
            alert.setHeaderText("Add to wishlist?");
            alert.setContentText("Course already in your Wishlist!");
            alert.showAndWait();
        } else if (coursePurchased) {
            // Update the user_courses table to set acheter to true
            String updateQuery = "UPDATE user_courses SET Favori = 1 WHERE id_gamer_id = ? AND id_cours_id = ?";
            PreparedStatement updateStmt = cnx.prepareStatement(updateQuery);
            updateStmt.setInt(1, userId);
            updateStmt.setInt(2, courseId);
            updateStmt.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Enjoy");
            alert.setHeaderText("Add to wishlist?");
            alert.setContentText("Course added successfully to wishlist!");
            alert.showAndWait();
        } else {
            // add course to user's wishlist
            String addQuery = "INSERT INTO user_courses (id_gamer_id, id_cours_id, favori,acheter) VALUES (?, ?, 1,0)";
            PreparedStatement addStmt = cnx.prepareStatement(addQuery);
            addStmt.setInt(1, userId);
            addStmt.setInt(2, courseId);
            addStmt.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("enjoy");
            alert.setHeaderText("add to wishlist ? ");
            alert.setContentText("Course added successfuly to WISHLIST!");
            alert.showAndWait();
        }


    }

    @Override
    public boolean buyCourse(int userId, int coachId, int courseId, int prixCourse) throws SQLException {
        // Check if user has enough points to buy the course
        String checkPointsQuery = "SELECT point FROM user WHERE id = ?";
        PreparedStatement checkPointsStmt = cnx.prepareStatement(checkPointsQuery);
        checkPointsStmt.setInt(1, userId);
        ResultSet checkPointsRs = checkPointsStmt.executeQuery();
        checkPointsRs.next();
        int userPoints = checkPointsRs.getInt("point");

        if (userPoints < prixCourse) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Buy course");
            alert.setContentText("You don't have enough points to buy this course!");
            alert.showAndWait();
            return false;
        } else {

            // Check if course is already in user wishlist
            String checkQuery = "SELECT * FROM user_courses WHERE id_gamer_id = ? AND id_cours_id = ? AND favori = 1";
            PreparedStatement checkStmt = cnx.prepareStatement(checkQuery);
            checkStmt.setInt(1, userId);
            checkStmt.setInt(2, courseId);
            ResultSet checkRs = checkStmt.executeQuery();
            boolean courseInWishlist = checkRs.next();


            // Check if course has already purchased
            String checkPurchasedQuery = "SELECT * FROM user_courses WHERE id_gamer_id = ? AND id_cours_id = ? AND acheter = 1";
            PreparedStatement checkPurchasedStmt = cnx.prepareStatement(checkPurchasedQuery);
            checkPurchasedStmt.setInt(1, userId);
            checkPurchasedStmt.setInt(2, courseId);
            ResultSet checkPurchasedRs = checkPurchasedStmt.executeQuery();
            boolean courseAlreadyPurchased = checkPurchasedRs.next();

            if (courseAlreadyPurchased) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Buy course");
                alert.setContentText("You have already purchased this course!");
                alert.showAndWait();
                return false;
            } else if (courseInWishlist) {
                // -- soum ml point mta gamer
                String deletPointsQuery = "UPDATE user SET point = point - ? where id = ?";
                PreparedStatement updatePointsStmt = cnx.prepareStatement(deletPointsQuery);
                updatePointsStmt.setInt(1, prixCourse);
                updatePointsStmt.setInt(2, userId);
                updatePointsStmt.executeUpdate();

                // ++ soum lel point mta coach
                String addPointsQuery = "UPDATE user SET point = point + ? where id = ?";
                PreparedStatement updatePointsCoachStmt = cnx.prepareStatement(addPointsQuery);
                updatePointsCoachStmt.setInt(1, prixCourse);
                updatePointsCoachStmt.setInt(2, coachId);
                updatePointsCoachStmt.executeUpdate();

                String updateQuery = "UPDATE user_courses SET acheter = 1 WHERE id_gamer_id = ? AND id_cours_id = ?";
                PreparedStatement updateStmt = cnx.prepareStatement(updateQuery);
                updateStmt.setInt(1, userId);
                updateStmt.setInt(2, courseId);
                updateStmt.executeUpdate();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Enjoy");
                alert.setHeaderText("Buy course");
                alert.setContentText("Course purchased successfully!");
                alert.showAndWait();
                return true;
            } else {
                // -- soum ml point mta gamer
                String deletPointsQuery = "UPDATE user SET point = point - ? where id = ?";
                PreparedStatement updatePointsStmt = cnx.prepareStatement(deletPointsQuery);
                updatePointsStmt.setInt(1, prixCourse);
                updatePointsStmt.setInt(2, userId);
                updatePointsStmt.executeUpdate();

                // ++ soum lel point mta coach
                String addPointsQuery = "UPDATE user SET point = point + ? where id = ?";
                PreparedStatement updatePointsCoachStmt = cnx.prepareStatement(addPointsQuery);
                updatePointsCoachStmt.setInt(1, prixCourse);
                updatePointsCoachStmt.setInt(2, coachId);
                updatePointsCoachStmt.executeUpdate();

                // Add course to user's wishlist with favori and acheter flags set to false
                String addQuery = "INSERT INTO user_courses (id_gamer_id, id_cours_id, favori, acheter) VALUES (?, ?, 0, 1)";
                PreparedStatement addStmt = cnx.prepareStatement(addQuery);
                addStmt.setInt(1, userId);
                addStmt.setInt(2, courseId);
                addStmt.executeUpdate();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Enjoy");
                alert.setHeaderText("Buy course");
                alert.setContentText("Course purchased successfully!");
                alert.showAndWait();
                return true;
            }
        }

    }


    public Boolean isInWishList(int userId, int courseId) throws SQLException {

        String req = "SELECT * FROM user_courses WHERE id_gamer_id  = ? and id_cours_id = ? and  Favori = 1 ";
        PreparedStatement st = cnx.prepareStatement(req);
        st.setInt(1, userId);
        st.setInt(2, courseId);
        ResultSet rs = st.executeQuery();

        //System.out.println(rs.next());
        if(rs.next()) return true;
        return false;

    }


    @Override
    public List<Cours> afficherWishlist(int userId) throws SQLException {
        List<Cours> temp = new ArrayList<>();

        String req = "SELECT * FROM cours INNER JOIN user_courses ON cours.id = user_courses.id_cours_id WHERE user_courses.id_gamer_id = ? and Favori = 1";

        PreparedStatement st = cnx.prepareStatement(req);
        st.setInt(1, userId);

        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            Cours c = new Cours();
            CoachService cs = new CoachService();
            c.setTitre(rs.getString(4));
            c.setId(rs.getInt(1));

            c.setCoach(cs.getCoachById((rs.getInt("id_coach_id")))); // retrieve coach from database
            c.setJeux(new Jeux(rs.getInt(3)));
            c.setDescription(rs.getString("description"));
            c.setVideo(rs.getString("video"));
            c.setImage(rs.getString("image"));
            c.setPrix(rs.getInt("prix"));
            c.setNiveau(rs.getString("niveau"));
            c.setEtat(rs.getInt("etat"));

            temp.add(c);
        }

        return temp;
    }

    @Override
    public List<Cours> afficherCoursAcheter(int userId) throws SQLException {
        List<Cours> temp = new ArrayList<>();

        String req = "SELECT * FROM cours INNER JOIN user_courses ON cours.id = user_courses.id_cours_id WHERE user_courses.id_gamer_id = ? and acheter = 1";

        PreparedStatement st = cnx.prepareStatement(req);
        st.setInt(1, userId);

        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            Cours c = new Cours();
            CoachService cs = new CoachService();
            c.setTitre(rs.getString(4));
            c.setId(rs.getInt(1));

            c.setCoach(cs.getCoachById((rs.getInt("id_coach_id")))); // retrieve coach from database
            c.setJeux(new Jeux(rs.getInt(3)));
            c.setDescription(rs.getString("description"));
            c.setVideo(rs.getString("video"));
            c.setImage(rs.getString("image"));
            c.setPrix(rs.getInt("prix"));
            c.setNiveau(rs.getString("niveau"));
            c.setEtat(rs.getInt("etat"));

            temp.add(c);
        }

        return temp;
    }


    @Override
    public void deleteFromWishlist(int userId, int courseId) throws SQLException {
        // Check if course has already purchased
        String checkPurchasedQuery = "SELECT * FROM user_courses WHERE id_gamer_id = ? AND id_cours_id = ? AND acheter = 1";
        PreparedStatement checkPurchasedStmt = cnx.prepareStatement(checkPurchasedQuery);
        checkPurchasedStmt.setInt(1, userId);
        checkPurchasedStmt.setInt(2, courseId);
        ResultSet checkPurchasedRs = checkPurchasedStmt.executeQuery();
        boolean courseAlreadyPurchased = checkPurchasedRs.next();

        if (courseAlreadyPurchased) {
            // Update the user_courses table to set acheter to true
            String updateQuery = "UPDATE user_courses SET Favori = 0 WHERE id_gamer_id = ? AND id_cours_id = ?";
            PreparedStatement updateStmt = cnx.prepareStatement(updateQuery);
            updateStmt.setInt(1, userId);
            updateStmt.setInt(2, courseId);
            updateStmt.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Enjoy");
            alert.setHeaderText("remove from your wishlist?");
            alert.setContentText("Course removed successfully from your wishlist!");
            alert.showAndWait();
        } else {
            // remove course from user's wishlist
            String removeQuery = "DELETE FROM user_courses WHERE id_gamer_id = ? AND id_cours_id = ? AND favori = 1";
            PreparedStatement removeStmt = cnx.prepareStatement(removeQuery);
            removeStmt.setInt(1, userId);
            removeStmt.setInt(2, courseId);
            int rowsDeleted = removeStmt.executeUpdate();
            if (rowsDeleted > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("success");
                alert.setHeaderText("remove from wishlist ? ");
                alert.setContentText("Course removed from wishlist!");
                alert.showAndWait();
                System.out.println("Course removed from wishlist");
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("error");
                alert.setHeaderText("remove from wishlist ? ");
                alert.setContentText("Course was not in wishlist!");
                alert.showAndWait();
                System.out.println("Course was not in wishlist");
            }
        }

    }

}
