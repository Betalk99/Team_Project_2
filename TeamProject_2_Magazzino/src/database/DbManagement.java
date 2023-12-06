package database;

import clients.Clients;
import clients.Customer;
import com.sun.source.tree.Tree;
import product.*;

import java.sql.*;
import java.util.*;

public class DbManagement {

    public static Statement makeConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/projectteam";
        String user = "TeamDev";
        String password = "TeamDev";
        Connection conn = DriverManager.getConnection(url, user, password);
        return conn.createStatement();
    }

    public static Product costructProd(ResultSet rs) throws SQLException {

        Product p = new Product(ProductTypes.valueOf(rs.getString("type")), rs.getString("brand"), rs.getString("model"), rs.getString("description"), rs.getDouble("displaySize"), rs.getDouble("storageCap"), rs.getBigDecimal("purchaseprice"), rs.getBigDecimal("sellprice"), rs.getInt("id"));
        return p;
    }

    public static ArrayList<Product> byModelDb() throws SQLException {
        Scanner in = new Scanner(System.in);
        ArrayList<Product> prodByModel = new ArrayList<>();
        try {

            Statement stmt = makeConnection();
            System.out.println("Which brand do you want to look for?");
            String model = in.nextLine();
            ResultSet rs = stmt.executeQuery("SELECT * FROM product WHERE model = '" + model + "';");

            while (rs.next()) {
                prodByModel.add(costructProd(rs));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return prodByModel;
    }

    public static ArrayList<Product> bySellPriceRangeDb() throws SQLException {
        ArrayList<Product> searchSellPrice = new ArrayList<>();
        double[] range = new double[2];
        try {
            Scanner in = new Scanner(System.in);
            Statement stmt = makeConnection();
            System.out.println("Search products by price range");
            System.out.println("From");
            range[0] = in.nextDouble();
            System.out.println("to");
            range[1] = in.nextDouble();
            Arrays.sort(range);

            String searchRange = "SELECT * FROM product WHERE product.sellprice > '" + range[0] + "' AND product.sellprice <= '" + range[1] + "';";
            ResultSet rs = stmt.executeQuery(searchRange);

            while (rs.next()) {
                searchSellPrice.add(costructProd(rs));
            }

        } catch (InputMismatchException e) {
            System.out.println("Please use an integer number (e.g. 250");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return searchSellPrice;
    }

    public static ArrayList<Product> stampStockDb() throws SQLException {
        Scanner in = new Scanner(System.in);
        ArrayList<Product> stock = new ArrayList<>();
        try {

            Statement stmt = makeConnection();
            ResultSet rs = stmt.executeQuery("SELECT * FROM product ;");

            while (rs.next()) {
                stock.add(costructProd(rs));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return stock;
    }


    public static ArrayList<Product> byTypeProductsFromDb(String type, ArrayList<Product> devicesByType) {

        try {
            Statement stmt = DbManagement.makeConnection();
            String query = "SELECT * FROM product WHERE type = '" + type + "';";

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                devicesByType.add(costructProd(rs));
            }
            for (Product p : devicesByType) {
                System.out.println(p);
            }
            System.out.println(" ");

        } catch (SQLException e) {
            e.getStackTrace();
        }
        return devicesByType;
    }

    public static TreeSet<String> catchTypesFromDb(TreeSet<String> typesFromDb) {

        try {
            Statement stmt = DbManagement.makeConnection();
            String query = "SELECT type FROM product";
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                typesFromDb.add(rs.getString("type"));
            }

            for (String s : typesFromDb) {
                System.out.println("- " + s);
            }
            System.out.println(" ");

        } catch (SQLException e) {
            e.getStackTrace();
        }
        return typesFromDb;
    }

    public static ArrayList<Product> byType() throws InputMismatchException {
        ArrayList<Product> devicesByType = new ArrayList<>();
        TreeSet<String> typesFromDb = new TreeSet<>();
        boolean isTrue = false;
        while (!isTrue) {
            try {
                Scanner in = new Scanner(System.in);
                System.out.println("Please select one of the following types:");
                DbManagement.catchTypesFromDb(typesFromDb);
                String type = in.nextLine();
                if (typesFromDb.contains(type)) {
                    DbManagement.byTypeProductsFromDb(type, devicesByType);
                    isTrue = true;
                } else {
                    System.out.println("This type does not exists.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please select an existing type (e.g. Tablet)");
            }
        }
        return devicesByType;
    }



    public static ArrayList<Product> byBrand() {
        Scanner sc = new Scanner(System.in);
        boolean stay = false;
        while (!stay) {
            boolean stay2 = false;
            while (!stay2) {
                try {
                    ArrayList<Product> selectedProducts = new ArrayList<>();
                    System.out.println("Please write the brand for which you want to search for products");
                    String selectedBrand = sc.nextLine();

                    try {
                        Statement stmt = DbManagement.makeConnection();
                        String query = "SELECT * FROM product WHERE brand = '" + selectedBrand + "';";
                        ResultSet rs = stmt.executeQuery(query);

                        while (rs.next()) {
                            selectedProducts.add(DbManagement.costructProd(rs));
                        }
                        if (selectedProducts.isEmpty()) {
                            throw new InputMismatchException("The selected brand is not available.\n");
                        } else {
                            return selectedProducts;
                        }
                    } catch (SQLException e) {
                        e.getStackTrace();
                    }
                    stay2 = true;
                } catch (InputMismatchException e) {
                    System.out.println(e.getMessage());
                }
            }
            boolean stay3 = false;
            while (!stay3) {
                try {
                    System.out.println("Would you like to search other products based on brand?\n1)Yes   2)No");
                    String answer = sc.nextLine();
                    if (answer.equals("1")) {
                        stay3 = true;
                    } else if (answer.equals("2")) {
                        stay = true;
                        stay3 = true;
                    } else {
                        throw new Exception("Please select a value between 1 or 2.\n");
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return null;
    }

    public static int idClient(Clients c){
        int idClient = 0;
        try{
            Statement stmt = DbManagement.makeConnection();
            String query = "SELECT * FROM client WHERE email = '" + c.getEmail() + "';";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()){
                idClient = rs.getInt("id");
            }
        }catch (SQLException e){
            e.getStackTrace();
        }
        return idClient;
}

    public static int idCart(int idClient){
        int idCart = -1;
        try{
            Statement stmt = DbManagement.makeConnection();
            String query = "SELECT * FROM cart " +
                    "WHERE cart.idClient = "+idClient+" " +
                    "AND cart.status = 1 ;";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()){
                idCart = rs.getInt("idCart");
            }
            if(idCart == -1){
                String query2 = "SELECT MAX(idCart) AS lastID FROM cart";
                ResultSet rs2 = stmt.executeQuery(query2);
                while (rs2.next()){
                    idCart = rs2.getInt("lastID") + 1;
                }
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return idCart;
    }




}
