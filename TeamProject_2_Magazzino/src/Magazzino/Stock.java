package Magazzino;
import Product.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Stock {
    public ArrayList<Product> listaProdotti;

    public Stock(ArrayList<Product> listaProdotti) {
        this.listaProdotti = listaProdotti;
    }

    public static void printStock(ArrayList<Product> listaProdotti){
        for(Product i : listaProdotti){
            System.out.println(i);
        }
    }


    @Override
    public String toString() {
        return "Stock{" +
                "listaProdotti=" + listaProdotti + "\n" +
                '}';
    }
}
