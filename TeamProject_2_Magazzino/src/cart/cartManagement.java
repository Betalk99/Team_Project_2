package cart;

import database.DbManagement;
import stock.Stock;
import product.*;


import java.math.BigDecimal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.OffsetDateTime;
import java.util.*;
import choice.*;

import static choice.whichOperationCustomer.stampResult;

public class cartManagement {

    public static void stampYourCart(ArrayList<Product> cart){
        System.out.println("Your Cart :  \n");
        for(Product i : cart){
            System.out.println(i);
        }
        System.out.println("\n");
    }


    public static Map<OffsetDateTime, ArrayList<Product>> receipt = new LinkedHashMap<>();


    public static void operCar(Stock stock, Cart cart, ArrayList<Product> arrayTemp, int idCart , int idClient) {

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
                         8) Get the average amount spent""");


                int operCarr = sc.nextInt();
                switch (operCarr) {
                    case 1://controllo stato carrello
                        stampYourCart(cartStatus(idClient));
                        break;
                    case 2://aggiunta elementi da carrello tramite id
                        addIdProdDB(idCart,idClient);
                        break;
                    case 3://rimozione elementi da carrello tramite id
                        removeProdFromId(idClient);
                        break;
                    case 4://Finalizza acquisti
                        break;
                    case 5://Aggiunta prodotti al carrello
                        break;
                    case 6://Prezzo totale dei prodotti nel carrello.
                        break;
                    case 7:
                        break;
                    case 8:
                        //averageSpending(cart);
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

            while (rs.next()){
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
}
