package lk.ijse.Sarasavi.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lk.ijse.Sarasavi.db.DbConnection;
import lk.ijse.Sarasavi.dto.Item;
import lk.ijse.Sarasavi.dto.OrderDetailDto;

public class ItemModel {
    public static boolean saveItem(Item item) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO Items (I_name, I_id, price, category, qty) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, item.getName());
            pstm.setInt(2, item.getId());
            pstm.setDouble(3, item.getPrice());
            pstm.setString(4, item.getCategory());
            pstm.setInt(5, item.getQty());

            return pstm.executeUpdate() > 0;
        }
    }

    public static boolean deleteItem(int itemId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "DELETE FROM Items WHERE I_id = ?";
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, itemId);
            return pstm.executeUpdate() > 0;
        }
    }

    public static boolean updateItem(Item item) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "UPDATE Items SET I_name = ?, price = ?, category = ?, qty = ? WHERE I_id = ?";
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, item.getName());
            pstm.setDouble(2, item.getPrice());
            pstm.setString(3, item.getCategory());
            pstm.setInt(4, item.getQty());
            pstm.setInt(5, item.getId());

            return pstm.executeUpdate() > 0;
        }
    }

    public static Item searchItem(int itemId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM Items WHERE I_id = ?";
        try (PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, itemId);

            try (ResultSet resultSet = pstm.executeQuery()) {
                Item item = null;
                if (resultSet.next()) {
                    String itemName = resultSet.getString("I_name");
                    double price = resultSet.getDouble("price");
                    String category = resultSet.getString("category");
                    int quantity = resultSet.getInt("qty");

                    item = new Item(itemName, itemId, price, category, quantity);
                }
                return item;
            }
        }
    }

    public static List<Item> getAllItems() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM Items";
        try (PreparedStatement pstm = connection.prepareStatement(sql);
             ResultSet rs = pstm.executeQuery()) {

            List<Item> itemList = new ArrayList<>();
            while (rs.next()) {
                String itemName = rs.getString("I_name");
                int itemId = rs.getInt("I_id");
                double price = rs.getDouble("price");
                String category = rs.getString("category");
                int quantity = rs.getInt("qty");

                Item item = new Item(itemName, itemId, price, category, quantity);
                itemList.add(item);
            }
            return itemList;
        }
    }

    public static boolean updateQty(List<OrderDetailDto> list) throws SQLException {
        for (OrderDetailDto orderDetailDto : list) {
            if (!updateQty(orderDetailDto))return false;
        }
        return true;
    }

    public static boolean updateQty(OrderDetailDto orderDetailDto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "UPDATE Items SET qty = qty - ? WHERE I_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setInt(1, orderDetailDto.getQuantity());
        pstm.setInt(2, orderDetailDto.getItemId());
        return pstm.executeUpdate() > 0;
    }

}
