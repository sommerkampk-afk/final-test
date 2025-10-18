package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Game;
import com.techelevator.model.Play;
import com.techelevator.model.User;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcPlayDao implements PlayDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcPlayDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Play> getAllPlays() {
        List<Play> plays = new ArrayList<>();
        String sql = "SELECT * FROM plays";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                Play play = new Play();
                play.setPlayId(results.getInt("playid"));
                play.setUserId(results.getInt("userid"));
                play.setObjectId(results.getInt("objectid"));
                play.setWinner(results.getString("winner"));
                play.setLegacyDevelopments(results.getString("legacy_developments"));
                play.setGameNotes(results.getString("game_notes"));
                play.setGameRating(results.getInt("game_rating"));
                plays.add(play);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return plays;
    }
    @Override
    public Play getPlayById(int id){
        Play play = null;
        String sql = "Select * from plays where playid =?";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
            if (results.next()){
                play=mapRowToPlay(results);
            }
        }catch(CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to the database.", e);
        }
        return play;
    }
    @Override
    public List<Play> getPlaysByUserId(int userId) {
        List<Play> plays = new ArrayList<>();
        String sql = "SELECT * FROM plays WHERE userid = ?";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
            while (results.next()) {
                plays.add(mapRowToPlay(results));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to the database.", e);
        }

        return plays;
    }
    public Play createPlay(Play play) {
        String sql = "INSERT INTO plays (userid, objectid, winner, legacy_developments, game_notes, game_rating) " +
                "VALUES (?, ?, ?, ?, ?, ?) RETURNING playid";

        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql,
                    play.getUserId(),
                    play.getObjectId(),
                    play.getWinner(),
                    play.getLegacyDevelopments(),
                    play.getGameNotes(),
                    play.getGameRating());

            if (result.next()) {
                int newId = result.getInt("playid");
                play.setPlayId(newId);
                return play;
            } else {
                throw new DaoException("Failed to return new Play ID");
            }

        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to the database.", e);
        }
    }

    public Play updatePlay(int id, Play play) {
        String sql = "UPDATE plays " +
                "SET userid = ?, objectid = ?, winner = ?, legacy_developments = ?, game_notes = ?, game_rating = ? " +
                "WHERE playid = ?";

        try {
            int rows = jdbcTemplate.update(sql,
                    play.getUserId(),
                    play.getObjectId(),
                    play.getWinner(),
                    play.getLegacyDevelopments(),
                    play.getGameNotes(),
                    play.getGameRating(),
                    id); // Don't forget to include this!

            return rows > 0 ? getPlayById(id) : null;

        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to the database.", e);
        }
    }
    @Override
    public boolean deletePlay(int id) {
        String sql = "DELETE FROM plays WHERE playid = ?";
        try {
            return jdbcTemplate.update(sql, id) > 0;
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to the database.", e);
        }
    }

    private Play mapRowToPlay(SqlRowSet rowset) {
        Play play = new Play();

        play.setPlayId(rowset.getInt("playid"));
        play.setUserId(rowset.getInt("userid"));
        play.setObjectId(rowset.getInt("objectid"));
        play.setWinner(rowset.getString("winner"));
        play.setLegacyDevelopments(rowset.getString("legacy_developments"));
        play.setGameNotes(rowset.getString("game_notes"));
        play.setGameRating(rowset.getInt("game_rating"));

        // Optionally hydrate User and Game stubs (if needed)
        User user = new User();
        user.setId(rowset.getInt("userid"));
        play.setUserId(user.getId());

        Game game = new Game();
        game.setObjectId(rowset.getInt("objectid"));
        play.setGame(game);

        return play;
    }
}
