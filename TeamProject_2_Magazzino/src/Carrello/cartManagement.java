package Carrello;

import Product.*;

import java.math.BigDecimal;
import java.util.*;

public class cartManagement {

    public static void operCar(ArrayList<Product> stock, ArrayList<Product> cart) {
        Scanner sc = new Scanner(System.in);
//        System.out.println(stock);
        boolean stay = true;
        while (stay) {
            System.out.println("""
                    Hello dear customer, please select one of the following options:
                     1) Cart status\s
                     2) Add product/s to cart via ID\s
                     3) Remove product/s to cart via ID\s
                     4) Get empty cart\s
                     5) Proceed to checkout\s
                     6) Add products to your cart\s
                     7) Get the total price of the items in the cart""");


            String operCarr = sc.nextLine();
            switch (operCarr) {
                case "1"://controllo stato carrello
                    System.out.println(cart);
                    break;
                case "2"://aggiunta elementi da carrello tramite id
                    addId(stock,cart);
                    break;
                case "3"://rimozione elementi da carrello tramite id
                    removeId(cart);
                    break;
                case "4"://svuota carello
                    break;
                case "5"://Finalizza acquisti
                    buyProducts(cart,stock);
                    break;
                case "6"://Aggiunta prodotti al carrello
                    management(cart, stock);
                case "7"://Prezzo totale dei prodotti nel carrello.
                    cartTotal(cart);
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
        ArrayList<Product> arrayTemp = new ArrayList<>();
        arrayTemp.clear();

        arrayTemp = stock;

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

    public static void addId(ArrayList<Product> stock,ArrayList<Product> cart){
        boolean stay = true;
        while (stay){
            Scanner in = new Scanner(System.in);
            System.out.println(stock);
            System.out.println("Which device do you want to add from your cart? \n Indicate the id : ");

            String idAdd = in.next();
           // System.out.println(idAdd);
            for(int i=0;i<stock.size();i++){
                if(stock.get(i).getItemId().equals(idAdd)){
                    cart.add(stock.get(i));
                }
            }
            stay=false;
        }
//        System.out.println(cart);
    }

    public static ArrayList<Product> removeId(ArrayList<Product> cart) {
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

    public static ArrayList<Product> buyProducts (ArrayList<Product> cart, ArrayList<Product> stock) {
        ArrayList<Product> finalizedPurchases = new ArrayList<>();
        for(int i = 0; i < cart.size(); i++) {
            stock.remove(cart.get(i));
            finalizedPurchases.add(cart.get(i));
        }
        cart.clear();
        return finalizedPurchases;
    }

    public static void cartTotal (ArrayList<Product> cart1) {
        double totalPrice = 0;
        for(int i = 0; i < cart1.size(); i++) {
            totalPrice += cart1.get(i).getSellPrice();
        }
        System.out.println(totalPrice);
    }


}
