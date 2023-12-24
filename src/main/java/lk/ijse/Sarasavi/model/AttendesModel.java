package lk.ijse.Sarasavi.model;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lk.ijse.Sarasavi.db.DbConnection;
import lk.ijse.Sarasavi.dto.AttendesDto;

public class AttendesModel {

    public static boolean saveAttendes(AttendesDto attendes) {
        Connection connection = null;
        PreparedStatement pstm = null;
        try {
            connection = DbConnection.getInstance().getConnection();
            String sql = "INSERT INTO Attendes (Date, working_hours, Ot_hours, employeeid) VALUES (?, ?, ?, ?)";
            pstm = connection.prepareStatement(sql);
            pstm.setDate(1, attendes.getDate());
            pstm.setString(2, attendes.getWorkingHours());
            pstm.setString(3, attendes.getOtHours());
            pstm.setString(4, attendes.getEmployeeId());
            return pstm.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            closeResources(connection, pstm);
        }
    }

    public List<AttendesDto> getAllAttendes() {
        Connection connection = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<AttendesDto> attendesList = new ArrayList<>();
        try {
            connection = DbConnection.getInstance().getConnection();
            String sql = "SELECT * FROM Attendes";
            pstm = connection.prepareStatement(sql);
            rs = pstm.executeQuery();
            while (rs.next()) {
                AttendesDto attendes = new AttendesDto();
                attendes.setId(rs.getInt("Id"));
                attendes.setDate(rs.getDate("Date"));
                attendes.setWorkingHours(rs.getString("working_hours"));
                attendes.setOtHours(rs.getString("Ot_hours"));
                attendes.setEmployeeId(rs.getString("employeeid"));
                attendesList.add(attendes);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            closeResources(connection, pstm, rs);
        }
        return attendesList;
    }

    public boolean updateAttendes(AttendesDto attendes) {
        Connection connection = null;
        PreparedStatement pstm = null;
        try {
            connection = DbConnection.getInstance().getConnection();
            String sql = "UPDATE Attendes SET Date=?, working_hours=?, Ot_hours=?, employeeid=? WHERE Id=?";
            pstm = connection.prepareStatement(sql);
            pstm.setDate(1, attendes.getDate());
            pstm.setString(2, attendes.getWorkingHours());
            pstm.setString(3, attendes.getOtHours());
            pstm.setString(4, attendes.getEmployeeId());
            pstm.setInt(5, attendes.getId());
            return pstm.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            closeResources(connection, pstm);
        }
    }

    public boolean deleteAttendes(int id) {
        Connection connection = null;
        PreparedStatement pstm = null;
        try {
            connection = DbConnection.getInstance().getConnection();
            String sql = "DELETE FROM Attendes WHERE Id=?";
            pstm = connection.prepareStatement(sql);
            pstm.setInt(1, id);
            return pstm.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            closeResources(connection, pstm);
        }
    }

    public static boolean isAttendanceAddedForToday(String employeeId) {
        Connection connection = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        boolean attendanceAdded = false;

        try {
            connection = DbConnection.getInstance().getConnection();
            String sql = "SELECT * FROM Attendes WHERE employeeid = ? AND Date = ?";
            pstm = connection.prepareStatement(sql);
            pstm.setString(1, employeeId);
            pstm.setDate(2, Date.valueOf(LocalDate.now()));
            rs = pstm.executeQuery();
            attendanceAdded = rs.next(); // If there's a record, attendance is added for today
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            closeResources(connection, pstm, rs);
        }
        return attendanceAdded;
    }

    public static List<AttendesDto> getAllAttendesForToday() {
        Connection connection = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<AttendesDto> attendesList = new ArrayList<>();

        try {
            connection = DbConnection.getInstance().getConnection();
            String sql = "SELECT * FROM Attendes a WHERE a.Date = ?";
            pstm = connection.prepareStatement(sql);
            pstm.setDate(1, Date.valueOf(LocalDate.now()));
            rs = pstm.executeQuery();
            while (rs.next()) {
                AttendesDto attendes = new AttendesDto();
                attendes.setId(rs.getInt("Id"));
                attendes.setDate(rs.getDate("Date"));
                attendes.setWorkingHours(rs.getString("working_hours"));
                attendes.setOtHours(rs.getString("Ot_hours"));
                attendes.setEmployeeId(rs.getString("employeeid"));
                attendesList.add(attendes);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            closeResources(connection, pstm, rs);
        }
        return attendesList;
    }

    private static void closeResources(Connection connection, Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static void closeResources(Connection connection, Statement statement, ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        closeResources(connection, statement);
    }
}
