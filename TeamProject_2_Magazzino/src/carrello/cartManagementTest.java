package carrello;

import magazzino.*;
import product.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

import static carrello.cartManagement.addProdCart;
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
        cart = cartManagement.removeId(cart, arrayTemp,idRemove);
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
        cart = cartManagement.removeId(cart, null,idRemove);
        assertEquals(null, cart);

    }

    @Test
    void itemsRemovedFromArrayTempAddProdCart() {
        Stock stock = new Stock();
        stock.setListaProdotti(ProductBase.baseProd(stock.getListaProdotti()));
        ArrayList<Product> arrayTemp = new ArrayList<>();
        arrayTemp.addAll(stock.getListaProdotti());
        Cart cart = new Cart();
        int originalSize = arrayTemp.size();
        cartManagement.addProdCart(cart, arrayTemp, 3);
        int newSize = arrayTemp.size();
        assertEquals(newSize, originalSize-1);
    }

    @Test
    void itemsAddedToCartAddProdCart() {
        Stock stock = new Stock();
        stock.setListaProdotti(ProductBase.baseProd(stock.getListaProdotti()));
        ArrayList<Product> arrayTemp = new ArrayList<>();
        arrayTemp.addAll(stock.getListaProdotti());
        Cart cart = new Cart();
        int originalSize = cart.getCart().size();
        cartManagement.addProdCart(cart, arrayTemp, 3);
        int newSize = cart.getCart().size();
        assertEquals(newSize, originalSize+1);
    }

    @Test
    void nullInputAddProdCart() {
        Stock stock = new Stock();
        stock.setListaProdotti(ProductBase.baseProd(stock.getListaProdotti()));
        ArrayList<Product> arrayTemp = new ArrayList<>();
        arrayTemp.addAll(stock.getListaProdotti());
        Cart cart = new Cart();
        assertEquals("No exceptions were caught.", cartManagement.addProdCart(null, arrayTemp, 4));
    }

    @Test
    void correctStockUpdateBuyProducts() {
        Stock stock = new Stock();
        stock.setListaProdotti(ProductBase.baseProd(stock.getListaProdotti()));
        ArrayList<Product> arrayTemp = new ArrayList<>();
        arrayTemp.addAll(stock.getListaProdotti());
        Cart cart = new Cart();
        cartManagement.buyProducts(cart, stock, arrayTemp);
        assertEquals(arrayTemp.size(),stock.getListaProdotti().size());
    }

    @Test
    void correctProductAdditionToFinalizedPurchasesBuyProducts() {
        Stock stock = new Stock();
        stock.setListaProdotti(ProductBase.baseProd(stock.getListaProdotti()));
        ArrayList<Product> arrayTemp = new ArrayList<>();
        arrayTemp.addAll(stock.getListaProdotti());
        Cart cart = new Cart();
        Tablet testItem1 = new Tablet(ProductTypes.tablet, "Samsung", "Galaxy Tab S6 Lite", "con pen in dotazione", 10.4, 128, 120.00, 298.00, "001");
        Smartphone testItem2 = new Smartphone(ProductTypes.smartphone, "Samsung", "Galaxy S23 Ultra", "top di gamma", 6.8, 256, 450.00, 999.00, "005");
        Notebook testItem3 = new Notebook(ProductTypes.notebook, "MSI", "GF 63 A54", "Serie Gaming", 15.6, 1000, 980, 1699.00, "007");
        cart.getCart().add(testItem1);
        cart.getCart().add(testItem2);
        cart.getCart().add(testItem3);
        int cartSize = cart.getCart().size();
        assertEquals(cartSize,cartManagement.buyProducts(cart, stock, arrayTemp).size());
    }

    @Test
    void nullInputBuyProducts() {
        Stock stock = new Stock();
        stock.setListaProdotti(ProductBase.baseProd(stock.getListaProdotti()));
        ArrayList<Product> arrayTemp = new ArrayList<>();
        arrayTemp.addAll(stock.getListaProdotti());
        Cart cart = new Cart();
        Tablet testItem1 = new Tablet(ProductTypes.tablet, "Samsung", "Galaxy Tab S6 Lite", "con pen in dotazione", 10.4, 128, 120.00, 298.00, "001");
        Smartphone testItem2 = new Smartphone(ProductTypes.smartphone, "Samsung", "Galaxy S23 Ultra", "top di gamma", 6.8, 256, 450.00, 999.00, "005");
        Notebook testItem3 = new Notebook(ProductTypes.notebook, "MSI", "GF 63 A54", "Serie Gaming", 15.6, 1000, 980, 1699.00, "007");
        cart.getCart().add(testItem1);
        cart.getCart().add(testItem2);
        cart.getCart().add(testItem3);
        assertEquals(0, cartManagement.buyProducts(cart, null, arrayTemp).size());
    }

    @Test
    void cartTotalCanSumThePriceOfGoods() {
        Cart cart = new Cart();
        Tablet testItem1 = new Tablet(ProductTypes.tablet, "Samsung", "Galaxy Tab S6 Lite", "con pen in dotazione", 10.4, 128, 120.00, 298.00, "001");
        Smartphone testItem2 = new Smartphone(ProductTypes.smartphone, "Samsung", "Galaxy S23 Ultra", "top di gamma", 6.8, 256, 450.00, 999.00, "005");
        Notebook testItem3 = new Notebook(ProductTypes.notebook, "MSI", "GF 63 A54", "Serie Gaming", 15.6, 1000, 980, 1699.00, "007");
        cart.getCart().add(testItem1);
        cart.getCart().add(testItem2);
        cart.getCart().add(testItem3);
        assertEquals(2996, cartManagement.cartTotal(cart));
    }

    @Test
    void nullInputCartTotal() {
        Cart cart = null;
        assertEquals(-1, cartManagement.cartTotal(cart));
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

        ArrayList<Product> x = cartManagement.getAddId(arraytemp,cart,valoreScelto);
        assertEquals(valoreScelto,x.get(0).getItemId());

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

        ArrayList<Product> x = cartManagement.getAddId(arraytemp,cart,valoreScelto);
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