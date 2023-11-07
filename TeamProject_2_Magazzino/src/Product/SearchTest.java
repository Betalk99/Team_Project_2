package Product;

import Magazzino.ProductBase;
import Magazzino.Stock;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SearchTest {

    @Test
    void byCostPriceRangeFrom1To150() {
        Stock stock = new Stock();
        stock.setListaProdotti(ProductBase.baseProd(stock.getListaProdotti()));
        double[] rangeValues = {1, 150};

        ArrayList<Product> a = Search.byCostPriceRange(stock, rangeValues);

        assertEquals(3, a.size());
    }

    @Test
    void getNotebookType() {
        Stock stock = new Stock();
        stock.setListaProdotti(ProductBase.baseProd(stock.getListaProdotti()));
        ArrayList<Product> note = Search.getNotebookType(stock);

        assertEquals(3,note.size());
    }

    @Test
    void getNotebookTypeNull() {
        Stock stock = new Stock();
        stock.setListaProdotti(ProductBase.baseProd(stock.getListaProdotti()));
        ArrayList<Product> note = Search.getNotebookType(null);

        assertEquals(null,note);
    }

    @Test
    void getTabletType() {
        Stock stock = new Stock();
        stock.setListaProdotti(ProductBase.baseProd(stock.getListaProdotti()));
        ArrayList<Product> tab = Search.getTabletType(stock);

        assertEquals(3,tab.size());
    }

    @Test
    void getTabletTypeNull() {
        Stock stock = new Stock();
        stock.setListaProdotti(ProductBase.baseProd(stock.getListaProdotti()));
        ArrayList<Product> tab = Search.getTabletType(null);

        assertEquals(null,tab);
    }

    @Test
    void getSmartphoneType() {
        Stock stock = new Stock();
        stock.setListaProdotti(ProductBase.baseProd(stock.getListaProdotti()));
        ArrayList<Product> smartphone = Search.getSmartphoneType(stock);

        assertEquals(3,smartphone.size());
    }

    @Test
    void getSmartphoneTypeNull() {
        Stock stock = new Stock();
        stock.setListaProdotti(ProductBase.baseProd(stock.getListaProdotti()));
        ArrayList<Product> smartphone = Search.getSmartphoneType(null);

        assertEquals(null,smartphone);
    }
}