package Magazzino;

import Product.*;

import java.util.ArrayList;

public class Stock {
    public ArrayList<Product> productList;

    public Stock(ArrayList<Product> listaProdotti) {
        this.productList = listaProdotti;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "listaProdotti=" + productList +
                '}';
    }
}
