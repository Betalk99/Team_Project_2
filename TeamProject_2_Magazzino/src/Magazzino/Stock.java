package Magazzino;
import Product.*;
import java.util.ArrayList;
public class Stock {
    public ArrayList<Product> listaProdotti;

    public Stock(ArrayList<Product> listaProdotti) {
        this.listaProdotti = listaProdotti;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "listaProdotti=" + listaProdotti + "\n" +
                '}';
    }
}
