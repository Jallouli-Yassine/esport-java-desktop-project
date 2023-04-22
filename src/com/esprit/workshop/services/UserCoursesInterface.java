package com.esprit.workshop.services;

import com.esprit.workshop.entites.Cours;

import java.sql.SQLException;
import java.util.List;

public interface UserCoursesInterface {
    void addToWishlist(int userId, int courseId) throws SQLException;
    boolean buyCourse(int userId, int courseId,int coachId,int prixCourse) throws SQLException;

    List<Cours> afficherWishlist(int userId) throws SQLException;
    List<Cours> afficherCoursAcheter(int userId) throws SQLException;

    void deleteFromWishlist(int userId, int courseId) throws SQLException;

}