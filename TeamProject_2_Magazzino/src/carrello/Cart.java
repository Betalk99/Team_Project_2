package carrello;

import product.Product;

import java.util.ArrayList;

public class Cart {
    private ArrayList<Product> cart = new ArrayList<>();

    public ArrayList<Product> getCart() {

        return cart;
    }

    public void setCart(ArrayList<Product> cart) {
        this.cart = cart;
    }
    @Override
    public String toString() {
        return "Cart{" +
                "cart=" + cart +
                '}';
    }
}
