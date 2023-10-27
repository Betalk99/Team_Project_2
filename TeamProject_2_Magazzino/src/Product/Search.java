package Product;

import java.util.*;

public class Search {
    public static ArrayList<Product> byType(ArrayList<Product> stock) throws InputMismatchException {
        ArrayList<Product> devicesByType = new ArrayList<>();
        boolean isTrue = false;
        while (!isTrue) {
            try {
                Scanner in = new Scanner(System.in);
                System.out.println("Please select one of the following categories: \n 1) Notebook \n 2) Smartphone \n 3) Tablet");
                int category = in.nextInt();
                switch (category) {
                    case 1:
                        for (Product products : stock) {
                            if (products.getType() == ProductTypes.notebook) {
                                devicesByType.add(products);
                                isTrue = true;
                            }
                        }
                        break;
                    case 2:
                        for (Product products : stock) {
                            if (products.getType() == ProductTypes.smartphone) {
                                devicesByType.add(products);
                                isTrue = true;
                            }
                        }
                        break;
                    case 3:
                        for (Product products : stock) {
                            if (products.getType() == ProductTypes.tablet) {
                                devicesByType.add(products);
                                isTrue = true;
                            }
                        }
                        break;
                    default:
                        System.out.println("The selected category is not available");
                        isTrue = false;
                }
            } catch (InputMismatchException e) {
                System.out.println("Please use a character between 1, 2 or 3");
                isTrue = false;
            }
        }
        return devicesByType;
    }

    public static void byModel(ArrayList<Product> stock) {
        System.out.println("Search by device model in our stock (with relative ID):");
        for (int i = 0; i < stock.size(); i++) {
            System.out.println("> " + i + " < " + stock.get(i).getModel());
        }
    }

    public static void bySellPriceRange(ArrayList<Product> stock) throws InputMismatchException {

        boolean isTrue = false;
        while (!isTrue) {
            try {
                double[] rangeValues = new double[2];
                Scanner in = new Scanner(System.in);
                System.out.println("Search products by price range");
                System.out.println("From");
                rangeValues[0] = in.nextDouble();
                System.out.println("to");
                rangeValues[1] = in.nextDouble();
                Arrays.sort(rangeValues);
                boolean inRange = false;
                for (int i = 0; i < stock.size(); i++) {
                    if (stock.get(i).getSellPrice() >= rangeValues[0] && stock.get(i).getSellPrice() <= rangeValues[1]) {
                        System.out.println(stock.get(i));
                        inRange = true;
                    }
                }
                if (!inRange) {
                    System.out.println("We don't have products for this price range in our stock");
                }
                System.out.println("Would you like to have another research by product range? 1)Yes   2)No");
                int stay = in.nextInt();
                if (stay == 2) {
                    isTrue = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Please use an integer number (e.g. 250");
                isTrue = false;
            }
        }
    }

    public static void byCostPriceRange(ArrayList<Product> stock) throws InputMismatchException {

        boolean isTrue = false;
        while (!isTrue) {
            try {
                double[] rangeValues = new double[2];
                Scanner in = new Scanner(System.in);
                System.out.println("Search products by cost range");
                System.out.println("From");
                rangeValues[0] = in.nextDouble();
                System.out.println("to");
                rangeValues[1] = in.nextDouble();
                Arrays.sort(rangeValues);
                boolean inRange = false;
                for (int i = 0; i < stock.size(); i++) {
                    if (stock.get(i).getPurchasePrice() >= rangeValues[0] && stock.get(i).getPurchasePrice() <= rangeValues[1]) {
                        System.out.println(stock.get(i));
                        inRange = true;
                    }
                }
                if (!inRange) {
                    System.out.println("We don't have products for this price range in our stock");
                }

                System.out.println("Would you like to research other products based on cost range? 1)Yes   2)No");
                int stay = in.nextInt();
                if (stay == 2) {
                    isTrue = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Please use an integer number (e.g. 250");
                isTrue = false;
            }
        }
    }
}
