package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.PlayerModel;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.SQLException;

public class GameDatabaseManager {

    private PlayerDao playerDao;
    private GameStateDao gameStateDao;

    public void setup() throws SQLException {
        DataSource dataSource = connect();
        playerDao = new PlayerDaoJdbc(dataSource);
        gameStateDao = new GameStateDaoJdbc(dataSource);
    }

    public void savePlayer(Player player) {
        PlayerModel model = new PlayerModel(player);
        playerDao.add(model);
    }

    public void saveGameState(String name, int currentMap, Date savedAt, PlayerModel player) {
        // fixit
        GameState game_state = new GameState(name, currentMap, savedAt, player);
        gameStateDao.add(game_state);
    }

    private PGSimpleDataSource connect() throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        String dbName = System.getenv("PSQL_DB_NAME");
        System.out.println(dbName);
        String user = System.getenv("PSQL_USER_NAME");
        System.out.println(user);
        String password = System.getenv("PSQL_PASS");
        System.out.println(password);

        dataSource.setDatabaseName(dbName);
        dataSource.setUser(user);
        dataSource.setPassword(password);

        System.out.println("Trying to connect");
        dataSource.getConnection().close();
        System.out.println("Connection ok.");
        return dataSource;
    }

    public PlayerDao getPlayerDao() {
        return playerDao;
    }

    public GameStateDao getGameStateDao() {
        return gameStateDao;
    }
}
