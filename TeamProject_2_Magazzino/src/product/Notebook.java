package product;

import java.math.BigDecimal;
import java.util.Scanner;

public class Notebook extends Product {
    public Notebook(ProductTypes type, String producer, String model, String description, double displaySize, double storageCap, BigDecimal purchasePrice, BigDecimal sellPrice, int itemId) {
        super(type, producer, model, description, displaySize, storageCap, purchasePrice, sellPrice, itemId);
    }
}
