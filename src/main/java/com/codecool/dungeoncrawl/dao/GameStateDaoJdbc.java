package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.GameState;

import java.util.List;

public class GameStateDaoJdbc implements GameStateDao {
    private DataSource dataSource;

    public GameStateDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(GameState state) {
        try (Connection connection = dataSource.getConnection()) {
            String query = "INSERT INTO game_state (name, current_map, saved_at, player_id) VALUES (?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, state.getState_name());
            preparedStatement.setInt(2, state.getCurrentMap());
            preparedStatement.setDate(3, state.getSavedAt());
            preparedStatement.setInt(4, state.getPlayer().getId());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            state.setId(resultSet.getInt(1));
            PlayerDaoJdbc jdbcPlayer = new PlayerDaoJdbc(dataSource);
            jdbcPlayer.add(state.getPlayer());
        } catch (SQLException e) {
            throw new RuntimeException(e + " add fail");
        }
    }

    @Override
    public void update(GameState state) {
        try (Connection connection = dataSource.getConnection()){
            String query = "UPDATE game_state SET name = ?, current_map = ?, saved_at = ?, player_id = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, state.getState_name());
            preparedStatement.setInt(2, state.getCurrentMap());
            preparedStatement.setDate(3, state.getSavedAt());
            preparedStatement.setInt(4, state.getPlayer().getId());
            preparedStatement.setInt(5, state.getId());
            preparedStatement.executeUpdate();
            PlayerDaoJdbc jdbcPlayer = new PlayerDaoJdbc(dataSource);
            jdbcPlayer.update(state.getPlayer());
        } catch (SQLException e) {
            throw new RuntimeException(e + " update fail");
        }
    }

    @Override
    public GameState get(int id) {
        try (Connection connection = dataSource.getConnection()){
            String query = "SELECT name, current_map, saved_at, player_id FROM game_state WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return null;
            }
            PlayerDaoJdbc playerDaoJdbc = new PlayerDaoJdbc(dataSource);
            PlayerModel playerModel = playerDaoJdbc.get(resultSet.getInt(4));
            GameState gameState = new GameState(resultSet.getString(1), resultSet.getInt(2), resultSet.getDate(3), playerModel);
            gameState.setId(id);
            return gameState;
        } catch (SQLException e) {
            throw new RuntimeException(e + " get fail");
        }
    }

    @Override
    public List<GameState> getAll() {
        try (Connection connection = dataSource.getConnection()){
            String query = "SELECT id, name, current_map, saved_at, player_id FROM game_state";
            ResultSet resultSet = connection.createStatement().executeQuery(query);
            List<GameState> gameStateList = new ArrayList<>();
            while (resultSet.next()) {
                PlayerDaoJdbc playerDaoJdbc = new PlayerDaoJdbc(dataSource);
                PlayerModel playerModel = playerDaoJdbc.get(resultSet.getInt(5));
                GameState gameState = new GameState(resultSet.getString(2), resultSet.getInt(3), resultSet.getDate(4), playerModel);
                gameState.setId(resultSet.getInt(1));
                gameStateList.add(gameState);
            }
            return gameStateList;
        } catch (SQLException e) {
            throw new RuntimeException(e + " get all fail");
        }
    }
}
