package database;

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


    public static TreeSet<String> uniqueBrandsFromDb(TreeSet<String> uniqueBrands) {

//        TreeSet<String> uniqueBrands = new TreeSet<>();

        try {
            Statement stmt = makeConnection();
            String query = "SELECT * FROM product;";
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                uniqueBrands.add(rs.getString("brand"));
            }

//            for (String s : uniqueBrands) {
//                System.out.println(s);
//            }
            return uniqueBrands;

        } catch (SQLException e) {
            e.getStackTrace();
        }
        return null;
    }

    public static ArrayList<Product> selectedProductsByBrandFromDb(String selectedBrand, ArrayList<Product> selectedProducts) {
//        ArrayList<Product> selectedProducts = new ArrayList<>();
        try {
            Statement stmt = makeConnection();
            String query = "SELECT * FROM product WHERE brand = '" + selectedBrand + "';";
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                selectedProducts.add(costructProd(rs));
            }
            return selectedProducts;
        } catch (SQLException e) {
            e.getStackTrace();
        }
        return selectedProducts;
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


}
