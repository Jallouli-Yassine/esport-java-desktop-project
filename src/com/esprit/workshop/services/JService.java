package com.esprit.workshop.services;


import com.esprit.workshop.entites.Jeux;

import java.sql.SQLException;
import java.util.List;


public interface JService<T> {

    int insertOne(T t) throws SQLException;

    void updateOne(T t) throws SQLException;



    void deleteOne(int id) throws SQLException;

    List<T> selectAll() throws SQLException;

    List<Jeux> getByName(String nomGame) throws SQLException;
}