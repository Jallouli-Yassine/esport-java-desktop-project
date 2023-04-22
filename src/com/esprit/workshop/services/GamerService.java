package com.esprit.workshop.services;

import com.esprit.workshop.entites.Gamer;
import com.esprit.workshop.utils.MyConnexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class GamerService {

    private Connection cnx;

    public GamerService() {
        cnx = MyConnexion.getInstance().getCnx();
    }

    public Gamer getGamerById(int idGamer) throws SQLException {
        String req = "SELECT * FROM `user` WHERE `id` = ? and discriminator  = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, idGamer);
        ps.setString(2, "Gamer");

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            Gamer g = new Gamer();

            g.setNom(rs.getString("nom"));

            return g;
        } else {
            throw new SQLException("No Gamer found with id " + idGamer);
        }
    }

}