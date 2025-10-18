package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Game;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcGameDao implements GameDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcGameDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Game> getAllGames() {
        List<Game> games = new ArrayList<>();
        String sql = "SELECT * FROM games WHERE retired = FALSE";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                games.add(mapRowToGame(results));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return games;
    }

    @Override
    public Game getGameById(int objectId) {
        Game game = null;
        String sql = "SELECT * FROM games WHERE objectid = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, objectId);
            if (results.next()) {
                game = mapRowToGame(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to the database.", e);
        }
        return game;
    }

    @Override
    public List<Game> getGamesByUserId(int userId) {
        List<Game> games = new ArrayList<>();
        String sql = "SELECT * FROM games WHERE user_id = ? AND retired = FALSE";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
            while (results.next()) {
                games.add(mapRowToGame(results));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to the database.", e);
        }
        return games;
    }

    @Override
    public Game createGame(Game game) {
        String sql = "INSERT INTO games (name, play_time, min_num_players, max_num_players, age, publisher, user_id, avg_weight, retired) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                     "RETURNING objectid";
        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql,
                    game.getName(),
                    game.getPlaytime(),
                    game.getMinimumPlayers(),
                    game.getMaximumPlayers(),
                    game.getRecommendedAge(),
                    game.getPublisher(),
                    game.getUserId(),
                    game.getAvgWeight(),
                    game.isRetired());
            if (result.next()) {
                int newId = result.getInt("objectid");
                game.setObjectId(newId);
                return game;
            } else {
                throw new DaoException("Failed to return new Game ID");
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to the database.", e);
        }
    }

    @Override
    public Game updateGame(int objectId, Game game) {
        String sql = "UPDATE games " +
                     "SET name = ?, play_time = ?, min_num_players = ?, max_num_players = ?, age = ?, publisher = ?, avg_weight = ?, retired = ? " +
                     "WHERE objectid = ?";
        try {
            int rows = jdbcTemplate.update(sql,
                    game.getName(),
                    game.getPlaytime(),
                    game.getMinimumPlayers(),
                    game.getMaximumPlayers(),
                    game.getRecommendedAge(),
                    game.getPublisher(),
                    game.getAvgWeight(),
                    game.isRetired(),
                    objectId);
            return rows > 0 ? getGameById(objectId) : null;
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to the database.", e);
        }
    }

    @Override
    public boolean deleteGame(int objectId) {
        String sql = "DELETE FROM games WHERE objectid = ?";
        try {
            return jdbcTemplate.update(sql, objectId) > 0;
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to the database.", e);
        }
    }

    @Override
    public List<Game> searchGames(String title, Integer numPlayers, Integer maxComplexity) {
        List<Object> params = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM games WHERE 1=1");

        if (title != null && !title.isBlank()) {
            sql.append(" AND LOWER(name) LIKE ?");
            params.add("%" + title.toLowerCase() + "%");
        }

        if (numPlayers != null) {
            sql.append(" AND min_num_players <= ? AND max_num_players >= ?");
            params.add(numPlayers);
            params.add(numPlayers);
        }

        if (maxComplexity != null) {
            sql.append(" AND avg_weight <= ?");
            params.add(maxComplexity);
        }

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql.toString(), params.toArray());

        List<Game> games = new ArrayList<>();
        while (results.next()) {
            games.add(mapRowToGame(results));
        }

        return games;
    }


    @Override
    public boolean retireGame(int objectId) {
        String sql = "UPDATE games SET retired = TRUE WHERE objectid = ?";
        try {
            return jdbcTemplate.update(sql, objectId) > 0;
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to the database.", e);
        }
    }

    private Game mapRowToGame(SqlRowSet rowSet) {
        Game game = new Game();
        game.setObjectId(rowSet.getInt("objectid"));
        game.setName(rowSet.getString("name"));
        game.setPlaytime(rowSet.getInt("play_time"));
        game.setMinimumPlayers(rowSet.getInt("min_num_players"));
        game.setMaximumPlayers(rowSet.getInt("max_num_players"));
        game.setRecommendedAge(rowSet.getInt("age"));
        game.setPublisher(rowSet.getString("publisher"));
        game.setUserId(rowSet.getInt("user_id"));
        game.setAvgWeight(rowSet.getInt("avg_weight"));
        game.setRetired(rowSet.getBoolean("retired"));
        return game;
    }
}