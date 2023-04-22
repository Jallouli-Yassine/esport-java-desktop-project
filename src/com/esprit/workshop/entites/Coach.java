package com.esprit.workshop.entites;

import java.util.Date;

public class Coach extends User{

    private int id;
    private float review;
    private String cv;
    private Boolean approuver;

    public Coach(int id,String nom, String prenom, String email, String password, Date date_naissance, float point, String phone, String about, String photo_profile, String photo_couverture,  String cv) {
        super(nom, prenom, email, password, date_naissance, point, phone, about, photo_profile, photo_couverture);
        this.id=id;
        this.review = 0;
        this.cv = cv;
        this.approuver=false;
    }

    public Coach() {
        this.approuver=false;
    }


    public float getReview() {
        return review;
    }

    public void setReview(float review) {
        this.review = review;
    }

    public String getCv() {
        return cv;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }

    public Boolean getApprouver() {
        return approuver;
    }

    public void setApprouver(Boolean approuver) {
        this.approuver = approuver;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return   super.toString() +
                ", id=" + id +
                ", review=" + review +
                ", cv='" + cv + '\'' +
                ", approuver=" + approuver ;
    }
}