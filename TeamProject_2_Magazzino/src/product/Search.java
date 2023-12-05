package product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import database.DbManagement;
import stock.*;

public class Search {
    public static ArrayList<Product> byType() throws InputMismatchException {
        ArrayList<Product> devicesByType = new ArrayList<>();
        TreeSet<String> typesFromDb = new TreeSet<>();
        boolean isTrue = false;
        while (!isTrue) {
            try {
                Scanner in = new Scanner(System.in);
                System.out.println("Please select one of the following types:");
                DbManagement.catchTypesFromDb(typesFromDb);
                String type = in.nextLine();
                if (typesFromDb.contains(type)) {
                    DbManagement.byTypeProductsFromDb(type, devicesByType);
                    isTrue = true;
                } else {
                    System.out.println("This type does not exists.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please select an existing type (e.g. Tablet)");
            }
        }
        return devicesByType;
    }



    public static ArrayList<Product> byBrand() {
        Scanner sc = new Scanner(System.in);
        boolean stay = false;
        while (!stay) {
            boolean stay2 = false;
            while (!stay2) {
                try {
                    ArrayList<Product> selectedProducts = new ArrayList<>();
                    System.out.println("Please write the brand for which you want to search for products");
                    String selectedBrand = sc.nextLine();

                    try {
                        Statement stmt = DbManagement.makeConnection();
                        String query = "SELECT * FROM product WHERE brand = '" + selectedBrand + "';";
                        ResultSet rs = stmt.executeQuery(query);

                        while (rs.next()) {
                            selectedProducts.add(DbManagement.costructProd(rs));
                        }
                        if (selectedProducts.isEmpty()) {
                            throw new InputMismatchException("The selected brand is not available.\n");
                        } else {
                            return selectedProducts;
                        }
                    } catch (SQLException e) {
                        e.getStackTrace();
                    }
                    stay2 = true;
                } catch (InputMismatchException e) {
                    System.out.println(e.getMessage());
                }
            }
            boolean stay3 = false;
            while (!stay3) {
                try {
                    System.out.println("Would you like to search other products based on brand?\n1)Yes   2)No");
                    String answer = sc.nextLine();
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
        return null;
    }
}