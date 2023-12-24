package lk.ijse.Sarasavi.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lk.ijse.Sarasavi.db.DbConnection;
import lk.ijse.Sarasavi.dto.SupplierDto;

public class SupplierModel {
    // Method to retrieve a supplier by ID
    public static SupplierDto getSupplierById(int id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM Supplier WHERE S_id=?";
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, id);
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    return new SupplierDto(
                            rs.getInt("S_id"),
                            rs.getString("S_name"),
                            rs.getString("S_address"),
                            rs.getInt("Contact_number")
                    );
                }
            }
        }
        return null; // Return null if not found
    }

    // Method to retrieve all suppliers
    public static List<SupplierDto> getAllSuppliers() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM Supplier";
        List<SupplierDto> suppliers = new ArrayList<>();
        try (PreparedStatement pstm = connection.prepareStatement(sql);
             ResultSet rs = pstm.executeQuery()) {
            while (rs.next()) {
                suppliers.add(new SupplierDto(
                        rs.getInt("S_id"),
                        rs.getString("S_name"),
                        rs.getString("S_address"),
                        rs.getInt("Contact_number")
                ));
            }
        }
        return suppliers;
    }

    // Method to insert a new supplier
    public static boolean saveSupplier(SupplierDto supplier) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "INSERT INTO Supplier (S_id, S_name, S_address, Contact_number) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, supplier.getId());
            pstm.setString(2, supplier.getName());
            pstm.setString(3, supplier.getAddress());
            pstm.setInt(4, supplier.getContactNumber());
            return pstm.executeUpdate() > 0;
        }
    }

    // Method to update an existing supplier
    public static boolean updateSupplier(SupplierDto supplier) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "UPDATE Supplier SET S_name=?, S_address=?, Contact_number=? WHERE S_id=?";
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, supplier.getName());
            pstm.setString(2, supplier.getAddress());
            pstm.setInt(3, supplier.getContactNumber());
            pstm.setInt(4, supplier.getId());
            return pstm.executeUpdate() > 0;
        }
    }

    // Method to delete a supplier by ID
    public static boolean deleteSupplier(int id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "DELETE FROM Supplier WHERE S_id=?";
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, id);
            return pstm.executeUpdate() > 0;
        }
    }
}
