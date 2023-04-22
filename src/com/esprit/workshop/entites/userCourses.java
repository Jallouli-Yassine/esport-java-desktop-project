package com.esprit.workshop.entites;

public class userCourses {
    private int id,gamerId,courseId,review;
    private boolean  favori,acheter;
    private String description;

    public userCourses() {
    }

    public userCourses(int gamerId, int courseId, int review, boolean favori, boolean acheter, String description) {
        this.gamerId = gamerId;
        this.courseId = courseId;
        this.review = review;
        this.favori = favori;
        this.acheter = acheter;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGamerId() {
        return gamerId;
    }

    public void setGamerId(int gamerId) {
        this.gamerId = gamerId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getReview() {
        return review;
    }

    public void setReview(int review) {
        this.review = review;
    }

    public boolean isFavori() {
        return favori;
    }

    public void setFavori(boolean favori) {
        this.favori = favori;
    }

    public boolean isAcheter() {
        return acheter;
    }

    public void setAcheter(boolean acheter) {
        this.acheter = acheter;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "userCourses{" +
                "id=" + id +
                ", gamerId=" + gamerId +
                ", courseId=" + courseId +
                ", review=" + review +
                ", favori=" + favori +
                ", acheter=" + acheter +
                ", description='" + description + '\'' +
                '}';
    }
}
