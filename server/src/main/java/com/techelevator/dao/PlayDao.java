package com.techelevator.dao;
import com.techelevator.model.Play;

import java.util.List;
    public interface PlayDao {
        List<Play> getAllPlays();
        Play getPlayById(int id);
        Play createPlay(Play play);
        Play updatePlay(int id, Play play);
        boolean deletePlay(int id);
        List<Play> getPlaysByUserId(int userId);
    }

