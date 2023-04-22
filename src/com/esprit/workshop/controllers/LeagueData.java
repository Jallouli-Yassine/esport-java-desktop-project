package com.esprit.workshop.controllers;

import com.esprit.workshop.entites.Summoner;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

import java.text.DecimalFormat;

public class LeagueData {

    @FXML
    private Label lv;

    @FXML
    private Label name;

    @FXML
    private ImageView playerIcon;

    @FXML
    private ImageView rankPicture;

    @FXML
    private Label rankedType;

    @FXML
    private Label winrate;

    @FXML
    private Button rankBTN;


    private Summoner summoner;
    private Image icon;
    private Image rankPic;

    public void initialize() {
        double winrateM = (double) summoner.getWins() / (summoner.getWins() + summoner.getLosses()) * 100.0;
        DecimalFormat df = new DecimalFormat("#.###");
        String formattedWinrate = df.format(winrateM);
        winrate.setText(formattedWinrate + " %");

        name.setText(summoner.getSummonerName());
        lv.setText(String.valueOf(summoner.getSummonerLevel()));
        rankedType.setText(summoner.getQueueType());


        icon = new Image("https://ddragon.leagueoflegends.com/cdn/13.4.1/img/profileicon/" + summoner.getProfileIconId() + ".png");
        playerIcon.setImage(icon);

// Set the clip on the ImageView
        Rectangle clip = new Rectangle(playerIcon.getFitWidth(), playerIcon.getFitHeight());
        clip.setArcWidth(playerIcon.getFitWidth());
        clip.setArcHeight(playerIcon.getFitHeight());
        playerIcon.setClip(clip);

        rankPic = new Image("https://opgg-static.akamaized.net/images/medals/" + summoner.getTier().toLowerCase() + "_1.png");
        rankPicture.setImage(rankPic);

        rankBTN.setText(summoner.getRank());

    }

    public void setSummoner(Summoner summoner) {
        this.summoner = summoner;
    }

}
