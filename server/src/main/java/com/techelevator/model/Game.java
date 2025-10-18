package com.techelevator.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class Game {
    private int objectId;
    @NotBlank(message = "The name field must not be blank.")
    private String name;
    @Min(value = 1, message = "The play time field must be greater than 0")
    private int playtime;
    @Min(value = 1, message = "The minimum number of players field must be greater than 0")
    private int minimumPlayers;
    @Min(value = 1, message = "The maximum number of players field must be greater than 0")
    private int maximumPlayers;
    @Min(value = 1, message = "The recommended age field must be greater than 0")
    private int recommendedAge;
    @NotBlank(message = "The publisher field cannot be blank.")
    private String publisher;
    private int userId;
    @Min(value = 1, message = "The average weight field must be greater than 0")
    private int avgWeight;
    private boolean retired;

    public Game(){
    }

    public Game(String name, int playtime, int minimumPlayers, int maximumPlayers, int recommendedAge, String publisher, int avgWeight) {
        this.name = name;
        this.playtime = playtime;
        this.minimumPlayers = minimumPlayers;
        this.maximumPlayers = maximumPlayers;
        this.recommendedAge = recommendedAge;
        this.publisher = publisher;
        this.avgWeight = avgWeight;
    }

    public Game(int objectId, String name, int playtime, int minimumPlayers, int maximumPlayers, int recommendedAge, String publisher, int userId, int avgWeight, boolean retired) {
        this.objectId = objectId;
        this.name = name;
        this.playtime = playtime;
        this.minimumPlayers = minimumPlayers;
        this.maximumPlayers = maximumPlayers;
        this.recommendedAge = recommendedAge;
        this.publisher = publisher;
        this.userId = userId;
        this.avgWeight = avgWeight;
        this.retired = retired;
    }

    public int getObjectId() {
        return objectId;
    }

    public String getName() {
        return name;
    }

    public int getPlaytime() {
        return playtime;
    }

    public int getMinimumPlayers() {
        return minimumPlayers;
    }

    public int getMaximumPlayers() {
        return maximumPlayers;
    }

    public int getRecommendedAge() {
        return recommendedAge;
    }

    public String getPublisher() {
        return publisher;
    }

    public int getUserId() {
        return userId;
    }

    public int getAvgWeight() {
        return avgWeight;
    }

    public boolean isRetired() {
        return retired;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlaytime(int playtime) {
        this.playtime = playtime;
    }

    public void setMinimumPlayers(int minimumPlayers) {
        this.minimumPlayers = minimumPlayers;
    }

    public void setMaximumPlayers(int maximumPlayers) {
        this.maximumPlayers = maximumPlayers;
    }

    public void setRecommendedAge(int recommendedAge) {
        this.recommendedAge = recommendedAge;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setAvgWeight(int avgWeight) {
        this.avgWeight = avgWeight;
    }

    public void setRetired(boolean retired) {
        this.retired = retired;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    @Override
    public String toString(){
    return "Game{" + "id=" + objectId + ", name='" + name + ", playing time=" + playtime + ", min/max number of players="
        + minimumPlayers + "/"+maximumPlayers +", recommended age="+ recommendedAge + ", publisher" + publisher + ", average complexity"+ avgWeight+"}";
    }
}
