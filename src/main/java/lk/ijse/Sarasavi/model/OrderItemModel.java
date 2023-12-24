package lk.ijse.Sarasavi.model;

import lk.ijse.Sarasavi.db.DbConnection;
import lk.ijse.Sarasavi.dto.OrderDetailDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderItemModel {
    public static boolean saveOrderItem(List<OrderDetailDto> list) throws SQLException {
        for (OrderDetailDto orderDetailDto : list) {
            if (!saveOrderItem(orderDetailDto))return false;
        }
        return true;
    }

    public static boolean saveOrderItem(OrderDetailDto orderDetailDto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "INSERT INTO orderDetail VALUES(?, ?, ?, ?)";
        System.out.println(orderDetailDto.getOrderId());
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setInt(1, orderDetailDto.getItemId());
        pstm.setInt(2, orderDetailDto.getOrderId());
        pstm.setInt(3, orderDetailDto.getQuantity());
        pstm.setDouble(4, orderDetailDto.getUnitPrice());
        return pstm.executeUpdate() > 0;
    }
}
