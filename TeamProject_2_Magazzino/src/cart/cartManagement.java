package cart;

import database.DbManagement;
import product.*;


import java.math.BigDecimal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.util.*;
import choice.*;

public class cartManagement {

    public static void stampYourCart(ArrayList<Product> cart){
        System.out.println("Your Cart :  \n");
        for(Product i : cart){
            System.out.println(i);
        }
        System.out.println("\n");
    }

    public static void checkCartEmpty(int idClient) throws SQLException {   // verifica se cart è vuoto
        ArrayList<Product> cart = cartStatus(idClient);
        if(cart.isEmpty()){
            System.out.println("Cart is Empty");
        }else{
            stampYourCart(cartStatus(idClient));
        }
    }


    public static void operCar(int idCart , int idClient) {

        System.out.println("CART ID " + idCart);

        System.out.println("CLIENT ID " + idClient);

        try {
            Scanner sc = new Scanner(System.in);
//        System.out.println(stock);
            boolean stay = true;
            while (stay) {
                System.out.println("""
                        Hello dear customer, please select one of the following options:
                         1) Cart status\s
                         2) Add product/s to cart via ID\s
                         3) Remove product/s to cart via ID\s
                         4) Proceed to checkout\s
                         5) Add products to your cart\s
                         6) Get the total price of the items in the cart\s
                         7) Get empty cart
                         8) Get the average amount spent
                         9) My order""");


                int operCarr = sc.nextInt();
                switch (operCarr) {
                    case 1://controllo stato carrello
                        checkCartEmpty(idClient);
                        break;
                    case 2://aggiunta elementi da carrello tramite id
                        addIdProdDB(idCart,idClient);
                        break;
                    case 3://rimozione elementi da carrello tramite id
                        removeProdFromId(idClient);
                        break;
                    case 4://Finalizza acquisti
                        checkout(idCart,idClient);
                        break;
                    case 5://Aggiunta prodotti al carrello
                        addProductToCart(idCart,idClient);
                        break;
                    case 6://Prezzo totale dei prodotti nel carrello.
                        getTotalPrice(idCart, idClient);
                        break;
                    case 7://Svuota carrello.
                        getEmptyCart(idClient);
                        break;
                    case 8: //averageSpending(cart);
                        System.out.println(avarageAmountSpent(idClient,idCart));
                        break;
                    case 9:// visualizzare ordini precedenti
                        stampYourCart(myOrder(idClient));
                        break;
                    default:
                        System.out.println("Error in your choice! ");
                        break;
                }
            }
            boolean stay2 = true;
            while (stay2) {
                System.out.println("If you'd like to perform other cart-related operations, type '1'.\nIf you wish to go back to the user's menu, type '2'.");
                String selectOption = sc.nextLine();
                if (Objects.equals(selectOption, "1")) {
                    stay = true;
                    stay2 = false;
                } else if (Objects.equals(selectOption, "2")) {
                    stay = false;
                    stay2 = false;
                } else {
                    System.out.println("Invalid input.");
                    stay = true;
                    stay2 = true;
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Error: invalid input.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Product> cartStatus(int idClient) throws SQLException {
        ArrayList<Product> cart = new ArrayList<>();
        try{
            Statement stmt = DbManagement.makeConnection();
            String joinCartProd = "SELECT * FROM cart AS c\n" +
                    "JOIN product AS p ON c.idProduct = p.id\n" +
                    "WHERE c.idClient = "+ idClient +" AND c.status = 1;";
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
            whichOperationCustomer.stampResult(DbManagement.stampStockDb());
            Scanner sc = new Scanner(System.in);
            System.out.println("Which device do you want to add in your cart from id?");
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

    public static void removeProdFromId(int idClient){
        try{
            Statement stmt = DbManagement.makeConnection();
            System.out.println("Which product do you want delete from your cart?\n" +
                    "Select the ID of product.");
            stampYourCart(cartStatus(idClient));
            Scanner sc = new Scanner(System.in);
            int idProd = sc.nextInt();
            String query = "DELETE FROM `projectteam`.`cart` WHERE `id` = " + idProd + ";";
            stmt.execute(query);
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /*public static void proceedToCheckout(int idCart, int idClient) throws SQLException {
        ArrayList<Integer> productsToVerifyOne = new ArrayList<>();
        ArrayList<Integer> productsToVerifyTwo = new ArrayList<>();
        boolean productAvailability = true;
        Statement stmt = DbManagement.makeConnection();
        Statement stmt2 = DbManagement.makeConnection();
        ResultSet rs1 = stmt.executeQuery("SELECT id FROM product");
        ResultSet rs2 = stmt2.executeQuery("SELECT idProduct FROM cart WHERE" +
                " idCart = " + idCart + " AND " + " idClient =" + idClient + ";");
        while(rs1.next()) {
            productsToVerifyOne.add(Integer.valueOf(rs1.getInt("id")));
        }
        while(rs2.next()) {
            productsToVerifyTwo.add(Integer.valueOf(rs1.getInt("idProduct")));
        }
        for(int i = 0; i < productsToVerifyTwo.size(); i++) {
            if(!productsToVerifyOne.contains(productsToVerifyTwo.get(i))) {
                productAvailability = false;
                break;
            }
        }
        //Logica necessaria a verificare la permanenza dei prodotti in magazzino
        // nel tempo necessario a finalizzare l'acquisto.
        if(productAvailability) {
            System.out.println("Your purchase has been finalized and your items are ready for shipping.");
            stmt.executeUpdate("INSERT INTO order (idCart, date)" +
                    " VALUES (" + idCart + ", '" + OffsetDateTime.now() + "')");
            //Aggiunta ordine alla tabella ordini.
            stmt.executeUpdate("UPDATE cart" +
                                   " SET orderStatus = 1" +
                                   " WHERE idClient = " + idClient + " AND idCart = " + idCart + ";");
            //Aggiornamento dello stato dei beni nel carrello tramite update di una nuova colonna "orderStatus".
            for(int i = 0; i < productsToVerifyTwo.size(); i++) {
                stmt.executeUpdate("DELETE product" +
                        " WHERE id = " + productsToVerifyTwo.get(i).intValue() + ";");
            //Eliminazione dei prodotti di cui è stato finalizzato l'acquisto dal magazzino.
            }
        } else {
            System.out.println("We are sorry. It seems that one of the selected items became unavailable while you were finalizing your purchase.");
            stmt.executeUpdate("DELETE FROM cart" +
                                   " WHERE idCart = " + idCart + " AND idClient = " + idClient + " AND orderStatus = 0;");
        }
    }*/




    public static void checkout(int idCart, int idClient){  //checkout fatto da Bruno Orlandi
        try{
            Scanner in = new Scanner(System.in);
            Statement stmt = DbManagement.makeConnection();
            cartManagement.stampYourCart(cartStatus(idClient));

            System.out.println("Are you sure you're checkout your cart?   1) YES  2) NO");
            System.out.println("Total cost is: " + avarageAmountSpent(idClient, idCart));
            int ans = in.nextInt();

            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();


            if(ans == 1){
                String checkout = "INSERT INTO orders (idCart, date)" +
                        "VALUES ("+idCart+", '"+ dateFormat.format(date) +"' )";
                stmt.execute(checkout);
                stmt.executeUpdate("UPDATE cart AS c " +
                        "SET c.status = 0 " +
                        "WHERE c.idClient = "+ idClient +" AND c.idCart = "+ idCart + ";");
                System.out.println("You have completed your order in date: " + dateFormat.format(date));
            }else{
                System.out.println("You have chosen not to checkout your cart " );
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static ArrayList<Product> myOrder(int idClient){
        ArrayList<Product> choiceOrder = new ArrayList<>();
        try{
            Scanner in = new Scanner(System.in);

            HashSet<Integer> listIdCart = new HashSet<>();
            HashSet<String> listDate = new HashSet<>();
            Statement stmt = DbManagement.makeConnection();
            String orderClient = "SELECT * FROM orders AS o\n" +
                    "JOIN cart AS c ON o.idCart = c.idCart\n" +
                    "WHERE c.idClient = "+ idClient +"; ";
            ResultSet rs = stmt.executeQuery(orderClient);
            while (rs.next()){
                listIdCart.add(rs.getInt("idCart"));
                listDate.add(rs.getString("date"));
            }

            stampMyOrder(listIdCart, listDate);
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

    public static void stampMyOrder(HashSet<Integer> listIdCart, HashSet<String> listDate ){
        String[] arrayListDate = listDate.toArray(new String[listDate.size()]);
        Integer[] arrayListIdCart = listIdCart.toArray(new Integer[listIdCart.size()]);

        for(int i = 0; i < arrayListDate.length; i++){
            System.out.println("ID Order : " + arrayListIdCart[i] + " Order Date: " + arrayListDate[i]);
        }
    }


    public static void addProductToCart(int idCart, int idClient) throws SQLException {
        Scanner sc = new Scanner(System.in);
        boolean stay = true;
        while(stay) {
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

    public static void getTotalPrice (int idCart, int idClient) throws SQLException {
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
    }

    public static void getEmptyCart(int idClient){
        try{
            Statement stmt = DbManagement.makeConnection();
            System.out.println("Do you want to get an empty cart?\nIf you are sure press : 1(Yes) or 2(No)");
            stampYourCart(cartStatus(idClient));
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
