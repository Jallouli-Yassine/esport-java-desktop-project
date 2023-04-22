/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.workshop.entites;

/**
 *
 * @author FGH
 */
public class Cours {

    private int id;
    private int prix;
    private int etat;
    private Coach coach;
    private Jeux jeux;
    private String titre, description,video,image,niveau;


    public Cours() {
    }

    public Cours(int prix, int etat, Coach coach, Jeux jeux, String titre, String description, String video, String image, String niveau) {
        this.prix = prix;
        this.etat = etat;
        this.coach = coach;
        this.jeux = jeux;
        this.titre = titre;
        this.description = description;
        this.video = video;
        this.image = image;
        this.niveau = niveau;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public Coach getCoach() {
        return coach;
    }

    public void setCoach(Coach coach) {
        this.coach = coach;
    }

    public Jeux getJeux() {
        return jeux;
    }

    public void setJeux(Jeux jeux) {
        this.jeux = jeux;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    @Override
    public String toString() {
        return "Cours{" +
                "id=" + id +
                ", prix=" + prix +
                ", etat=" + etat +
                ", coach= {" + coach + "}"+
                ", jeux=" + jeux +
                ", titre='" + titre + '\'' +
                ", description='" + description + '\'' +
                ", video='" + video + '\'' +
                ", image='" + image + '\'' +
                ", niveau='" + niveau + '\'' +
                '}';
    }
}
