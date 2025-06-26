package net.zine.supmtiproject.DAO;
import net.zine.supmtiproject.Model.Appointment;

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
    private ResultSet resultSet, resultSet1;
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

    public List<Appointment> getRendezVousOfMedcine(int idMedcine) throws SQLException {
        List<Appointment> list = new ArrayList<>();

        String sql = """
        SELECT r.id_redezvous, r.date_rendezvous, r.horaire, r.maladie, r.CompteRendu, u.full_name
        FROM task_manager_db.redezvous r
        JOIN task_manager_db.client c ON r.id_client = c.id_client
        JOIN task_manager_db.users u ON c.id_personne = u.id
        WHERE r.id_medcine = ?;
    """;

        preparedStatement = con.prepareStatement(sql);
        preparedStatement.setInt(1, idMedcine);
        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            int id = resultSet.getInt("id_redezvous");
            LocalDate date = resultSet.getDate("date_rendezvous").toLocalDate();
            String hour = resultSet.getString("horaire");
            String reason = resultSet.getString("maladie");
            String report = resultSet.getString("CompteRendu");
            String fullName = resultSet.getString("full_name");

            Appointment appointment = new Appointment(id, fullName, date, hour, reason, report);
            list.add(appointment);
        }

        return list;
    }


    public void saveCompteRendu(int idrendezVous, String text) throws SQLException {
        String sql = "UPDATE `task_manager_db`.`redezvous` SET `CompteRendu` = ? WHERE (`id_redezvous` = ?);";
        preparedStatement = con.prepareStatement(sql);
        preparedStatement.setString(1, text);
        preparedStatement.setInt(2, idrendezVous);
        preparedStatement.executeUpdate();
    }

    public List<Appointment> getRendezVousOfMedcineById(int medcineId, LocalDate newVal) throws SQLException {
        List<Appointment> list = new ArrayList<>();

        String sql = """
        SELECT r.id_redezvous, r.date_rendezvous, r.horaire, r.maladie, r.CompteRendu, u.full_name
        FROM task_manager_db.redezvous r
        JOIN task_manager_db.client c ON r.id_client = c.id_client
        JOIN task_manager_db.users u ON c.id_personne = u.id
        WHERE r.id_medcine = ? AND r.date_rendezvous = ?;
    """;

        preparedStatement = con.prepareStatement(sql);
        preparedStatement.setInt(1, medcineId);
        preparedStatement.setDate(2, Date.valueOf(newVal));
        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            int id = resultSet.getInt("id_redezvous");
            LocalDate date = resultSet.getDate("date_rendezvous").toLocalDate();
            String hour = resultSet.getString("horaire");
            String reason = resultSet.getString("maladie");
            String report = resultSet.getString("CompteRendu");
            String fullName = resultSet.getString("full_name");

            Appointment appointment = new Appointment(id, fullName, date, hour, reason, report);
            list.add(appointment);
        }

        return list;
    }
}
