package net.zine.supmtiproject.DAO;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class rendezvousDao {
    private DBConnection dbConnection = new DBConnection();
    private Connection con;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    public rendezvousDao() {
        try {
            con = dbConnection.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> invalidHoure(LocalDate selectedDate) throws SQLException {
        java.sql.Date sqlDate = java.sql.Date.valueOf(selectedDate);
        String sql = "SELECT `horaire` FROM task_manager_db.redezvous where `date_rendezvous` = ?;";
        preparedStatement = con.prepareStatement(sql);
        preparedStatement.setDate(1, sqlDate);
        resultSet = preparedStatement.executeQuery();
        List<String> H = new ArrayList<>();
        while (resultSet.next()) {
            String hour = resultSet.getString("horaire");
            H.add(hour);
        }
        return H;
    }

    public void insertrendezvous(int idclient, LocalDate selectedDate, String hour, String maladie) throws SQLException {
        UUID id = UUID.randomUUID();
        java.sql.Date sqlDate = java.sql.Date.valueOf(selectedDate);

        String sql = "INSERT INTO `task_manager_db`.`redezvous` " +
                "(`id_redezvous`, `horaire`, `maladie`, `id_client`, `id_medcine`, `date_rendezvous`) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        preparedStatement = con.prepareStatement(sql);
        preparedStatement.setInt(1, id.hashCode());
        preparedStatement.setString(2, hour);
        preparedStatement.setString(3, maladie);
        preparedStatement.setInt(4, idclient);
        preparedStatement.setInt(5, 2);
        preparedStatement.setDate(6, sqlDate);
        preparedStatement.executeUpdate();
    }

}
