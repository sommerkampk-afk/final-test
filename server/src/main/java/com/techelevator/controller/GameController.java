package com.techelevator.controller;

import com.techelevator.dao.GameDao;
import com.techelevator.model.Game;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/games")
@PreAuthorize("isAuthenticated()")
public class GameController {

    private final GameDao gameDao;

    public GameController(GameDao gameDao) {
        this.gameDao = gameDao;
    }


    @GetMapping("/search")
    public List<Game> searchGames(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Integer numPlayers,
            @RequestParam(required = false) Integer maxComplexity) {

        List<Game> games = gameDao.searchGames(title, numPlayers, maxComplexity);

        if (games == null || games.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No games found matching the criteria.");
        }

        return games;
    }

    @GetMapping
    public List<Game> listGames() {
        return gameDao.getAllGames();
    }


    @GetMapping("/{id}")
    public Game getGame(@PathVariable int id) {
        Game game = gameDao.getGameById(id);
        if (game == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game with id " + id + " not found");
        }
        return game;
    }


    @GetMapping("/user/{userId}")
    public List<Game> getGamesByUserId(@PathVariable int userId) {
        List<Game> game = gameDao.getGamesByUserId(userId);
        if (game == null || game.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id " + userId + " has no games");
        }
        return game;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Game createGame(@Valid @RequestBody Game game) {
        return gameDao.createGame(game);
    }


    @PutMapping("/{id}")
    public Game updateGame(@PathVariable int id, @Valid @RequestBody Game game) {
        Game updated = gameDao.updateGame(id, game);
        if (updated == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game with id " + id + " not found");
        }
        return updated;
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteGame(@PathVariable int id) {
        boolean deleted = gameDao.deleteGame(id);
        if (!deleted) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game with id " + id + " not found");
        }
    }


    @PutMapping("/{id}/retire")
    public void retireGame(@PathVariable int id) {
        boolean retired = gameDao.retireGame(id);
        if (!retired) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game with id " + id + " not found");
        }
    }
}

