package Product;

public class Notebook extends Product {
    public Notebook(ProductTypes type, String producer, String model, String description, double displaySize, double storageCap, double purchasePrice, double sellPrice, String itemId) {
        super(type, producer, model, description, displaySize, storageCap, purchasePrice, sellPrice, itemId);
    }
}
