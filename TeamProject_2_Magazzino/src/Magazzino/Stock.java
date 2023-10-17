package Magazzino;
import Product.*;
import java.util.ArrayList;
public class Stock {
    public ArrayList<Product> listaProdotti = new ArrayList<>();

    @Override
    public String toString() {
        return "Stock{" +
                "listaProdotti=" + listaProdotti +
                '}';
    }
}
