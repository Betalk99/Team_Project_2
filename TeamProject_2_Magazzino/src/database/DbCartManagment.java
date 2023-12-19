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
        try {
            Statement stmt = DbManagement.makeConnection();
            String joinCartProd = "SELECT * FROM product\n" +
                    "JOIN cart ON cart.idProduct = product.id\n" +
                    "WHERE cart.idClient = " + idClient + " AND cart.status = 1;";
            ResultSet rs = stmt.executeQuery(joinCartProd);

            while (rs.next()) {
                cart.add(DbManagement.costructProd(rs));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return cart;
    }

    public static void addIdProdDB(int idCart, int idClient) {
        try {
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
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void removeProdById(int idClient) {
        try {
            Statement stmt = DbManagement.makeConnection();
            System.out.println("Select the ID of the product you would like to remove from your cart.");
            stampYourCart(DbCartManagment.cartStatus(idClient));
            Scanner sc = new Scanner(System.in);
            int idProd = sc.nextInt();
            String query = "DELETE FROM `projectteam`.`cart` WHERE `id` = " + idProd + ";";
            stmt.execute(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    ////////CHECKOUT BLOCK

    public static void checkOut(int idCart, int idClient) throws SQLException {
        Scanner in = new Scanner(System.in);
        cartManagement.checkCartEmpty(idClient);
        getTotalPrice(idCart, idClient);
        System.out.println("Are you sure you want to proceed to checkout?  YES / NO");

        String ans = in.nextLine();
        boolean stay = true;
        boolean stayTwo = true;
        while (stay) {
            if (ans.equalsIgnoreCase("yes")) {

                if (availabilityCheck(idCart, idClient)) {
                    finalizePurchase(idCart, idClient);
                } else {

                    while (stayTwo) {
                        cartUpdate(idCart, idClient);
                        System.out.println("It seems that while you were finalizing your purchases some of the items from your cart have become unavailable.\nThis is your current cart based on the currently available products.");
                        cartManagement.checkCartEmpty(idClient);
                        DbCartManagment.getTotalPrice(idCart, idClient);
                        System.out.println("Do you still want to proceed to checkout?      YES / NO");
                        String ansTwo = in.nextLine();

                        if (availabilityCheck(idCart, idClient) && ansTwo.equalsIgnoreCase("YES")) {
                            finalizePurchase(idCart, idClient);
                            stayTwo = false;
                        } else if (!availabilityCheck(idCart, idClient) && ansTwo.equalsIgnoreCase("YES")) {
                            stayTwo = true;
                        } else if (ansTwo.equalsIgnoreCase("NO")) {
                            System.out.println("You have decided not to proceed to checkout.");
                            stayTwo = false;
                        } else {
                            System.out.println("Invalid input.");
                            stayTwo = true;
                        }
                    }
                }
                stay = false;
            } else if (ans.equalsIgnoreCase("no")) {
                System.out.println("You have decided not to proceed to checkout.");
                stay = false;
            } else {
                System.out.println("Invalid input.");
                stay = true;
            }
        }
    }

    public static void finalizePurchase(int idCart, int idClient) throws SQLException {
        Statement stmt = DbManagement.makeConnection();

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        ArrayList<Product> cartList = createCartProductList(idCart, idClient);

        if (cartList.isEmpty()) {
            System.out.println("Your cart was empty. As a consequence, no operation has been performed.");
        } else {
            String checkout = "INSERT INTO orders (idCart, date)" +
                    "VALUES (" + idCart + ", '" + dateFormat.format(date) + "' )";
            stmt.execute(checkout);
            stmt.executeUpdate("UPDATE cart AS c " +
                    "SET c.status = 0 " +
                    "WHERE c.idClient = " + idClient + " AND c.idCart = " + idCart + ";");

            for (int i = 0; i < cartList.size(); i++) {
                stmt.executeUpdate("UPDATE stock" +
                        " SET stock.qty = stock.qty - 1" +
                        " WHERE " + cartList.get(i).getItemId() + " = stock.idStock;");
            }

            System.out.println("You have completed your order on: " + dateFormat.format(date));
        }
    }


    public static void cartUpdate(int idCart, int idClient) throws SQLException {
        Statement stmt = DbManagement.makeConnection();

        Map<Product, Integer> cartQuantities = countItemsByProduct(createCartProductList(idCart, idClient));
        Map<Integer, Integer> stockQuantities = createMapStockQty(idCart, idClient);
        Map<Product, Integer> valuesForUpdate = new HashMap<>();

        for (Map.Entry<Product, Integer> entry : cartQuantities.entrySet()) {
            Product product = entry.getKey();
            int cartQty = entry.getValue();

            for (Map.Entry<Integer, Integer> innerEntry : stockQuantities.entrySet()) {
                int stockId = innerEntry.getKey();
                int stockQty = innerEntry.getValue();

                if (product.getItemId() == stockId) {
                    valuesForUpdate.put(product, Math.abs(stockQty - cartQty));
                }
            }
        }
        //Riempimento di una mappa che associ i singoli prodotti alle quantità da ridurre nel carrello.

        for (Map.Entry<Product, Integer> entry : valuesForUpdate.entrySet()) {
            Product product = entry.getKey();
            int valueForUpdate = entry.getValue();

            stmt.executeUpdate("UPDATE cart" +
                    " SET cart.status = 0" +
                    " WHERE cart.idProduct = " + product.getItemId() +
                    " AND cart.idCart = " + idCart +
                    " AND cart.idClient = " + idClient +
                    " AND cart.status = 1" +
                    " LIMIT " + valueForUpdate + ";");
        }
        //Riduzione dei prodotti del carrello in proporzione alle minori quantità disponibili in magazzino.
    }

    public static boolean availabilityCheck(int idCart, int idClient) throws SQLException {
        Map<Integer, Integer> stockMap = createMapStockQty(idCart, idClient);

        boolean checker = true;
        Map<Product, Integer> productCountMap = countItemsByProduct(createCartProductList(idCart, idClient));
        outerLoop:
        for (Map.Entry<Product, Integer> entry : productCountMap.entrySet()) {
            Product product = entry.getKey();
            int cartQty = entry.getValue();

            for (Map.Entry<Integer, Integer> innerEntry : stockMap.entrySet()) {
                int stockId = innerEntry.getKey();
                int stockQty = innerEntry.getValue();

                if (product.getItemId() == stockId && cartQty > stockQty) {
                    checker = false;
                    break outerLoop;
                }
            }

        }
        return checker;
    }

    public static ArrayList<Product> createCartProductList(int idCart, int idClient) throws SQLException {
        Statement stmt = DbManagement.makeConnection();
        ArrayList<Product> cartProductList = new ArrayList<>();
        ResultSet rs = stmt.executeQuery("SELECT * FROM product" +
                " INNER JOIN cart ON product.id = cart.idProduct" +
                " WHERE cart.idCart = " + idCart + " AND cart.idClient = " + idClient + " AND cart.status = 1;");
        while (rs.next()) {
            cartProductList.add(DbManagement.costructProd(rs));
        }
        return cartProductList;
    }

    public static Map<Product, Integer> countItemsByProduct(ArrayList<Product> cartList) {
        Map<Product, Integer> productCountMap = new HashMap<>();
        for (Product product : cartList) {
            productCountMap.put(product, productCountMap.getOrDefault(product, 0) + 1);
        }
        return productCountMap;
    }

    public static Map<Integer, Integer> createMapStockQty(int idCart, int idClient) throws SQLException {
        Statement stmt = DbManagement.makeConnection();
        ResultSet rsStock = stmt.executeQuery("SELECT stock.idStock, stock.qty" +
                " FROM stock" +
                " INNER JOIN product ON stock.idStock = product.id" +
                " INNER JOIN cart ON product.id = cart.idProduct" +
                " WHERE cart.idCart = " + idCart +
                " AND idClient = " + idClient +
                " AND cart.status = 1" +
                " GROUP BY stock.idStock");

        Map<Integer, Integer> stockMap = new HashMap<>();
        while (rsStock.next()) {
            stockMap.put(rsStock.getInt("idStock"), rsStock.getInt("qty"));
        }
        return stockMap;
    }


    //////////////////////CHECKOUT BLOCK

    public static BigDecimal getTotalPrice(int idCart, int idClient) throws SQLException {
        BigDecimal totalPrice = null;
        Statement stmt = DbManagement.makeConnection();
        String query = "SELECT SUM(product.sellprice) AS totalprice" +
                " FROM cart" +
                " JOIN product ON cart.idProduct = product.id" +
                " WHERE cart.idClient = " + idClient + " AND cart.idCart = " + idCart + " AND cart.status = 1;";
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            totalPrice = rs.getBigDecimal("totalprice");
        }
        if (totalPrice == null) {
            totalPrice = BigDecimal.valueOf(0);
        }
        System.out.println("The total price of the items in your cart is " + totalPrice);
        return totalPrice;
    }

    public static ArrayList<Product> myOrder(int idClient) {
        ArrayList<Product> choiceOrder = new ArrayList<>();
        ArrayList<Orders> listOrder = new ArrayList<>();
        try {
            Scanner in = new Scanner(System.in);

            Statement stmt = DbManagement.makeConnection();
            String orderClient = "SELECT * FROM orders AS o\n" +
                    "JOIN cart AS c ON o.idCart = c.idCart\n" +
                    "WHERE c.idClient = " + idClient + "; ";
            ResultSet rs = stmt.executeQuery(orderClient);

            while (rs.next()) {
                if (!(listOrder.contains(DbManagement.costructOrder(rs)))) {
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

            while (rs1.next()) {
                choiceOrder.add(DbManagement.costructProd(rs1));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println(e.getMessage());
        }
        return choiceOrder;
    }

    public static void stampMyOrder(ArrayList<Orders> listOrder) {
        for (Orders i : listOrder) {
            System.out.println(i);
        }
    }

    public static void addProductToCart(int idCart, int idClient) throws SQLException {
        Scanner sc = new Scanner(System.in);
        boolean stay = true;
        while (stay) {
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
            while (stay2) {
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

    public static void getEmptyCart(int idClient) {
        try {
            Statement stmt = DbManagement.makeConnection();
            System.out.println("Do you want to get an empty cart?\nIf you are sure press : 1(Yes) or 2(No)");
            stampYourCart(DbCartManagment.cartStatus(idClient));
            Scanner sc = new Scanner(System.in);
            int choice = sc.nextInt();
            String query = "DELETE FROM `projectteam`.`cart` " +
                    "WHERE `cart`.`idClient` = " + idClient + " " +
                    "AND `cart`.`status` = 1 ;";
            if (choice == 1) {
                stmt.execute(query);
            } else {
                System.out.println("You have chosen not to empty your cart!");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static BigDecimal avarageAmountSpent(int idClient, int idCart) {
        BigDecimal sum = null;
        try {
            Statement stmt = DbManagement.makeConnection();
            System.out.println("Your avarage amount is : ");
            String query = "SELECT SUM(p.sellprice) AS sum FROM cart AS c " +
                    "JOIN product AS p ON c.idProduct = p.id " +
                    "WHERE c.idClient = " + idClient + " AND c.idCart = " + idCart + ";";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                sum = BigDecimal.valueOf(rs.getInt("sum"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return sum;
    }


}
