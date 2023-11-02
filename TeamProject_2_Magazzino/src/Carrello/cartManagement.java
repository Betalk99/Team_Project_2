package Carrello;

import Product.*;

import java.util.*;

public class cartManagement {

    public static void operCar(ArrayList<Product> stock,ArrayList<Product> cart){
        Scanner sc = new Scanner(System.in);
//        System.out.println(stock);
        System.out.println("""
                        Hello dear customer, please select one of the following options:
                         1) Cart status\s
                         2) Remove product/s to cart via ID\s
                         3) Get empty cart\s
                         4) Proceed to checkout\s
                         5) -----""");;


        int operCarr = sc.nextInt();
        switch (operCarr){
            case 1://controllo stato carrello
                System.out.println(cart);
                break;
            case 2://rimozione elementi da carrello tramite id
                removeId(cart);
                break;
            case 3://svuota carello
                break;
            case 4://Finalizza acquisti
                break;
            case 5://
                break;
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
                System.out.println("Would you like to keep purchasing products? Please enter 'Yes' or 'No'");
                String answer = in.next();
                if (Objects.equals(answer.toLowerCase(), "No")) {
                    stay = false;
                    stay2 = false;
                } else if (Objects.equals(answer.toLowerCase(), "Yes") || Objects.equals(answer.toLowerCase(), "yes")) {
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
        return arrayTemp;
    }


    public static ArrayList<Product> removeId(ArrayList<Product> cart){
        boolean stay = true;

//        Tablet tablet1 = new Tablet(ProductTypes.tablet, "Samsung", "Galaxy Tab S6 Lite", "con pen in dotazione", 10.4, 128, 120.00, 298.00, "011");
//        cart.add(tablet1);

        while(stay) {
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
            int i= in.nextInt();
            if(i == 2){
                stay = false;
            }
        }

        return cart;
    }


}
