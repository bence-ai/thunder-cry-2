package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.PlayerModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class PlayerDaoJdbc implements PlayerDao {
    private DataSource dataSource;

    public PlayerDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(PlayerModel player) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO player (name, avatar, mp, hp, weapon, defense, pos_x, pos_y ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, player.getPlayerName());
            statement.setString(2, player.getAvatar());
            statement.setInt(3,player.getMp());
            statement.setInt(4,player.getHp());
            statement.setString(5, player.getWeapon());
            statement.setInt(6, player.getDefense());
            statement.setInt(7, player.getX());
            statement.setInt(8, player.getY());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            player.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e + " add fail");
        }
    }

    @Override
    public void update(PlayerModel player) {
        try (Connection connection = dataSource.getConnection()){
            String query = "UPDATE player SET name = ?, avatar = ?, mp = ?, hp = ?, weapon = ?, defense = ?, pos_x = ?, pos_y = ?, WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, player.getPlayerName());
            preparedStatement.setString(2, player.getAvatar());
            preparedStatement.setInt(3, player.getMp());
            preparedStatement.setInt(4, player.getHp());
            preparedStatement.setString(5, player.getWeapon());
            preparedStatement.setInt(6, player.getDefense());
            preparedStatement.setInt(7, player.getX());
            preparedStatement.setInt(8, player.getY());
            preparedStatement.setInt(9, player.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            throw new RuntimeException(e + " update fail");
        }

    }

    @Override
    public PlayerModel get(int id) {
        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT name, avatar, mp, hp, weapon, defense, pos_x, pos_y FROM player WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return null;
            }
            PlayerModel playerModel = new PlayerModel(resultSet.getString(1), resultSet.getString(2),
                                                        resultSet.getInt(3), resultSet.getInt(4),
                                                        resultSet.getString(5), resultSet.getInt(6),
                                                        resultSet.getInt(7), resultSet.getInt(8));
            playerModel.setId(id);
            return playerModel;

        } catch (SQLException e) {
            throw new RuntimeException(e + " get fail");
        }

    }

    @Override
    public List<PlayerModel> getAll() {
        return null;
    }
}
