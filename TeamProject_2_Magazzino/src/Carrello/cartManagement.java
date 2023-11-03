package Carrello;

import Product.*;

import java.math.BigDecimal;
import java.util.*;

public class cartManagement {

    public static ArrayList<Product> arrayTemp = new ArrayList<Product>();

    public static void operCar(ArrayList<Product> stock, ArrayList<Product> cart) {

        arrayTemp.clear();

        arrayTemp = stock;

        Scanner sc = new Scanner(System.in);
//        System.out.println(stock);
        boolean stay = true;
        while (stay) {
            System.out.println("""
                    Hello dear customer, please select one of the following options:
                     1) Cart status\s
                     2) Remove product/s to cart via ID\s
                     3) Get empty cart\s
                     4) Proceed to checkout\s
                     5) Add products to your cart\s
                     6) Get the total price of the items in the cart""");


            String operCarr = sc.nextLine();
            switch (operCarr) {
                case "1"://controllo stato carrello
                    System.out.println(cart);
                    break;
                case "2"://rimozione elementi da carrello tramite id
                    removeId(cart, arrayTemp);
                    break;
                case "3"://svuota carello
                    break;
                case "4"://Finalizza acquisti
                    buyProducts(cart,stock, arrayTemp);
                    break;
                case "5"://Aggiunta prodotti al carrello
                    management(cart, stock);
                case "6"://Prezzo totale dei prodotti nel carrello.
                    cartTotal(cart, arrayTemp);
                    break;
            }
            boolean stay2 = true;
            while(stay2) {
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
        }

    }

    public static ArrayList<Product> management(ArrayList<Product> cart, ArrayList<Product> stock) {

        Scanner in = new Scanner(System.in);
        boolean stay = true;
        while (stay) {
            boolean stay4 = true;
            while (stay4) {
                try {

                    System.out.println("Please select the index of the product you want to add to your cart:");
                    for (int i = 0; i < stock.size(); i++) {
                        System.out.println(stock.get(i).getBrand() + " " + stock.get(i).getModel() + " Index: " + (i + 1));
                    }
                    int answer = in.nextInt();
                    if (1 <= answer && answer <= stock.size()) {
                        cart.add(arrayTemp.get(answer - 1));
                        arrayTemp.remove(answer - 1);
                        stay4 = false;
                    } else {
                        System.out.println("Unavailable answer inserted");
                        stay4 = true;
                    }

                } catch (InputMismatchException e) {
                    System.out.println("Unavailable answer inserted");
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
                    System.out.println("Unavailable answer inserted");
                    stay2 = true;
                }
            }
        }


        return arrayTemp;
    }


    public static ArrayList<Product> removeId(ArrayList<Product> cart, ArrayList<Product> arrayTemp) {
        boolean stay = true;

//        Tablet tablet1 = new Tablet(ProductTypes.tablet, "Samsung", "Galaxy Tab S6 Lite", "con pen in dotazione", 10.4, 128, 120.00, 298.00, "011");
//        cart.add(tablet1);

        while (stay) {
            Scanner in = new Scanner(System.in);
            for (Product i : cart) {
                System.out.println(i);
            }

            System.out.println("Which device do you want to remove from your cart? \n Indicate the id : ");
            String idRemove = in.next();

            for (int i = 0; i < cart.size(); i++) {
                if (cart.get(i).getItemId() == idRemove) {
                    cart.remove(i);
                }
            }
            System.out.println("Do you want to delete some other elements? 1/Yes - 2/No");
            int i = in.nextInt();
            if (i == 2) {
                stay = false;
            }
        }

        return cart;
    }

    public static ArrayList<Product> buyProducts (ArrayList<Product> cart, ArrayList<Product> stock, ArrayList<Product> arrayTemp) {
        ArrayList<Product> finalizedPurchases = new ArrayList<>();
        for(int i = 0; i < cart.size(); i++) {
            stock.remove(cart.get(i));
            finalizedPurchases.add(cart.get(i));
        }
        cart.clear();
        return finalizedPurchases;
    }


    public static void cartTotal (ArrayList<Product> cart1, ArrayList<Product> arrayTemp) {
        double totalPrice = 0;
        for(int i = 0; i < cart1.size(); i++) {
            totalPrice += cart1.get(i).getSellPrice();
        }
        System.out.println(totalPrice);
    }


}
