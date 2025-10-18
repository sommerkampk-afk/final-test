package com.techelevator.model;

import jakarta.validation.constraints.*;
import com.techelevator.model.Game;
import com.techelevator.model.User;

public class Play {


        private Integer playId;

        @NotNull(message = "User ID is required")
        private Integer userId;

        @NotNull(message = "Game (object ID) is required")
        private Integer objectId;

        @NotBlank(message = "Winner name must not be blank")
        private String winner;

        private String legacyDevelopments;

        private String gameNotes;

        @Min(value = 1, message = "Rating must be at least 1")
        @Max(value = 10, message = "Rating cannot exceed 10")
        private Integer gameRating;
        private Game game;
        private User user;


        public Play() {
        }

        public Play(Integer playId, Integer userId, Integer objectId, String winner,
                    String legacyDevelopments, String gameNotes, Integer gameRating) {
            this.playId = playId;
            this.userId = userId;
            this.objectId = objectId;
            this.winner = winner;
            this.legacyDevelopments = legacyDevelopments;
            this.gameNotes = gameNotes;
            this.gameRating = gameRating;
        }


        public Integer getPlayId() {
            return playId;
        }

        public Integer getUserId() {
            return userId;
        }

        public Integer getObjectId() {
            return objectId;
        }

        public String getWinner() {
            return winner;
        }

        public String getLegacyDevelopments() {
            return legacyDevelopments;
        }

        public String getGameNotes() {
            return gameNotes;
        }

        public Integer getGameRating() {
            return gameRating;
        }


        public void setPlayId(Integer playId) {
            this.playId = playId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public void setObjectId(Integer objectId) {
            this.objectId = objectId;
        }

        public void setWinner(String winner) {
            this.winner = winner;
        }

        public void setLegacyDevelopments(String legacyDevelopments) {
            this.legacyDevelopments = legacyDevelopments;
        }

        public void setGameNotes(String gameNotes) {
            this.gameNotes = gameNotes;
        }

        public void setGameRating(Integer gameRating) {
            this.gameRating = gameRating;
        }

    public void setGame(Game game) {
        this.game = game;
    }


        @Override
        public String toString() {
            return "Play{" + "Play ID=" + playId + ", User name=" + (user != null ? user.getUsername() : "User#" + userId) +
                    ", Game Title =" + (game != null ? game.getName() : "Game#" + objectId) + ", Winner =" + winner +
                    ", Legacy Developments =" + legacyDevelopments + ", Game Notes =" + gameNotes + ", Game Rating =" + gameRating + '}';
        }
    }

