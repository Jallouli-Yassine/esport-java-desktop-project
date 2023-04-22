package com.esprit.workshop.services;

import com.esprit.workshop.entites.Jeux;
import com.esprit.workshop.utils.MyConnexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceJeux implements JService<Jeux> {

    private Connection cnx;

    public ServiceJeux() {
            cnx = MyConnexion.getInstance().getCnx();
    }

    @Override
    public List<Jeux> selectAll() throws SQLException {
        List<Jeux> temp = new ArrayList<>();

        String req = "SELECT * FROM `jeux`";
        Statement st = cnx.createStatement();

        ResultSet rs = st.executeQuery(req);

        while (rs.next()) {
            Jeux j = new Jeux();

            j.setId(rs.getInt(1));
            j.setNomGame(rs.getString(2));
            j.setDateAddGame(rs.getString(3));
            j.setMaxPlayers(rs.getInt(4));
            j.setImage(rs.getString(5));
            j.setDescription(rs.getString(6));
            temp.add(j);
        }

        return temp;

    }

    @Override
    public int insertOne(Jeux j) throws SQLException {
        String req = "INSERT INTO `jeux`(`nom_game`, `max_players`,`image`,`description`,`date_add_game`) "
                + "VALUES ('" + j.getNomGame() + "','" + j.getMaxPlayers() + "','" + j.getImage() + "','" + j.getDescription() + "', NOW())";
        Statement st = cnx.createStatement();
        st.executeUpdate(req, Statement.RETURN_GENERATED_KEYS);
        ResultSet rs = st.getGeneratedKeys();
        int id = -1;
        if (rs.next()) {
            id = rs.getInt(1);
            j.setId(id);
        }
        System.out.println("jeux ajouté avec l'id " + id);
        return id;
    }


    @Override
    public void updateOne(Jeux t) throws SQLException {
        String req = "UPDATE `jeux` SET `nom_game` = '"+t.getNomGame()+"', `description` = '"+t.getDescription()+"', `max_players` = '"+t.getMaxPlayers()+"', `image` = '"+t.getImage()+"' WHERE `id` = "+t.getId();
        Statement st = cnx.createStatement();
        st.executeUpdate(req);
        System.out.println("jeux mis à jour !");
    }

    @Override
    public void deleteOne(int id) throws SQLException {
        String req = "DELETE FROM `jeux` WHERE `id` = " + id;
        Statement st = cnx.createStatement();
        st.executeUpdate(req);
        System.out.println("jeux supprimé !");
    }
    public List<Jeux> getByName(String nomGame) throws SQLException {
        List<Jeux> jeux = new ArrayList<>();
        String sql = "SELECT * FROM jeux WHERE nom_game = ?";
        PreparedStatement statement = cnx.prepareStatement(sql);
        statement.setString(1, nomGame);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Jeux jeu = new Jeux();
            jeu.setId(resultSet.getInt("id"));
            jeu.setNomGame(resultSet.getString("nom_game"));
            jeu.setDescription(resultSet.getString("description"));
            jeu.setMaxPlayers(resultSet.getInt("max_players"));
            // add the retrieved Jeux instance to the list
            jeux.add(jeu);
        }
        return jeux;
    }






}