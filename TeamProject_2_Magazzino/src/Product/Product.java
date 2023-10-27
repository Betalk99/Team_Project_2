package Product;

public class Product {
    public TipoProdotti tipo;
    private String model;
    private String producer;
    private String description;
    private double displaySize;
    private double storageCap;
    private double purchPrice;
    private double sellPrice;
    private String itemId;

    public Product(TipoProdotti tipo, String producer, String model, String description, double displaySize, double storageCap, double purchPrice, double sellPrice, String itemId) {
        this.tipo = tipo;
        this.producer = producer;
        this.model = model;
        this.description = description;
        this.displaySize = displaySize;
        this.storageCap = storageCap;
        this.purchPrice = purchPrice;
        this.sellPrice = sellPrice;
        this.itemId = itemId;
    }

    public TipoProdotti getTipo() {
        return tipo;
    }

    public void setTipo(TipoProdotti tipo) {
        this.tipo = tipo;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getDisplaySize() {
        return displaySize;
    }

    public void setDisplaySize(double displaySize) {
        this.displaySize = displaySize;
    }

    public double getStorageCap() {
        return storageCap;
    }

    public void setStorageCap(double storageCap) {
        this.storageCap = storageCap;
    }

    public double getPurchPrice() {
        return purchPrice;
    }

    public void setPurchPrice(double purchPrice) {
        this.purchPrice = purchPrice;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }





    @Override
    public String toString() {
        return "Product{" +
                "tipo=" + tipo +
                ", model='" + model + '\'' +
                ", producer='" + producer + '\'' +
                ", description='" + description + '\'' +
                ", displaySize=" + displaySize +
                ", storageCap=" + storageCap + "gb" +
                ", purchPrice=" + purchPrice +
                ", sellPrice=" + sellPrice +
                ", itemId='" + itemId + '\'' +
                '}';
    }



}
