package Magazzino;
import Product.*;

import java.util.ArrayList;

public class ProductBase {

    public static ArrayList<Product> baseProd (ArrayList<Product> stock) {
        Tablet tablet1 = new Tablet(ProductTypes.tablet, "Samsung", "Galaxy Tab S6 Lite", "con pen in dotazione", 10.4, 128, 120.00, 298.00, "001");
        Tablet tablet2 = new Tablet(ProductTypes.tablet, "Samsung", "Galaxy Tab A7 Lite", "economico", 8.7, 64, 60, 119.00, "002");
        Tablet tablet3 = new Tablet(ProductTypes.tablet, "Lenovo", "Tab M10", "miglior rapporto qualità prezzo", 10.6, 128, 150, 229, "003");
        stock.add(tablet1);
        stock.add(tablet2);
        stock.add(tablet3);

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        Smartphone smartp1 = new Smartphone(ProductTypes.smartphone, "Samsung", "Galaxy A54", "superofferta", 6.4, 256, 250, 529.00, "004");
        Smartphone smartp2 = new Smartphone(ProductTypes.smartphone, "Samsung", "Galaxy S23 Ultra", "top di gamma", 6.8, 256, 450.00, 999.00, "005");
        Smartphone smartp3 = new Smartphone(ProductTypes.smartphone, "Xiaomi", "Redmi Note 12 Pro", "best buy", 6.67, 256, 200, 339.9, "006");
        stock.add(smartp1);
        stock.add(smartp2);
        stock.add(smartp3);

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        Notebook note1 = new Notebook(ProductTypes.notebook, "MSI", "GF 63 A54", "Serie Gaming", 15.6, 1000, 980, 1699.00, "007");
        Notebook note2 = new Notebook(ProductTypes.notebook, "Lenovo", "Legion 5 Pro", "best buy", 16, 512, 780.00, 1499.00, "008");
        Notebook note3 = new Notebook(ProductTypes.notebook, "Asus", "TUf G F15", "superofferta", 15.6, 512, 940.00, 1399.00, "009");
        stock.add(note1);
        stock.add(note2);
        stock.add(note3);

        return stock;

    }

}
