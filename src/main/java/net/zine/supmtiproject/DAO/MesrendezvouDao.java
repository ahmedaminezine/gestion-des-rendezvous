package net.zine.supmtiproject.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MesrendezvouDao {
    private DBConnection dbConnection = new DBConnection();
    private Connection con;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    public MesrendezvouDao() {
        try {
            con = dbConnection.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ResultSet getRendezVousByIdClient(int clientId) throws SQLException {
        String sql ="SELECT * FROM task_manager_db.redezvous where id_client = ?;";
        preparedStatement = con.prepareStatement(sql);
        preparedStatement.setInt(1, clientId);
        resultSet = preparedStatement.executeQuery();
        return resultSet;
    }
}
