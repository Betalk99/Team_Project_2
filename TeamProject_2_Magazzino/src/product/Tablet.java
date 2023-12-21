package product;

import java.math.BigDecimal;
import java.util.Scanner;

public class Tablet extends Product{
    public Tablet(ProductTypes type, String brand, String model, String description, double displaySize, double storageCap, BigDecimal purchasePrice, BigDecimal sellPrice, int itemId) {
        super(type, brand, model, description, displaySize, storageCap, purchasePrice, sellPrice, itemId);
    }
}
