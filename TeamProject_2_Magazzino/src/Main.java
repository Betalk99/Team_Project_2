import Product.*;
import Magazzino.*;
import Scelta.*;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        ArrayList<Product> arrayTemp = new ArrayList<>();
        ArrayList<Product> stock = new ArrayList<>();  // arraylist magazzino
        Stock mag = new Stock(stock);
        ProductBase.baseProd(stock); //richiamare oggetti precaricati arraylist magazzino
        ArrayList<Product> cart = new ArrayList<>();

        softwareUsageSelection(stock,cart,arrayTemp);// richiamo funzione per accedere alla scelta
    }
    public static void softwareUsageSelection(ArrayList<Product> stock, ArrayList<Product> cart,ArrayList<Product> arrayTemp) throws InputMismatchException {
        boolean isTrue = false;
        while (!isTrue) {
            try {
                Scanner in = new Scanner(System.in);
                System.out.println("Hello, who is going to use this management system? \n1-Customer \n2-Company user");
                int a = in.nextInt();
                if (a == 1) {
                    whichOperationCustomer.oper(stock,cart, arrayTemp);
                } else if (a == 2) {
                    whichOperationCompany.oper(stock,cart, arrayTemp);
                }
                System.out.println("would you like to carry out other actions? 1)Yes   2)No");
                int stay = in.nextInt();
                if (stay == 2) {
                    isTrue = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Please use a number between 1 (Yes) or 2 (No)");
                isTrue = false;
            }
        }
    }
}