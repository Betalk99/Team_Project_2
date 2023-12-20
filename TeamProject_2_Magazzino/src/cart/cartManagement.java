package cart;

import database.DbCartManagment;
import database.DbManagement;
import order.Orders;
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

    public static void stampYourCart(ArrayList<Product> cart) {
        System.out.println("Your Cart :  \n");
        for (Product i : cart) {
            System.out.println(i);
        }
        System.out.println("\n");
    }

    public static void checkCartEmpty(int idClient) throws SQLException {   // verifica se cart è vuoto
        ArrayList<Product> cart = DbCartManagment.cartStatus(idClient);
        if (cart.isEmpty()) {
            System.out.println("The cart is Empty");
        } else {
            stampYourCart(DbCartManagment.cartStatus(idClient));
        }
    }


    public static void operCar(int idCart, int idClient) {

//        System.out.println("CART ID " + idCart);
//
//        System.out.println("CLIENT ID " + idClient);

        try {


            Scanner sc = new Scanner(System.in);
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
                         9) My order
                         10) Refresh cart to reflect current product availability""");


                int operCarr = sc.nextInt();
                switch (operCarr) {
                    case 1://controllo stato carrello
                        checkCartEmpty(idClient);
                        break;
                    case 2://aggiunta elementi da carrello tramite id
                        DbCartManagment.addIdProdDB(idCart, idClient);
                        break;
                    case 3://rimozione elementi da carrello tramite id
                        DbCartManagment.removeProdById(idCart, idClient);
                        break;
                    case 4://Finalizza acquisti
                        DbCartManagment.checkOut(idCart, idClient);
                        break;
                    case 5://Aggiunta prodotti al carrello
                        DbCartManagment.addProductToCart(idCart, idClient);
                        break;
                    case 6://Prezzo totale dei prodotti nel carrello.
                        DbCartManagment.getTotalPrice(idCart, idClient);
                        break;
                    case 7://Svuota carrello.
                        DbCartManagment.getEmptyCart(idClient);
                        break;
                    case 8: //averageSpending(cart);
                        System.out.println(DbCartManagment.avarageAmountSpent(idClient, idCart));
                        break;
                    case 9:// visualizzare ordini precedenti
                        stampYourCart(DbCartManagment.myOrder(idClient));
                        break;
                    case 10:// aggiorna il carrello in base alla disponibilità attuale dei prodotti in magazzino.
                        DbCartManagment.refreshCart(idCart, idClient);
                        break;
                    default:
                        System.out.println("Invalid input.");
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
}
