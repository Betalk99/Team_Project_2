package database;

import product.*;

import java.sql.*;

public class DbManagement {

    public static Statement makeConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/projectteam";
        String user = "TeamDev";
        String password = "TeamDev";
        Connection conn = DriverManager.getConnection(url,user,password);
        return conn.createStatement();
    }

    public static Product costructProd(ResultSet rs) throws SQLException {
        Product t1 = new Product(ProductTypes.valueOf(rs.getString("type")), rs.getString("brand"), rs.getString("model"), rs.getString("description"), rs.getDouble("displaySize"), rs.getDouble("storageCap"), rs.getDouble("purchasePrice"), rs.getDouble("sellPrice"), rs.getInt("idProd"));

        return t1;
    }

}
