package product;

import java.util.Scanner;

public class Smartphone extends Product{
    public Smartphone(ProductTypes type, String brand, String model, String description, double displaySize, double storageCap, double purchasePrice, double sellPrice, String itemId) {
        super(type, brand, model, description, displaySize, storageCap, purchasePrice, sellPrice, itemId);
    }

    public static Smartphone inputKeyboard(){
        Scanner in = new Scanner(System.in);

        System.out.println("Producer :  ");
        String producer = in.nextLine();
        System.out.println("Model :  ");
        String model = in.nextLine();
        System.out.println("Id :  ");
        String id = in.nextLine();
        System.out.println("Description :  ");
        String description = in.nextLine();
        System.out.println("Dim Display  :  ");
        double dim_display = in.nextDouble();
        System.out.println("Dim storage :  ");
        double dim_arch = in.nextInt();
        System.out.println("Purchase Price :  ");
        double purchasePrice = in.nextDouble();
        System.out.println("Sell Price :  ");
        double sellPrice = in.nextDouble();

        Smartphone tab = new Smartphone(ProductTypes.smartphone,producer,model,description,dim_display,dim_arch, purchasePrice,sellPrice,id);


        return tab;

    }


}