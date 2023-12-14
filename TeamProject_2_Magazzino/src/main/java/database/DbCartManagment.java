package database;

import cart.cartManagement;
import choice.whichOperationCustomer;
import order.Orders;
import product.*;

import java.math.BigDecimal;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

import static cart.cartManagement.stampYourCart;

public class DbCartManagment {

    public static ArrayList<Product> cartStatus(int idClient) throws SQLException {
        ArrayList<Product> cart = new ArrayList<>();
        try{
            Statement stmt = DbManagement.makeConnection();
            String joinCartProd = "SELECT * FROM product\n" +
                    "JOIN cart ON cart.idProduct = product.id\n" +
                    "WHERE cart.idClient = "+ idClient +" AND cart.status = 1;";
            ResultSet rs = stmt.executeQuery(joinCartProd);

            while (rs.next()) {
                cart.add(DbManagement.costructProd(rs));
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return cart;
    }

    public static void addIdProdDB(int idCart, int idClient){
        try{
            Statement stmt = DbManagement.makeConnection();
            System.out.println("That's the list of the products currently available in our store.");
            whichOperationCustomer.stampResult(DbManagement.stampStockDb());
            Scanner sc = new Scanner(System.in);
            System.out.println("Select the ID of the product you would like to add to your cart.");
            int idProd = sc.nextInt();
            String query = "INSERT INTO `projectteam`.`cart`\n" +
                    "(idCart,\n" +
                    "idProduct,\n" +
                    "idClient)\n" +
                    "VALUES\n" +
                    "(" + idCart + ",\n" +
                    " " + idProd + ",\n" +
                    " " + idClient + ");\n";
            stmt.execute(query);
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static void removeProdById(int idClient){
        try{
            Statement stmt = DbManagement.makeConnection();
            System.out.println("Select the ID of the product you would like to remove from your cart.");
            stampYourCart(DbCartManagment.cartStatus(idClient));
            Scanner sc = new Scanner(System.in);
            int idProd = sc.nextInt();
            String query = "DELETE FROM `projectteam`.`cart` WHERE `id` = " + idProd + ";";
            stmt.execute(query);
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void checkout(int idCart, int idClient){  //checkout fatto da Bruno Orlandi
        try{
            Scanner in = new Scanner(System.in);
            Statement stmt = DbManagement.makeConnection();
            cartManagement.stampYourCart(DbCartManagment.cartStatus(idClient));

            System.out.println("Are you sure you want to proceed to checkout?  1) YES  2) NO");
            getTotalPrice(idCart, idClient);
            int ans = in.nextInt();

            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();

            ArrayList<Product> stockUpdate = new ArrayList<>();
            ResultSet rs = stmt.executeQuery("SELECT * FROM product" +
                    " INNER JOIN cart ON product.id = cart.idProduct" +
                    " WHERE cart.idCart = " + idCart + " AND cart.idClient = " + idClient + ";");
            while(rs.next()) {
                stockUpdate.add(DbManagement.costructProd(rs));
            }

            if(ans == 1){
                String checkout = "INSERT INTO orders (idCart, date)" +
                        "VALUES ("+idCart+", '"+ dateFormat.format(date) +"' )";
                stmt.execute(checkout);
                stmt.executeUpdate("UPDATE cart AS c " +
                        "SET c.status = 0 " +
                        "WHERE c.idClient = "+ idClient +" AND c.idCart = "+ idCart + ";");

                for(int i = 0; i < stockUpdate.size(); i++) {
                    stmt.executeUpdate("UPDATE stock" +
                            " SET stock.qty = stock.qty - 1" +
                            " WHERE " + stockUpdate.get(i).getItemId() + " = stock.idStock;");
                }

                System.out.println("You have completed your order on: " + dateFormat.format(date));
            }else{
                System.out.println("You have chosen not to proceed to checkout." );
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static BigDecimal getTotalPrice (int idCart, int idClient) throws SQLException {
        BigDecimal totalPrice = null;
        Statement stmt = DbManagement.makeConnection();
        String query = "SELECT SUM(product.sellprice) AS totalprice" +
                " FROM cart" +
                " JOIN product ON cart.idProduct = product.id" +
                " WHERE cart.idClient = " + idClient + " AND cart.idCart = " + idCart + ";";
        ResultSet rs = stmt.executeQuery(query);
        while(rs.next()) {
            totalPrice = rs.getBigDecimal("totalprice");
        }
        System.out.println("The total price of the items in your cart is " + totalPrice);
        return totalPrice;
    }

    public static ArrayList<Product> myOrder(int idClient){
        ArrayList<Product> choiceOrder = new ArrayList<>();
        ArrayList<Orders> listOrder = new ArrayList<>();
        try{
            Scanner in = new Scanner(System.in);

            Statement stmt = DbManagement.makeConnection();
            String orderClient = "SELECT * FROM orders AS o\n" +
                    "JOIN cart AS c ON o.idCart = c.idCart\n" +
                    "WHERE c.idClient = "+ idClient +"; ";
            ResultSet rs = stmt.executeQuery(orderClient);

            while (rs.next()){
                if(!(listOrder.contains(DbManagement.costructOrder(rs)))){
                    listOrder.add(DbManagement.costructOrder(rs));
                }
            }

            stampMyOrder(listOrder);

            System.out.println("Which order do you want to display ? Indicate ID ");
            int idOrder = in.nextInt();

            String visualOrderQuery = "SELECT p.id, p.`type`, p.brand, p.model, p.description, p.displaysize, p.storagecap, p.purchaseprice, p.sellprice FROM orders AS o\n" +
                    "JOIN cart AS c ON o.idCart = c.idCart\n" +
                    "JOIN product AS p ON c.idProduct = p.id\n" +
                    "WHERE c.idClient = " + idClient + " AND c.idCart = " + idOrder + ";";

            ResultSet rs1 = stmt.executeQuery(visualOrderQuery);

            while (rs1.next()){
                choiceOrder.add(DbManagement.costructProd(rs1));
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }catch (InputMismatchException e){
            System.out.println(e.getMessage());
        }
        return choiceOrder;
    }

    public static void stampMyOrder(ArrayList<Orders>  listOrder){
        for(Orders i: listOrder){
            System.out.println(i);
        }
    }

    public static void addProductToCart(int idCart, int idClient) throws SQLException {
        Scanner sc = new Scanner(System.in);
        boolean stay = true;
        while(stay) {
            System.out.println("That's the list of the products currently available in our store.");
            whichOperationCustomer.stampResult(DbManagement.stampStockDb());
            System.out.println("Please write the brand of the product you would like to add to your cart.");
            String brandName = sc.nextLine();
            System.out.println("Please write the model of the product you would like to add to your cart.");
            String modelName = sc.nextLine();
            Statement stmt = DbManagement.makeConnection();
            String query = "SELECT * FROM product" +
                    " WHERE brand COLLATE utf8mb4_general_ci = '" + brandName +
                    "' AND model COLLATE utf8mb4_general_ci = '" + modelName + "'";
            ResultSet rs = stmt.executeQuery(query);
            if (!rs.next()) {
                System.out.println("We are sorry. We couldn't find any product matching your request.");
            } else {
                stmt.executeUpdate("INSERT INTO cart (idCart, idProduct, idClient)" +
                        " VALUES ('" + idCart + "', '" + rs.getString("id") + "', '" + idClient + "')");

            }
            boolean stay2 = true;
            while(stay2) {
                System.out.println("Would you like to try to add one more item to your cart? YES / NO");
                String reply = sc.nextLine().toLowerCase();
                switch (reply) {
                    case "yes":
                        stay = true;
                        stay2 = false;
                        break;
                    case "no":
                        stay = false;
                        stay2 = false;
                        break;
                    default:
                        System.out.println("Please insert a valid answer.");
                        stay = true;
                        stay2 = true;
                }
            }
        }
    }

    public static void getEmptyCart(int idClient){
        try{
            Statement stmt = DbManagement.makeConnection();
            System.out.println("Do you want to get an empty cart?\nIf you are sure press : 1(Yes) or 2(No)");
            stampYourCart(DbCartManagment.cartStatus(idClient));
            Scanner sc = new Scanner(System.in);
            int choice = sc.nextInt();
            String query = "DELETE FROM `projectteam`.`cart` " +
                    "WHERE `cart`.`idClient` = " + idClient + " " +
                    "AND `cart`.`status` = 1 ;";
            if(choice == 1){
                stmt.execute(query);
            }else{
                System.out.println("You have chosen not to empty your cart!");
            }
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static BigDecimal avarageAmountSpent(int idClient, int idCart){
        BigDecimal sum = null;
        try{
            Statement stmt = DbManagement.makeConnection();
            System.out.println("Your avarage amount is : ");
            String query = "SELECT SUM(p.sellprice) AS sum FROM cart AS c " +
                    "JOIN product AS p ON c.idProduct = p.id " +
                    "WHERE c.idClient = " + idClient + " AND c.idCart = " + idCart + ";";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()){
                sum = BigDecimal.valueOf(rs.getInt("sum"));
            }

        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return sum;
    }
}
