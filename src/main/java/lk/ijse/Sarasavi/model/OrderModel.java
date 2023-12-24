package lk.ijse.Sarasavi.model;

import lk.ijse.Sarasavi.db.DbConnection;
import lk.ijse.Sarasavi.dto.OrderDto;
import lk.ijse.Sarasavi.dto.tm.IncomeTM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class OrderModel {
    public static int generateNextOrderId() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT MAX(O_id) AS maxOrderId FROM Orders";
        try (PreparedStatement pstm = connection.prepareStatement(sql);
             ResultSet rs = pstm.executeQuery()) {

            int maxOrderId = 0;
            if (rs.next()) {
                maxOrderId = rs.getInt("maxOrderId");
            }
            return maxOrderId + 1;
        }
    }

    public static boolean placeOrder(OrderDto order) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);
        try {
            boolean save = saveOrder(order);
            if (save) {
                boolean save1 = OrderItemModel.saveOrderItem(order.getOrderDetailDtos());
                if (save1) {
                    boolean save2 = ItemModel.updateQty(order.getOrderDetailDtos());
                    if (save2) {
                        connection.commit();
                        return true;
                    }
                }
            }
            connection.rollback();
        }catch (Exception e){
            connection.rollback();
            e.printStackTrace();
        }finally {
            connection.setAutoCommit(true);
        }
        return false;
    }

    public static boolean saveOrder(OrderDto orderDto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        System.out.println("Order : "+orderDto.getOrderId());
        String sql = "INSERT INTO Orders VALUES(?, ?, ?, ?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setInt(1, orderDto.getOrderId());
        pstm.setString(2, orderDto.getCustomerId());
        pstm.setDate(3, orderDto.getOrderDate());
        pstm.setString(4, orderDto.getOrderType());
        pstm.setDouble(5, orderDto.getTotal());
        return pstm.executeUpdate() > 0;

    }

    public static List<IncomeTM> getIncomeAnnualy() throws SQLException {
        List<IncomeTM> list = new ArrayList<>();
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT YEAR(O_Date),SUM(total) FROM Orders group by year(O_Date)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();
        while (rs.next()) {
            IncomeTM incomeTM = new IncomeTM();
            incomeTM.setIdentifier(rs.getString(1));
            incomeTM.setAmount(rs.getDouble(2));
            list.add(incomeTM);
        }
        return list;
    }

    public static List<IncomeTM> getIncomeByMonthly(int year) throws SQLException {
        List<IncomeTM> list = new ArrayList<>();
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT MONTH(O_Date),SUM(total) FROM Orders WHERE YEAR(O_Date) = ? group by month(O_Date)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setInt(1, year);
        ResultSet rs = pstm.executeQuery();
        while (rs.next()) {
            IncomeTM incomeTM = new IncomeTM();
            incomeTM.setIdentifier(Month.of(rs.getInt(1)).name());
            incomeTM.setAmount(rs.getDouble(2));
            list.add(incomeTM);
        }
        return list;
    }

}
