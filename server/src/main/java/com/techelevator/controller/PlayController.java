
package com.techelevator.controller;

import com.techelevator.dao.PlayDao;
import com.techelevator.model.Play;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/plays")
@PreAuthorize("isAuthenticated")
public class PlayController {

    private final PlayDao playDao;

    public PlayController(PlayDao playDao) {
        this.playDao = playDao;
    }


    @GetMapping
    public List<Play> list(@RequestParam(defaultValue = "0") int userid) {
        if (userid > 0) {
            return playDao.getPlaysByUserId(userid);
        }
        return playDao.getAllPlays();
    }


    @GetMapping("/{id}")
    public Play get(@PathVariable int id) {
        Play play = playDao.getPlayById(id);
        if (play == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Play with id " + id + " not found");
        }
        return play;
    }


    @GetMapping("/user/{userId}")
    public List<Play> getPlaysByUserId(@PathVariable int userId) {
        List<Play> plays = playDao.getPlaysByUserId(userId);
        if (plays == null || plays.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id " + userId + " has no plays");
        }
        return plays;

    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Play createPlay(@Valid @RequestBody Play play) {
        return playDao.createPlay(play);
    }


    @PutMapping("/{id}")
    public Play updatePlay(@PathVariable int id, @Valid @RequestBody Play play) {
        Play updated = playDao.updatePlay(id, play);
        if (updated == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Play with id " + id + " not found");
        }
        return updated;
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void deletePlay(@PathVariable int id) {
        boolean deleted = playDao.deletePlay(id);
        if (!deleted) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Play with id " + id + " not found");
        }
    }
}
