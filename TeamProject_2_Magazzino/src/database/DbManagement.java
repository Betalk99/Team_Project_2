package database;

import clients.Clients;
import clients.Customer;
import com.sun.source.tree.Tree;
import order.Orders;
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

    public static Orders costructOrder(ResultSet rs) throws SQLException {
        Orders ord = new Orders(rs.getInt("idCart"), rs.getString("date"));
        return ord;
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
            System.out.println("What model are you interested in?");
            String model = in.nextLine();

            ResultSet rs = stmt.executeQuery(DbQuery.getByModel(model));

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

            ResultSet rs = stmt.executeQuery(DbQuery.getBySellPriceRange(range));

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
            ResultSet rs = stmt.executeQuery(DbQuery.getStampStock());

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

            ResultSet rs = stmt.executeQuery(DbQuery.getByTypeProducts(type));

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
            ResultSet rs = stmt.executeQuery(DbQuery.getSelectType());

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
                        ResultSet rs = stmt.executeQuery(DbQuery.getByBrand(selectedBrand));

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
            ResultSet rs = stmt.executeQuery(DbQuery.getIdClient(c));
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
            ResultSet rs = stmt.executeQuery(DbQuery.getIdCart(idClient));
            while (rs.next()){
                idCart = rs.getInt("idCart");
            }
            if(idCart == -1){
                ResultSet rs2 = stmt.executeQuery(DbQuery.getIdCartMax());
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
