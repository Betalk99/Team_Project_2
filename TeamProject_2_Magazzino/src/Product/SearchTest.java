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
}