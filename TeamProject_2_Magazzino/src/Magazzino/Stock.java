package Magazzino;
import Product.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Stock {
    private ArrayList<Product> listaProdotti = new ArrayList<>();

    public ArrayList<Product> getListaProdotti() {
        return listaProdotti;
    }

    public void setListaProdotti(ArrayList<Product> listaProdotti) {
        this.listaProdotti = listaProdotti;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "listaProdotti=" + listaProdotti + "\n" +
                '}';
    }
}
