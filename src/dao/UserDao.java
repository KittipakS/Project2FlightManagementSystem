/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

/**
 *
 * @author kitti
 */
public class UserDao {
    private final String SELECT_USER_BASED_ON_ID_QUERY = "select id, firstName, lastName, phoneNumber, address, username, password, type from user_details where id = ?";
    private final String SELECT_USER_BASED_ON_USERNAME_AND_PASSWORD = "select id, firstName, lastName, phoneNumber, address, username, password, type from user_details where username = ? and password = ?";
    private final String DELETE_USER_QUERY = "delete from user_details where id = ?";
    private final String INSERT_USER_QUERY = "insert into user_details (id, firstName, lastName, phoneNumber, address, username, password, type) values (?,?,?,?,?,?,?,?)";

    public void insertUser(UserModel user) throws SQLException {
        try (PreparedStatement statement = DatabaseConfiguration.connect().prepareStatement(INSERT_USER_QUERY)) {
            int index = 0;
            statement.setLong(++index, user.getId());
            statement.setString(++index, user.getFirstName());
            statement.setString(++index, user.getLastName());
            statement.setInt(++index, user.getPhoneNumber());
            statement.setString(++index, user.getAddress());
            statement.setString(++index, user.getUsername());
            statement.setString(++index, Base64.getEncoder().encodeToString(user.getPassword().getBytes()));
            statement.setString(++index, user.getType());
            statement.executeUpdate();
        }
    }

    public boolean deleteUser(long userId) throws SQLException {
        try (PreparedStatement statement = DatabaseConfiguration.connect().prepareStatement(DELETE_USER_QUERY)) {
            statement.setLong(1, userId);
            return statement.executeUpdate() > 0;
        }
    }

    public UserModel getUserById(long userId) throws SQLException {
        try (PreparedStatement statement = DatabaseConfiguration.connect().prepareStatement(SELECT_USER_BASED_ON_ID_QUERY)) {
            statement.setLong(1, userId);
            System.out.println("Executing Query: " + statement);

            ResultSet results = statement.executeQuery();
            if (results.next()) {
                return getUser(results);
            }
            return null;
        }
    }

    public UserModel getUserByUserNameAndPassword(String username, String password) throws SQLException {
        try (PreparedStatement statement = DatabaseConfiguration.connect().prepareStatement(SELECT_USER_BASED_ON_USERNAME_AND_PASSWORD)) {
            statement.setString(1, username);
            statement.setString(2, Base64.getEncoder().encodeToString(password.getBytes()));
            System.out.println("Executing Query: " + statement);

            ResultSet results = statement.executeQuery();
            if (results.next()) {
                return getUser(results);
            }
            return null;
        }
    }

    private UserModel getUser(ResultSet results) throws SQLException {
        UserModel user = new UserModel();
        user.setId(results.getLong("id"));
        user.setFirstName(results.getString("firstName"));
        user.setLastName(results.getString("lastName"));
        user.setPhoneNumber(results.getInt("phoneNumber"));
        user.setAddress(results.getString("address"));
        user.setUsername(results.getString("username"));
        user.setPassword(new String(Base64.getDecoder().decode(results.getString("password"))));
        user.setType(results.getString("type"));
        return user;
    }
}
