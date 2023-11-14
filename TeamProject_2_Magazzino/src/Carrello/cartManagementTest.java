package Carrello;

import Magazzino.*;
import Product.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class cartManagementTest {

    @Test
    void removeId() {
        Stock stock = new Stock();
        stock.setListaProdotti(ProductBase.baseProd(stock.getListaProdotti()));
        ArrayList<Product> arrayTemp = new ArrayList<>();
        arrayTemp.addAll(stock.getListaProdotti());
        String idRemove = "001";
        Cart cart = new Cart();
        cart.getCart().add(new Tablet(ProductTypes.tablet, "Samsung", "Galaxy Tab S6 Lite", "con pen in dotazione", 10.4, 128, 120.00, 298.00, "001"));
        cart = cartManagement.removeId(cart, arrayTemp, idRemove);
        assertEquals(0, cart.getCart().size());

    }

    @Test
    void removeIdNull() {
        Stock stock = new Stock();
        stock.setListaProdotti(ProductBase.baseProd(stock.getListaProdotti()));
//        ArrayList<Product> arrayTemp = new ArrayList<>();
//        arrayTemp.addAll(stock.getListaProdotti());
        String idRemove = "001";
        Cart cart = new Cart();
        cart.getCart().add(new Tablet(ProductTypes.tablet, "Samsung", "Galaxy Tab S6 Lite", "con pen in dotazione", 10.4, 128, 120.00, 298.00, "001"));
        cart = cartManagement.removeId(cart, null, idRemove);
        assertEquals(null, cart);

    }

    @Test
    void getAddId() {
        Cart cart = new Cart();
        Tablet tablet1 = new Tablet(ProductTypes.tablet, "Samsung", "Galaxy Tab S6 Lite", "con pen in dotazione", 10.4, 128, 120.00, 298.00, "001");
        cart.getCart().add(tablet1);
        Stock stock = new Stock();
        stock.setListaProdotti(ProductBase.baseProd(stock.getListaProdotti()));

        ArrayList<Product> arraytemp = new ArrayList<>();

        arraytemp.addAll(stock.getListaProdotti());
        String valoreScelto = "001";

        ArrayList<Product> x = cartManagement.getAddId(arraytemp, cart, valoreScelto);
        assertEquals(valoreScelto, x.get(0).getItemId());

    }

    @Test
    void getAddIdNull() {
        Cart cart = new Cart();
        cart.getCart().add(new Tablet(ProductTypes.tablet, "Samsung", "Galaxy Tab S6 Lite", "con pen in dotazione", 10.4, 128, 120.00, 298.00, null));
        Stock stock = new Stock();
        stock.setListaProdotti(ProductBase.baseProd(stock.getListaProdotti()));

        ArrayList<Product> arraytemp = new ArrayList<>();

        arraytemp.addAll(stock.getListaProdotti());
        String valoreScelto = "001";

        ArrayList<Product> x = cartManagement.getAddId(arraytemp, cart, valoreScelto);
        assertEquals(null, cart.getCart().get(0).getItemId());

    }

    @Test
    void averageSpending() {
        Cart cart = new Cart();
        cart.getCart().add(new Tablet(ProductTypes.tablet, "Samsung", "Galaxy Tab S6 Lite", "con pen in dotazione", 10.4, 128, 120.00, 298.00, "001"));
        cart.getCart().add(new Tablet(ProductTypes.smartphone, "Samsung", "Galaxy Tab S6 Lite", "con pen in dotazione", 10.4, 128, 250.00, 415.00, "002"));
        cart.getCart().add(new Tablet(ProductTypes.notebook, "Lenovo", "Galaxy Tab S6 Lite", "con pen in dotazione", 10.4, 128, 502.00, 745.00, "005"));

        double expected = 486.00;
        assertEquals(expected, cartManagement.averageSpending(cart));
    }

    @Test
    void averageSpendingSingleProductInCart() {
        Cart cart = new Cart();
        cart.getCart().add(new Tablet(ProductTypes.tablet, "Samsung", "Galaxy Tab S6 Lite", "con pen in dotazione", 10.4, 128, 120.00, 298.00, "001"));

        double expected = cart.getCart().get(0).getSellPrice();
        assertEquals(expected, cartManagement.averageSpending(cart));
    }

    @Test
    void averageSpendingEmptyCart() {
        Cart cart = new Cart();

        double expected = 0.0;
        assertEquals(expected, cartManagement.averageSpending(cart));
    }
}