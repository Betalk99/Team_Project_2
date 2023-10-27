package Carrello;

import Product.Product;

import java.util.ArrayList;

public class Cart {
    private ArrayList<Product> cart;

    public Cart(ArrayList<Product> cart) {
        this.cart = cart;
    }

    public ArrayList<Product> getCart() {
        return cart;
    }

    public void setCart(ArrayList<Product> cart) {
        this.cart = cart;
    }
}
