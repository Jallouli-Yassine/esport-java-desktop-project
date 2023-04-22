/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.workshop.services;

import com.esprit.workshop.entites.Cours;
import com.esprit.workshop.entites.Jeux;
import com.esprit.workshop.utils.MyConnexion;
import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ServiceCours implements IService<Cours> {

    private Connection cnx;

    public ServiceCours() {
        cnx = MyConnexion.getInstance().getCnx();
    }


    public void insertOne1(Cours c) throws SQLException {
        String req = "INSERT INTO `cours`(`id_coach_id`,`id_jeux_id`,`titre`, `description`, `video`, `image`, `prix`, `niveau`, `etat`) "
                + "VALUES ('" + c.getTitre() + "','" + c.getDescription() + "','" + c.getVideo() + "','" + c.getImage() + "','" + c.getPrix() + "','" + c.getNiveau() + "','" + c.getEtat() + " ')";
        Statement st = cnx.createStatement();
        st.executeUpdate(req);
        System.out.println("person ajouté !");
    }

    @Override
    public void ajouterCours(Cours c) throws SQLException {
        String req = "INSERT INTO `cours`(`titre`, `description`, `video`, `image`, `prix`, `niveau`, `etat`,`id_coach_id`,`id_jeux_id`) VALUES (?,?,?,?,?,?,?,?,?)";

        PreparedStatement ps = cnx.prepareStatement(req);

        ps.setString(1, c.getTitre());
        ps.setString(2, c.getDescription());
        ps.setString(3, c.getVideo());
        ps.setString(4, c.getImage());
        ps.setInt(5, c.getPrix());
        ps.setString(6, c.getNiveau());
        ps.setInt(7, c.getEtat());
        ps.setInt(8, c.getCoach().getId());
        ps.setInt(9, c.getJeux().getId());
        ps.executeUpdate();
        System.out.println("cours ajouté !");
    }


    @Override
    public void modifierCours(Cours c, int id) throws SQLException {
        System.out.println(c.getId());
        String req = "UPDATE `cours` SET `titre`='" + c.getTitre() + "', `description`='" + c.getDescription() + "', `video`='" + c.getVideo() + "', `image`='" + c.getImage() + "', `prix`='" + c.getPrix() + "', `niveau`='" + c.getNiveau() + "', `etat`='" + c.getEtat() + "' WHERE `id`=" + id;
        Statement st = cnx.createStatement();
        st.executeUpdate(req);
        System.out.println("cours modifier !");

    }

    /*
        @Override
        public void supprimerCours(Cours c) throws SQLException {
            String req = "DELETE FROM `cours` WHERE id = " + c.getId();
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("cours supprimer !");
        }
    */
    @Override
    public void supprimerCours(int id) throws SQLException {
        String req = "DELETE FROM `cours` WHERE id = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, id);

        int rowsDeleted = ps.executeUpdate();
        if (rowsDeleted > 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText("Cours supprimé");
            alert.setContentText("Le cours a été supprimé avec succès !");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Attention");
            alert.setHeaderText("Aucun Cours supprimé");
            alert.setContentText("Aucun cours n'a été supprimé !");
            alert.showAndWait();
        }
    }

    @Override
    public void accepterCours(int id) throws SQLException {
        String query = "UPDATE cours SET etat = ? where id = ?";
        PreparedStatement st = cnx.prepareStatement(query);
        st.setInt(1, 1);
        st.setInt(2, id);
        st.executeUpdate();
    }

    @Override
    public void refuserCours(int id) throws SQLException {
        String query = "UPDATE cours SET etat = ? where id = ?";
        PreparedStatement st = cnx.prepareStatement(query);
        st.setInt(1, -1);
        st.setInt(2, id);
        st.executeUpdate();
    }

    @Override
    public List<Cours> afficherToutesCours() throws SQLException {
        List<Cours> temp = new ArrayList<>();

        String req = "SELECT * FROM `cours`";
        Statement st = cnx.createStatement();

        ResultSet rs = st.executeQuery(req);

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
    public List<Cours> searchCourses(String search) throws SQLException {
        List<Cours> temp = new ArrayList<>();

        String req = "SELECT * FROM `cours` WHERE titre LIKE ? and etat = 1";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setString(1, "%" + search + "%");

        ResultSet rs = ps.executeQuery();

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
    public List<Cours> afficherCours() throws SQLException {
        List<Cours> temp = new ArrayList<>();

        String req = "SELECT * FROM `cours` where id_coach_id = 24";
        Statement st = cnx.createStatement();

        ResultSet rs = st.executeQuery(req);

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
    public List<Cours> afficherCoursAxepter() throws SQLException {
        List<Cours> temp = new ArrayList<>();

        String req = "SELECT * FROM `cours` where etat = 1";
        Statement st = cnx.createStatement();

        ResultSet rs = st.executeQuery(req);

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
    public List<Cours> afficherCoursWaiting() throws SQLException {
        List<Cours> temp = new ArrayList<>();

        String req = "SELECT * FROM `cours` where etat = 0";
        Statement st = cnx.createStatement();

        ResultSet rs = st.executeQuery(req);

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
    public List<Cours> afficherCoursRefuser() throws SQLException {
        List<Cours> temp = new ArrayList<>();

        String req = "SELECT * FROM `cours` where etat = -1";
        Statement st = cnx.createStatement();

        ResultSet rs = st.executeQuery(req);

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
    public Cours getCourseById(int idCours) throws SQLException {
        String req = "SELECT * FROM `cours` WHERE `id` = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, idCours);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            Cours c = new Cours();
            CoachService cs = new CoachService();
            c.setTitre(rs.getString("titre"));
            c.setId(rs.getInt("id"));
            c.setCoach(cs.getCoachById((rs.getInt("id_coach_id")))); // retrieve coach from database
            c.setJeux(new Jeux(rs.getInt("id_jeux_id")));
            c.setDescription(rs.getString("description"));
            c.setVideo(rs.getString("video"));
            c.setImage(rs.getString("image"));
            c.setPrix(rs.getInt("prix"));
            c.setNiveau(rs.getString("niveau"));
            c.setEtat(rs.getInt("etat"));

            return c;
        } else {
            throw new SQLException("No course found with id " + idCours);
        }
    }


}
