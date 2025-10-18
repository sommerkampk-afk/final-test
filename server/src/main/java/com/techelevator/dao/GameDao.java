package com.techelevator.dao;

import com.techelevator.model.Game;

import java.util.List;

public interface GameDao {
    List<Game> getAllGames();
    Game getGameById(int objectId);
    Game createGame(Game game);
    Game updateGame(int objectId, Game game);
    boolean deleteGame(int objectId);
    List<Game> getGamesByUserId(int userId);
    List<Game> searchGames(String title, Integer numPlayers, Integer maxComplexity);
    boolean retireGame(int objectId);
}