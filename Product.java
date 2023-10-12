public class Product {
    
    String model;
    String description;
    double displaySize;
    double storageCap;
    double purchPrice;
    double sellPrice;
    String itemId;

    public Product(String model, String description, double displaySize, double storageCap, double purchPrice, double sellPrice, String itemId) 
        this.model = model;
        this.description = description;
        this.displaySize = displaySize;
        this.storageCap = storageCap;
        this.purchPrice = purchPrice;
        this.sellPrice = sellPrice;
        this.itemId = itemId;
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
}
