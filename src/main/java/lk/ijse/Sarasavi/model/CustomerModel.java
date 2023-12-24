package lk.ijse.Sarasavi.model;

import lk.ijse.Sarasavi.db.DbConnection;
import lk.ijse.Sarasavi.dto.CustomerDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerModel {
    public static boolean saveCustomer(CustomerDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO Customer VALUES(?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);


        pstm.setString(1, dto.getId());
        pstm.setString(2, dto.getName());
        pstm.setString(3, dto.getAddress());
        pstm.setString(4, dto.getNumber());

        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;
    }

    public static boolean deleteCustomer(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "delete from Customer where C_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, id);

        return pstm.executeUpdate() > 0;
    }

    public static boolean updateCustomer(CustomerDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "update Customer set C_name = ?, C_address = ?, Contact_number = ? where C_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getName());
        pstm.setString(2, dto.getAddress());
        pstm.setString(3, dto.getNumber());
        pstm.setString(4, dto.getId());

        return pstm.executeUpdate() > 0;
    }



    public static CustomerDto searchCustomer(String Contact_number) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "select * from Customer where Contact_number=?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, Contact_number);

        ResultSet resultSet = pstm.executeQuery();

        CustomerDto dto = null;

        if(resultSet.next()) {
            String cus_id = resultSet.getString(1);
            String cus_name = resultSet.getString(2);
            String cus_address = resultSet.getString(3);
            String cus_number = resultSet.getString(4);

            dto = new CustomerDto(cus_id, cus_name, cus_address, cus_number);
        }
        System.out.println(dto);
        return dto;
    }

    public static List<CustomerDto> getAllCustomer() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "select * from Customer";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();



        ArrayList<CustomerDto> dtoList = new ArrayList<>();
        while (rs.next()) {
            dtoList.add(
                    new CustomerDto(
                            rs.getString(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4)
                    )
            );
        }
        return dtoList;

    }
}