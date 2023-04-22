package com.esprit.workshop.services;

import com.esprit.workshop.entites.Coach;
import com.esprit.workshop.utils.MyConnexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CoachService {
    private Connection cnx;

    public CoachService() {
        cnx = MyConnexion.getInstance().getCnx();
    }

    public Coach getCoachById(int idCoach) throws SQLException {
        String req = "SELECT * FROM `user` u INNER JOIN `coach` c ON u.id = c.id WHERE u.id = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, idCoach);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            Coach c = new Coach();

            c.setId(rs.getInt("id"));
            c.setNom(rs.getString("nom"));
            c.setPrenom(rs.getString("prenom"));
            c.setEmail(rs.getString("email"));
            c.setPassword(rs.getString("password"));
            c.setDate_naissance(rs.getDate("date_naissance"));
            c.setPoint(rs.getFloat("point"));
            //c.setPhone(rs.getString("phone"));
            //c.setAbout(rs.getString("about"));
            //c.setPhoto_profile(rs.getString("photo_profile"));
            //c.setPhoto_couverture(rs.getString("photo_couverture"));
            //c.setBan(rs.getBoolean("ban"));
            //c.setStatut(rs.getBoolean("statut"));
            //c.setValidEmail(rs.getBoolean("validEmail"));

            //c.setId(rs.getInt("id"));
            c.setReview(rs.getFloat("review"));
            c.setCv(rs.getString("cv"));
            //c.setApprouver(rs.getBoolean("approuver"));

            return c;
        } else {
            throw new SQLException("No Gamer found with id " + idCoach);
        }
    }
}
