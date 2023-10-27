package Product;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Search {
    public static ArrayList<Product> byType(ArrayList<Product> stock) throws InputMismatchException {
        ArrayList<Product> devicesByType = new ArrayList<>();
        boolean isTrue = false;
        while (!isTrue) {
            try {
                Scanner in = new Scanner(System.in);

                System.out.println("Dear customer, please select one of the following categories: \n 1 = Notebook \n 2 = Smartphone \n 3 = Tablet");
                int category = in.nextInt();
                switch (category) {
                    case 1:
                        for (Product products : stock) {
                            if (products.getTipo() == TipoProdotti.notebook) {
                                devicesByType.add(products);
                                isTrue = true;
                            }
                        }
                        break;
                    case 2:
                        for (Product products : stock) {
                            if (products.getTipo() == TipoProdotti.smartphone) {
                                devicesByType.add(products);
                                isTrue = true;
                            }
                        }
                        break;
                    case 3:
                        for (Product products : stock) {
                            if (products.getTipo() == TipoProdotti.tablet) {
                                devicesByType.add(products);
                                isTrue = true;
                            }
                        }
                        break;
                    default:
                        System.out.println("We are sorry, the selected category does not exists");
                        isTrue = false;
                }

            } catch (InputMismatchException e) {
                System.out.println("Please use one of the following characters: 1, 2 or 3");
                isTrue = false;
            }

        }
        return devicesByType;
    }

    public static void byModel(ArrayList<Product> stock) {
        System.out.println("With this function you can search by model (and related ID) through the devices in our stock: ");
        for (int i = 0; i < stock.size(); i++) {
            System.out.println("> " + i + " < " + stock.get(i).getModel());
        }
    }

    public static void bySellPriceRange(ArrayList<Product> stock) throws InputMismatchException {

        boolean isTrue = false;
        while (!isTrue) {
            try {
                Scanner in = new Scanner(System.in);
                System.out.println("With this function you can search by price range");
                System.out.println("From");
                double rangeIni = in.nextDouble();
                System.out.println("to");
                double rangeFin = in.nextDouble();
                boolean inRange = false;
                for (int i = 0; i < stock.size(); i++) {
                    if (stock.get(i).getSellPrice() > rangeIni && stock.get(i).getSellPrice() < rangeFin) {
                        System.out.println(stock.get(i));
                        inRange = true;
                    }
                }
                if (!inRange) {
                    System.out.println("We don't have products within this range in our stock");
                }

                System.out.println("Would you like to do another research based on product's price range? 1)Si   2)No");
                int stay = in.nextInt();
                if (stay == 2) {
                    isTrue = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Please use an integer number (e.g. 150)");
                isTrue = false;
            }
        }
    }
}
