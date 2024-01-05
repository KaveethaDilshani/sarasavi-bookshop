package lk.ijse.Sarasavi.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lk.ijse.Sarasavi.db.DbConnection;
import lk.ijse.Sarasavi.dto.CustomerDto;
import lk.ijse.Sarasavi.dto.Employee;
import lk.ijse.Sarasavi.dto.Item;


public class EmployeeModel {
    private static int contactNumber;

    public static boolean saveEmployee(Employee employee) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO Employee VALUES(?, ?, ?, ?)";
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, employee.getEName());
            pstm.setString(2, employee.getEId());
            pstm.setString(3, employee.getEAddress());
            pstm.setInt(4, Integer.parseInt(employee.getContactNumber()));

            return pstm.executeUpdate() > 0;
        }
    }

    public static boolean deleteEmployee(String eName) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "DELETE FROM Employee WHERE E_name = ?";
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, eName);
            return pstm.executeUpdate() > 0;
        }
    }

    public static boolean updateEmployee(Employee employee) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "UPDATE Employee SET E_id = ?, E_address = ?, Contact_number = ? WHERE E_name = ?";
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, employee.getEId());
            pstm.setString(2, employee.getEAddress());
            pstm.setInt(3, Integer.parseInt(employee.getContactNumber()));
            pstm.setString(4, employee.getEName());

            return pstm.executeUpdate() > 0;

        }
    }
    public static Employee searchEmployee(String E_id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM employee WHERE E_id = ?";

        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, E_id);

            try (ResultSet resultSet = pstm.executeQuery()) {

                if (resultSet.next()) {
                    return new Employee(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getInt(4)
                    );

                }else return null;

            }
        }
    }

   /* public static Employee searchEmployee(String eName) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM Employee WHERE E_name=?";
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, eName);

            try (ResultSet resultSet = pstm.executeQuery()) {
                Employee employee = null;
                if (resultSet.next()) {
                    String eId = resultSet.getString("E_id");
                    String eAddress = resultSet.getString("E_address");
                    int contactNumber = resultSet.getInt("Contact_number");

                    employee = new Employee( eId,eName, eAddress, contactNumber);
                }
                return employee;
            }
        }
    }*/

    public static List<Employee> getAllEmployees() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM Employee";
        try (PreparedStatement pstm = connection.prepareStatement(sql);
             ResultSet rs = pstm.executeQuery()) {

            List<Employee> employeeList = new ArrayList<>();
            while (rs.next()) {
                Employee employee = new Employee(rs.getString(1),
                rs.getString(2),
                rs.getString(3),
                rs.getInt(4)

 );
                employeeList.add(employee);
            }
            return employeeList;
        }
    }
}
