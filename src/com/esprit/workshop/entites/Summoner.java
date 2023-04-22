package com.esprit.workshop.entites;

public class Summoner {

    private String summonerName;
    private String rank;
    private String tier;
    private String queueType;
    private int wins;
    private int losses;
    private int profileIconId;
    private int summonerLevel;

    public Summoner() {
    }

    public Summoner(String summonerName, String rank, String tier, String queueType, int wins, int losses, int profileIconId, int summonerLevel) {
        this.summonerName = summonerName;
        this.rank = rank;
        this.tier = tier;
        this.queueType = queueType;
        this.wins = wins;
        this.losses = losses;
        this.profileIconId = profileIconId;
        this.summonerLevel = summonerLevel;
    }

    public String getSummonerName() {
        return summonerName;
    }

    public void setSummonerName(String summonerName) {
        this.summonerName = summonerName;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

    public String getQueueType() {
        return queueType;
    }

    public void setQueueType(String queueType) {
        this.queueType = queueType;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getProfileIconId() {
        return profileIconId;
    }

    public void setProfileIconId(int profileIconId) {
        this.profileIconId = profileIconId;
    }

    public int getSummonerLevel() {
        return summonerLevel;
    }

    public void setSummonerLevel(int summonerLevel) {
        this.summonerLevel = summonerLevel;
    }

    @Override
    public String toString() {
        return "Summoner{" +
                "summonerName='" + summonerName + '\'' +
                ", rank='" + rank + '\'' +
                ", tier='" + tier + '\'' +
                ", queueType='" + queueType + '\'' +
                ", wins=" + wins +
                ", losses=" + losses +
                ", profileIconId=" + profileIconId +
                ", summonerLevel=" + summonerLevel +
                '}';
    }
}
