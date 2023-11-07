package Product;

import java.util.*;

import Carrello.*;
import Product.*;
import Magazzino.*;
import Scelta.*;
import Clients.*;


public class Search {
    public static ArrayList<Product> byType(Stock stock) throws InputMismatchException {
        ArrayList<Product> devicesByType = new ArrayList<>();
        boolean isTrue = false;
        while (!isTrue) {
            try {
                Scanner in = new Scanner(System.in);
                System.out.println("Please select one of the following categories: \n 1) Notebook \n 2) Smartphone \n 3) Tablet");
                int category = in.nextInt();
                switch (category) {
                    case 1:
                        for (Product products : stock.getListaProdotti()) {
                            if (products.getType() == ProductTypes.notebook) {
                                devicesByType.add(products);
                                isTrue = true;
                            }
                        }
                        break;
                    case 2:
                        for (Product products : stock.getListaProdotti()) {
                            if (products.getType() == ProductTypes.smartphone) {
                                devicesByType.add(products);
                                isTrue = true;
                            }
                        }
                        break;
                    case 3:
                        for (Product products : stock.getListaProdotti()) {
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

    public static void byModel(Stock stock) {
        System.out.println("Search by device model in our stock (with relative ID):");
        for (int i = 0; i < stock.getListaProdotti().size(); i++) {
            System.out.println("> " + i + " < " + stock.getListaProdotti().get(i).getModel());
        }
    }

    public static void bySellPriceRange(Stock stock) throws InputMismatchException {

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
                for (int i = 0; i < stock.getListaProdotti().size(); i++) {
                    if (stock.getListaProdotti().get(i).getSellPrice() >= rangeValues[0] && stock.getListaProdotti().get(i).getSellPrice() <= rangeValues[1]) {
                        System.out.println(stock.getListaProdotti().get(i));
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

    public static void inputRange(Stock stock) {
        boolean isTrue = false;
        while (!isTrue) {

            double[] rangeValues = new double[2];
            Scanner in = new Scanner(System.in);
            System.out.println("Search products by cost range");
            System.out.println("From");
            rangeValues[0] = in.nextDouble();
            System.out.println("to");
            rangeValues[1] = in.nextDouble();
            Arrays.sort(rangeValues);

            byCostPriceRange(stock, rangeValues);

            System.out.println("Would you like to research other products based on cost range? 1)Yes   2)No");
            int stay = in.nextInt();
            if (stay == 2) {
                isTrue = true;
            }
        }
    }

    public static ArrayList<Product> byCostPriceRange(Stock stock, double[] rangeValues) throws InputMismatchException {
        ArrayList<Product> temp = new ArrayList<>();
        try {
            for (int i = 0; i < stock.getListaProdotti().size(); i++) {
                if (stock.getListaProdotti().get(i).getPurchasePrice() >= rangeValues[0] && stock.getListaProdotti().get(i).getPurchasePrice() <= rangeValues[1]) {
                    temp.add(stock.getListaProdotti().get(i)); //La stringa diversa.
                }
            }
            return temp;
        } catch (InputMismatchException e) {
            System.out.println("Please use an integer number (e.g. 250");
        }
        return temp;
    }


    public static void productsView(Stock stock) {
        for (int i = 0; i < stock.getListaProdotti().size(); i++) {
            System.out.println(stock.getListaProdotti().get(i));
        }
    }


    public static String selectedBrand() { //metodo per il byBrand method;
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    public static String answerSelection() { //metodo per il byBrand method
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    public static ArrayList<Product> byBrand(Stock stock) {
        ArrayList<Product> selectedProducts = new ArrayList<>();
        boolean stay = false;
        while (!stay) {

            Set<String> uniqueProducers = new TreeSet<>();
            System.out.println("These are the product's brands available in our stock");
            for (int i = 0; i < stock.getListaProdotti().size(); i++) {
                uniqueProducers.add(stock.getListaProdotti().get(i).getBrand());
            }

            boolean stay2 = false;
            while (!stay2) {
                try {
                    System.out.println(uniqueProducers);
                    System.out.println("For which of these brands would you like to see available products?");
                    String selectedBrand = selectedBrand();

                    if (uniqueProducers.contains(selectedBrand)) {
                        for (int j = 0; j < stock.getListaProdotti().size(); j++) {
                            if (stock.getListaProdotti().get(j).getBrand().equals(selectedBrand)) {
                                System.out.println(stock.getListaProdotti().get(j));
                                stay2 = true;
                            }
                        }
                    } else
                        throw new InputMismatchException("The selected brand is not available.\nYou can try to copy/paste the desired brand name in order to avoid typing mistakes");
                } catch (InputMismatchException e) {
                    System.out.println(e.getMessage());
                }
            }
            boolean stay3 = false;
            while (!stay3) {
                try {
                    System.out.println("Would you like to search other products based on brand?\n1)Yes   2)No");
                    String answer = answerSelection();
                    if (answer.equals("1")) {
                        stay3 = true;
                    } else if (answer.equals("2")) {
                        stay = true;
                        stay3 = true;
                    } else {
                        throw new Exception("Please select a value between 1 or 2.\n");
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return selectedProducts;
    }
}