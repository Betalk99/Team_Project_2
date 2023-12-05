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
    public static Map<OffsetDateTime, ArrayList<Product>> receipt = new LinkedHashMap<>();


    public static void operCar(Stock stock, Cart cart, ArrayList<Product> arrayTemp, int idCart , int idClient) {
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
                        cartStatus();
                        break;
                    case 2://aggiunta elementi da carrello tramite id
                        addIdProdDB(idCart,idClient);
                        break;
                    case 3://rimozione elementi da carrello tramite id
                        insertRemoveId(cart, arrayTemp);
                        break;
                    case 4://Finalizza acquisti
                        buyProducts(cart, stock, arrayTemp);
                        break;
                    case 5://Aggiunta prodotti al carrello
                        insertAddProdCart(cart, arrayTemp);
                        break;
                    case 6://Prezzo totale dei prodotti nel carrello.
                        cartTotal(cart);
                        break;
                    case 7:
                        cart.getCart().clear();
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

    public static void cartStatus() throws SQLException {
        ArrayList<Product> cart = new ArrayList<>();
        Statement stmt = DbManagement.makeConnection();
        ResultSet rs = stmt.executeQuery("SELECT *" +
                                             " FROM cart" +
                                             " INNER JOIN product ON cart.idProduct = product.id;");
        if(!rs.next()) {
            System.out.println("The cart is empty.");
        } else {
            while (rs.next()) {
                cart.add(DbManagement.costructProd(rs));
            }
            System.out.println(cart.toString());
        }
    }
//    public static void addId(ArrayList<Product> arrayTemp, Cart cart) {
//        boolean stay = true;
//        while (stay) {
//            Scanner in = new Scanner(System.in);
//            System.out.println(arrayTemp);
//            System.out.println("Which device do you want to add from your cart? \n Indicate the id : ");
//
//            String idAdd = in.next();
//            // System.out.println(idAdd);
//            getAddId(arrayTemp, cart, idAdd);
//            stay = false;
//        }
//    }

    public static void addIdProdDB(int idCart, int idClient){
        try{
            Statement stmt = DbManagement.makeConnection();
            whichOperationCustomer.stampResult(DbManagement.stampStockDb());
            Scanner sc = new Scanner(System.in);
            System.out.println("Which device do you want to add in your cart from id?");
            int idProd = sc.nextInt();
            String query = "INSERT INTO `projectteam`.`cart`\n" +
                    "(`idCart`,\n" +
                    "`idProduct`,\n" +
                    "`idClient`)\n" +
                    "VALUES\n" +
                    "(" + idCart + ",\n" +
                    " " + idProd + ",\n" +
                    " " + idClient + ");\n";
            stmt.execute(query);
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

//    public static ArrayList<Product> getAddId(ArrayList<Product> arraytemp, Cart cart, String valoreScelto) {
//        ArrayList<Product> x = new ArrayList<Product>();
//        for (int i = 0; i < arraytemp.size(); i++) {
//            if (arraytemp.get(i).getItemId() != null) {
//                if (arraytemp.get(i).getItemId().equals(valoreScelto)) {
//                    x.add(arraytemp.get(i));
//                    cart.getCart().add(arraytemp.get(i));
//                    arraytemp.remove(arraytemp.get(i));
//                }
//            }
//            return x;
//        }
//        return null;
//    }

    public static void insertAddProdCart(Cart cart, ArrayList<Product> arrayTemp) {

        Scanner in = new Scanner(System.in);
        boolean stay = true;
        while (stay) {
            boolean stay4 = true;
            while (stay4) {
                try {

                    System.out.println("Please select the index of the product you want to add to your cart:");
                    for (int i = 0; i < arrayTemp.size(); i++) {
                        System.out.println(arrayTemp.get(i).getBrand() + " " + arrayTemp.get(i).getModel() + " Index: " + (i + 1));
                    }
                    int answer = in.nextInt();
                    if (1 <= answer && answer <= arrayTemp.size()) {
                        addProdCart(cart, arrayTemp, answer);
                        stay4 = false;
                    } else {
                        System.out.println("Invalid answer.");
                        stay4 = true;
                    }

                } catch (InputMismatchException e) {
                    System.out.println("Invalid answer.");
                    stay4 = true;
                }
            }
            boolean stay2 = true;
            while (stay2) {
                System.out.println("What would you like to do now:\nIf you wish to continue adding items to your cart, type 1.\nIf you wish to go back to the cart menu, type 2.");
                String answer = in.next();
                if (Objects.equals(answer, "1")) {
                    stay = true;
                    stay2 = false;
                } else if (Objects.equals(answer, "2")) {
                    stay = false;
                    stay2 = false;
                } else {
                    System.out.println("Invalid answer.");
                    stay2 = true;
                }
            }
        }
    }

    public static String addProdCart(Cart cart, ArrayList<Product> arrayTemp, int answer) {
        try {
            cart.getCart().add(arrayTemp.get(answer - 1));
            arrayTemp.remove(answer - 1);
        } catch (NullPointerException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return "No exceptions were caught.";
    }

    public static Cart insertRemoveId(Cart cart, ArrayList<Product> arrayTemp) {

        boolean stay = true;

        while (stay) {
            Scanner in = new Scanner(System.in);
            for (Product i : cart.getCart()) {
                System.out.println(i);
            }
            System.out.println("Which device do you want to remove from your cart? \n Indicate the id : ");
            String idRemove = in.next();

//            removeId(cart, arrayTemp, idRemove);

            System.out.println("Do you want to delete some other elements? 1/Yes - 2/No");
            int i = in.nextInt();
            if (i == 2) {
                stay = false;
            }
        }
        return cart;
    }

//    public static Cart removeId(Cart cart, ArrayList<Product> arrayTemp, String idRemove) {
//        if (arrayTemp != null && idRemove == null) {
//            if (idRemove != null && arrayTemp == null) {
//                for (int i = 0; i < cart.getCart().size(); i++) {
//                    if (cart.getCart().get(i).getItemId().equals(idRemove)) {
//                        Product a = cart.getCart().get(i); // creo oggetto temp
//                        arrayTemp.add(a); // aggiungo oggetto temp
//                        cart.getCart().remove(i); // rimozione oggetto da carrello
//                    }
//                }
//            }
//            return cart;
//        } else {
//            return null;
//        }
//    }


    public static ArrayList<Product> buyProducts(Cart cart, Stock stock, ArrayList<Product> arrayTemp) {
        ArrayList<Product> finalizedPurchases = new ArrayList<>();

        try {

            for (int i = 0; i < cart.getCart().size(); i++) {
                finalizedPurchases.add(cart.getCart().get(i));
            }
            receipt.put(OffsetDateTime.now(), finalizedPurchases);
//        System.out.println("scontrino" + receipt);
            for (Map.Entry<OffsetDateTime, ArrayList<Product>> entry : receipt.entrySet()) {
                System.out.println(entry);
            }
            stock.getListaProdotti().clear();
            stock.getListaProdotti().addAll(arrayTemp);
            cart.getCart().clear();
            return finalizedPurchases;

        } catch (NullPointerException e) {

            System.out.println("Error: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static BigDecimal cartTotal(Cart cart) {
        try {
            BigDecimal totalPrice = BigDecimal.valueOf(0);
            for (int i = 0; i < cart.getCart().size(); i++) {
                totalPrice = totalPrice.add(cart.getCart().get(i).getSellPrice());
            }
            System.out.println("Total price of the cart " + totalPrice);
            return totalPrice;
        } catch (NullPointerException e) {
            System.out.println("Error: " + e.getMessage());
            return BigDecimal.valueOf(-1);
        }
    }

    public static double averageSpending(Cart cart) {
        double result;
        double totalAmount = 0.0;
        int numberOfProducts = cart.getCart().size();
        if (numberOfProducts >= 2) {
            for (int i = 0; i < cart.getCart().size(); i++) {
//                totalAmount += cart.getCart().get(i).getSellPrice();
            }
            result = totalAmount / numberOfProducts;
            System.out.println("Total amount: " + totalAmount + " €");
            System.out.println("Number of items: " + numberOfProducts + " pcs");
            System.out.println("The average amount per item that you're going to spend is: " + result + " €");
            return result;
        } else if (numberOfProducts != 0) {
            System.out.println("You only have 1 product in your cart, you're going to spend: " + cart.getCart().get(0).getSellPrice() + " €");
//            return cart.getCart().get(0).getSellPrice();
        }
        System.out.println("Your cart is empty, please add some products");
        return 0.0;
    }
}
