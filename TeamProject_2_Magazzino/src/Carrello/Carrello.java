package Carrello;

import Product.Product;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Carrello {

   private ArrayList<Product> carrello;

    public Carrello(ArrayList<Product> carrello) {
        this.carrello = carrello;
    }

    public ArrayList<Product> getCarrello() {
        return carrello;
    }

    public void setCarrello(ArrayList<Product> carrello) {
        this.carrello = carrello;
    }

}
