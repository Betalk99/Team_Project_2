package Carrello;

import Product.Product;

import java.util.*;

public class cartManagement {

    public static ArrayList<Product> creation() {
        ArrayList<Product> cart = new ArrayList<>();
        return cart;
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
                System.out.println("Would you like to keep purchasing products? Please enter 'Yes' or 'No'");
                String answer = in.next();
                if (Objects.equals(answer.toLowerCase(), "no")) {
                    stay = false;
                    stay2 = false;
                } else if (Objects.equals(answer.toLowerCase(), "sì") || Objects.equals(answer.toLowerCase(), "si")) {
                    stay = true;
                    stay2 = false;
                } else {
                    System.out.println("Unavailable answer inserted");
                    stay2 = true;
                }
            }
        }
        boolean stay3 = true;
        while (stay3) {
            try {
                System.out.println("""
                        To purchase product/s in your cart please enter: "1"
                        To discard all products in your cart please enter: "2"
                        """);
                int answer = in.nextInt();
                if (answer == 1) {
                    stock = arrayTemp;
                    stay3 = false;
                } else if (answer == 2) {
                    cart.clear();
                    stay3 = false;
                } else {
                    System.out.println("Unavailable answer inserted");
                    stay3 = true;
                }
            } catch (NoSuchElementException e) {
                System.out.println("Please insert an integer number");
            }
        }
        return stock;
    }
}
