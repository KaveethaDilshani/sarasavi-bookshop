package lk.ijse.Sarasavi.model;

import lk.ijse.Sarasavi.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignUpModel {
    public static boolean saveUser(lk.ijse.Sarasavi.dto.SignUpDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO user VALUES(?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getUsername());
        pstm.setString(2, dto.getPassword());

        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;
    }
}
