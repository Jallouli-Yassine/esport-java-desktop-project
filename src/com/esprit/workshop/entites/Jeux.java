package com.esprit.workshop.entites;

public class Jeux {
    private Integer id;
    private String nomGame;
    private String dateAddGame;
    private Integer maxPlayers;
    private String image;
    private String description;

    public Jeux() {
    }

    public Jeux(String nomGame) {
        this.nomGame = nomGame;
    }

    public Jeux(String nomGame, Integer maxPlayers, String description) {
        this.nomGame = nomGame;
        this.maxPlayers = maxPlayers;
        this.description = description;
    }

    public Jeux(Integer id, String nomGame, String dateAddGame, Integer maxPlayers, String image, String description) {
        this.id = id;
        this.nomGame = nomGame;
        this.dateAddGame = dateAddGame;
        this.maxPlayers = maxPlayers;
        this.image = image;
        this.description = description;
    }

    public Jeux(int anInt) {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomGame() {
        return nomGame;
    }

    public void setNomGame(String nomGame) {
        this.nomGame = nomGame;
    }

    public String getDateAddGame() {
        return dateAddGame;
    }

    public void setDateAddGame(String dateAddGame) {
        this.dateAddGame = dateAddGame;
    }

    public Integer getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(Integer maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return nomGame;
    }

}