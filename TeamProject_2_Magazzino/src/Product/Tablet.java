package Product;

public class Tablet extends Product{
    public Tablet(TipoProdotti tipo, String producer, String model, String description, double displaySize, double storageCap, double purchPrice, double sellPrice, String itemId) {
        super(tipo, producer, model, description, displaySize, storageCap, purchPrice, sellPrice, itemId);
    }
}