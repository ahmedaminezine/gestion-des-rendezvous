package net.zine.supmtiproject.DAO;

import com.mysql.cj.xdevapi.JsonString;

import java.sql.*;
import java.time.LocalDate;
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
            con = dbConnection.getConnection();
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

    public int getClientId(int idUser) throws SQLException {
        String sql = "SELECT id_client FROM task_manager_db.client WHERE id_personne = ?";

        try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            preparedStatement.setInt(1, idUser);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id_client");
                } else {
                    System.out.println(" No client found for id_personne = " + idUser);
                    return 0;
                }
            }
        }
    }

    public ResultSet getClientInfo(int idUser) throws SQLException {
        String sql ="SELECT u.*, c.* FROM task_manager_db.users u JOIN task_manager_db.client c ON c.id_personne = u.id WHERE u.id = ?;";
       PreparedStatement preparedStatement = con.prepareStatement(sql);
       preparedStatement.setInt(1, idUser);
       ResultSet resultSet = preparedStatement.executeQuery();
       return resultSet;

    }


    public String getmedcineNameById(int idmedcine) throws SQLException {
        String sql = "SELECT u.full_name FROM task_manager_db.redezvous r JOIN task_manager_db.medcine m ON m.id_medcine = r.id_medcine JOIN task_manager_db.users u ON u.id = m.id_pers WHERE r.id_medcine = ? limit 1;";
        try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            preparedStatement.setInt(1, idmedcine);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("full_name");
                } else {

                    return null;
                }
            }
        }
    }

    public void UpdateUser(int id, String UserName, String UserPassword, String FullName, String city, int age) throws SQLException {
        String sql ="UPDATE `task_manager_db`.`users` SET `username` = ?, `password` = ?, `full_name` = ? WHERE (`id` = ?);";
        preparedStatement = con.prepareStatement(sql);
        preparedStatement.setString(1, UserName);
        preparedStatement.setString(2, UserPassword);
        preparedStatement.setString(3, FullName);
        preparedStatement.setInt(4, id);
        preparedStatement.executeUpdate();
        sql = "UPDATE `task_manager_db`.`client` SET `age_client` = ?, `ville_client` = ? WHERE (`id_personne` = ?);";
        preparedStatement = con.prepareStatement(sql);
        preparedStatement.setInt(1, age);
        preparedStatement.setString(2, city);
        preparedStatement.setInt(3, id);
        preparedStatement.executeUpdate();

    }
}
