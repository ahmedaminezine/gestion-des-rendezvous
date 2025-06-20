package net.zine.supmtiproject.DAO;

import java.sql.*;
import java.util.List;
import java.util.UUID;

public class UserDao {
    private DBConnection dbConnection = new DBConnection();
    private Connection con;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;

    private UUID id = UUID.randomUUID();

    public UserDao() {
        try {
            con = dbConnection.getConnection(); // méthode qui renvoie une connexion
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ResultSet validateLogin(String login, String password) throws SQLException {
        String sql = "select * from users where username = ? and password = ?";
        preparedStatement = con.prepareStatement(sql);
        preparedStatement.setString(1, login);
        preparedStatement.setString(2, password);
        resultSet = preparedStatement.executeQuery();

        return resultSet;
    }

    public void insertUser(String UserName, String UserPassword, String FullName, String city, int age) throws SQLException {
        String sql1 = "INSERT INTO `task_manager_db`.`users` (`username`, `password`, `full_name`) VALUES (?, ?, ?)";

        try (PreparedStatement ps1 = con.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS)) {

            ps1.setString(1, UserName);
            ps1.setString(2, UserPassword);
            ps1.setString(3, FullName);

            int affectedRows = ps1.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = ps1.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    long userId = generatedKeys.getLong(1);

                    String sql2 = "INSERT INTO `task_manager_db`.`client` (`id_client`,`age_client`, `ville_client`, `id_personne`) VALUES (?,?, ?, ?)";
                    try (PreparedStatement ps2 = con.prepareStatement(sql2)) {
                        ps2.setLong(1, id.hashCode());
                        ps2.setInt(2, age);
                        ps2.setString(3, city);
                        ps2.setLong(4, userId);

                        ps2.executeUpdate();
                    }
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        }
    }


}
