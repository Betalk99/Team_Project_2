package product;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {
    public ProductTypes type;
    private String model;
    private String brand;
    private String description;
    private double displaySize;
    private double storageCap;
    private BigDecimal purchasePrice;
    private BigDecimal sellPrice;
    private int itemId;

    public Product(ProductTypes type, String brand, String model, String description, double displaySize, double storageCap, BigDecimal purchasePrice, BigDecimal sellPrice, int itemId) {
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

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public BigDecimal getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(BigDecimal sellPrice) {
        this.sellPrice = sellPrice;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(displaySize, product.displaySize) == 0 && Double.compare(storageCap, product.storageCap) == 0 && itemId == product.itemId && type == product.type && Objects.equals(model, product.model) && Objects.equals(brand, product.brand) && Objects.equals(description, product.description) && Objects.equals(purchasePrice, product.purchasePrice) && Objects.equals(sellPrice, product.sellPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, model, brand, description, displaySize, storageCap, purchasePrice, sellPrice, itemId);
    }

    @Override
    public String toString() {
        return "Product{" +
                "itemId='" + itemId + '\'' +
                ", type=" + type +
                ", model='" + model + '\'' +
                ", producer='" + brand + '\'' +
                ", description='" + description + '\'' +
                ", displaySize=" + displaySize +
                ", storageCap=" + storageCap + " GB" +
                ", purchasePrice=" + purchasePrice +
                ", sellPrice=" + sellPrice +
                '}';
    }



}
