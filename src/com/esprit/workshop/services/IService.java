/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.workshop.services;

import com.esprit.workshop.entites.Cours;

import java.sql.SQLException;
import java.util.List;


public interface IService<T> {

    void ajouterCours(T t) throws SQLException;

    void modifierCours(T t, int id) throws SQLException;

    //void supprimerCours(T t) throws SQLException;
    void supprimerCours(int id) throws SQLException;

    void accepterCours(int id) throws SQLException;

    void refuserCours(int id) throws SQLException;

    List<T> afficherCours() throws SQLException;

    List<T> afficherCoursAxepter() throws SQLException;

    List<T> afficherCoursWaiting() throws SQLException;

    List<T> afficherCoursRefuser() throws SQLException;

    List<T> afficherToutesCours() throws SQLException;

    List<T> searchCourses(String search) throws SQLException;

    Cours getCourseById(int idCours) throws SQLException;


}
