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


public class EmployeeModel {
    private static int contactNumber;

    public static boolean saveEmployee(Employee employee) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO Employee VALUES(?, ?, ?, ?)";
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, employee.getEId());
            pstm.setString(2, employee.getEName());
            pstm.setString(3, employee.getEAddress());
            pstm.setInt(4, employee.getContactNumber());

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
            pstm.setString(1, employee.getEName());
            pstm.setString(2, employee.getEAddress());
            pstm.setInt(3, employee.getContactNumber());
            pstm.setString(4, employee.getEId());

            return pstm.executeUpdate() > 0;

        }
    }
    public static Employee searchEmployee(int C_number) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "select * from Employee where Contact_number=?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setInt(1, C_number);

        ResultSet resultSet = pstm.executeQuery();

        Employee employee = null;

        if(resultSet.next()) {
            String E_id = resultSet.getString(1);
            String E_name = resultSet.getString(2);
            String  E_address= resultSet.getString(3);
            int Contact_number= resultSet.getInt(4);

            employee= new Employee(E_id, E_name, E_address, Contact_number);
        }
        System.out.println(employee);
        return employee;
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
                String eId = rs.getString("E_name");
                String eName = rs.getString("E_id");
                String eAddress = rs.getString("E_address");
                int contactNumber = rs.getInt("Contact_number");

                Employee employee = new Employee(eName, eId, eAddress, contactNumber);
                employeeList.add(employee);
            }
            return employeeList;
        }
    }
}
