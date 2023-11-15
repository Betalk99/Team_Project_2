package product;

public class Product {
    public ProductTypes type;
    private String model;
    private String brand;
    private String description;
    private double displaySize;
    private double storageCap;
    private double purchasePrice;
    private double sellPrice;
    private String itemId;

    public Product(ProductTypes type, String brand, String model, String description, double displaySize, double storageCap, double purchasePrice, double sellPrice, String itemId) {
        this.type = type;
        this.brand = brand;
        this.model = model;
        this.description = description;
        this.displaySize = displaySize;
        this.storageCap = storageCap;
        this.purchasePrice = purchasePrice;
        this.sellPrice = sellPrice;
        this.itemId = itemId;
    }

    public ProductTypes getType() {
        return type;
    }

    public void setType(ProductTypes type) {
        this.type = type;
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

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public String toString() {
        return "Product{" +
                "type=" + type +
                ", model='" + model + '\'' +
                ", producer='" + brand + '\'' +
                ", description='" + description + '\'' +
                ", displaySize=" + displaySize +
                ", storageCap=" + storageCap + " GB" +
                ", purchasePrice=" + purchasePrice +
                ", sellPrice=" + sellPrice +
                ", itemId='" + itemId + '\'' +
                '}';
    }
}