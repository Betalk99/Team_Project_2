package database;

import cart.cartManagement;
import choice.whichOperationCustomer;
import order.Orders;
import product.*;

import java.math.BigDecimal;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

import static cart.cartManagement.stampYourCart;

public class DbCartManagment {

    public static ArrayList<Product> cartStatus(int idClient) throws SQLException {
        ArrayList<Product> cart = new ArrayList<>();
        try {
            Statement stmt = DbManagement.makeConnection();
            ResultSet rs = stmt.executeQuery(DbQuery.getCartStatus(idClient));

            while (rs.next()) {
                cart.add(DbManagement.costructProd(rs));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return cart;
    }

    public static void addIdProdDB(int idCart, int idClient) {
        try {
            Statement stmt = DbManagement.makeConnection();
            System.out.println("That's the list of the products currently available in our store.");
            whichOperationCustomer.stampResult(DbManagement.stampStockDb());
            Scanner sc = new Scanner(System.in);
            System.out.println("Select the ID of the product you would like to add to your cart.");
            int idProd = sc.nextInt();
            if (preventAdditionByIdIfCartBigger(createBooleanProductMap(countItemsByProduct(createCartProductList(idCart, idClient)), createMapStockQty(idCart, idClient)), idProd)) {
                stmt.execute(DbQuery.getAddIdProd(idCart,idClient,idProd));
            } else {
                System.out.println("It seems like the product quantity you are trying to add to the cart exceeds that currently available in our stock.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void removeProdById(int idCart, int idClient) {
        try {
            Statement stmt = DbManagement.makeConnection();
            System.out.println("Select the ID of the product you would like to remove from your cart.");
            stampYourCart(DbCartManagment.cartStatus(idClient));
            Scanner sc = new Scanner(System.in);
            int idProd = sc.nextInt();
            stmt.execute(DbQuery.getRemoveProdId(idCart,idClient,idProd));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Boolean preventAdditionByIdIfCartBigger(Map<Product, Boolean> productBooleanMap, int productId) {
        Boolean checker = true;
        for (Map.Entry<Product, Boolean> entry : productBooleanMap.entrySet()) {
            Product product = entry.getKey();
            Boolean stillPossibleToAdd = entry.getValue();
            if (product.getItemId() == productId) {
                checker = stillPossibleToAdd;
                break;
            }
        }
        return checker;
    }

    public static Boolean preventAdditionByNameIfCartBigger(Map<Product, Boolean> productBooleanMap, String brandName, String modelName) {
        Boolean checker = true;
        for (Map.Entry<Product, Boolean> entry : productBooleanMap.entrySet()) {
            Product product = entry.getKey();
            Boolean stillPossibleToAdd = entry.getValue();
            if (Objects.equals(product.getBrand(), brandName) && Objects.equals(product.getModel(), modelName)) {
                checker = stillPossibleToAdd;
                break;
            }
        }
        return checker;
    }

    public static Map<Product, Boolean> createBooleanProductMap(Map<Product, Integer> cartMap, Map<Integer, Integer> stockMap) {
        Map<Product, Boolean> productBooleanMap = new HashMap<>();

        for (Map.Entry<Product, Integer> entry : cartMap.entrySet()) {
            Product cartProduct = entry.getKey();
            Integer cartQty = entry.getValue();

            for (Map.Entry<Integer, Integer> innerEntry : stockMap.entrySet()) {
                Integer stockId = innerEntry.getKey();
                Integer stockQty = innerEntry.getValue();

                if (stockId == cartProduct.getItemId() && cartQty >= stockQty) {
                    productBooleanMap.put(cartProduct, false);
                    break;
                } else if (stockId == cartProduct.getItemId() && cartQty < stockQty) {
                    productBooleanMap.put(cartProduct, true);
                    break;
                }

            }
        }
        return productBooleanMap;
    }

    ////////CHECKOUT BLOCK

    public static void checkOut(int idCart, int idClient) throws SQLException {
        Scanner in = new Scanner(System.in);
        cartManagement.checkCartEmpty(idClient);
        getTotalPrice(idCart, idClient);
        System.out.println("Are you sure you want to proceed to checkout?  YES / NO");

        String ans = in.nextLine();
        boolean stay = true;
        boolean stayTwo = true;
        while (stay) {
            if (ans.equalsIgnoreCase("yes")) {

                if (availabilityCheck(idCart, idClient)) {
                    finalizePurchase(idCart, idClient);
                } else {

                    while (stayTwo) {
                        cartUpdate(idCart, idClient);
                        System.out.println("It seems that while you were finalizing your purchases some of the items from your cart have become unavailable.\nThis is your current cart based on the currently available products.");
                        cartManagement.checkCartEmpty(idClient);
                        DbCartManagment.getTotalPrice(idCart, idClient);
                        System.out.println("Do you still want to proceed to checkout?      YES / NO");
                        String ansTwo = in.nextLine();

                        if (availabilityCheck(idCart, idClient) && ansTwo.equalsIgnoreCase("YES")) {
                            finalizePurchase(idCart, idClient);
                            stayTwo = false;
                        } else if (!availabilityCheck(idCart, idClient) && ansTwo.equalsIgnoreCase("YES")) {
                            stayTwo = true;
                        } else if (ansTwo.equalsIgnoreCase("NO")) {
                            System.out.println("You have decided not to proceed to checkout.");
                            stayTwo = false;
                        } else {
                            System.out.println("Invalid input.");
                            stayTwo = true;
                        }
                    }
                }
                stay = false;
            } else if (ans.equalsIgnoreCase("no")) {
                System.out.println("You have decided not to proceed to checkout.");
                stay = false;
            } else {
                System.out.println("Invalid input.");
                stay = true;
            }
        }
    }

    public static void finalizePurchase(int idCart, int idClient) throws SQLException {
        Statement stmt = DbManagement.makeConnection();

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        ArrayList<Product> cartList = createCartProductList(idCart, idClient);

        if (cartList.isEmpty()) {
            System.out.println("Your cart was empty. As a consequence, no operation has been performed.");
        } else {
            stmt.execute(DbQuery.getCheckout(idCart, dateFormat, date));
            stmt.executeUpdate(DbQuery.getUpdateStatusCart(idCart, idClient));

            for (int i = 0; i < cartList.size(); i++) {
                stmt.executeUpdate(DbQuery.getUpdateQtyStock(cartList, i));
            }

            System.out.println("You have completed your order on: " + dateFormat.format(date));
        }
    }


    public static void cartUpdate(int idCart, int idClient) throws SQLException {
        Statement stmt = DbManagement.makeConnection();

        Map<Product, Integer> cartQuantities = countItemsByProduct(createCartProductList(idCart, idClient));
        Map<Integer, Integer> stockQuantities = createMapStockQty(idCart, idClient);
        Map<Product, Integer> valuesForUpdate = new HashMap<>();

        for (Map.Entry<Product, Integer> entry : cartQuantities.entrySet()) {
            Product product = entry.getKey();
            int cartQty = entry.getValue();

            for (Map.Entry<Integer, Integer> innerEntry : stockQuantities.entrySet()) {
                int stockId = innerEntry.getKey();
                int stockQty = innerEntry.getValue();

                if (product.getItemId() == stockId) {
                    valuesForUpdate.put(product, Math.abs(stockQty - cartQty));
                }
            }
        }
        //Riempimento di una mappa che associ i singoli prodotti alle quantità da ridurre nel carrello.

        for (Map.Entry<Product, Integer> entry : valuesForUpdate.entrySet()) {
            Product product = entry.getKey();
            int valueForUpdate = entry.getValue();

            stmt.executeUpdate(DbQuery.getUpdateCart(idCart, idClient, valueForUpdate, product));
        }
        //Riduzione dei prodotti del carrello in proporzione alle minori quantità disponibili in magazzino.
    }

    public static boolean availabilityCheck(int idCart, int idClient) throws SQLException {
        Map<Integer, Integer> stockMap = createMapStockQty(idCart, idClient);

        boolean checker = true;
        Map<Product, Integer> productCountMap = countItemsByProduct(createCartProductList(idCart, idClient));
        outerLoop:
        for (Map.Entry<Product, Integer> entry : productCountMap.entrySet()) {
            Product product = entry.getKey();
            int cartQty = entry.getValue();

            for (Map.Entry<Integer, Integer> innerEntry : stockMap.entrySet()) {
                int stockId = innerEntry.getKey();
                int stockQty = innerEntry.getValue();

                if (product.getItemId() == stockId && cartQty > stockQty) {
                    checker = false;
                    break outerLoop;
                }
            }

        }
        return checker;
    }

    public static ArrayList<Product> createCartProductList(int idCart, int idClient) throws SQLException {
        Statement stmt = DbManagement.makeConnection();
        ArrayList<Product> cartProductList = new ArrayList<>();
        ResultSet rs = stmt.executeQuery(DbQuery.getCreateCartProductList(idCart, idClient));
        while (rs.next()) {
            cartProductList.add(DbManagement.costructProd(rs));
        }
        return cartProductList;
    }

    public static Map<Product, Integer> countItemsByProduct(ArrayList<Product> cartList) {
        Map<Product, Integer> productCountMap = new HashMap<>();
        for (Product product : cartList) {
            productCountMap.put(product, productCountMap.getOrDefault(product, 0) + 1);
        }
        return productCountMap;
    }

    public static Map<Integer, Integer> createMapStockQty(int idCart, int idClient) throws SQLException {
        Statement stmt = DbManagement.makeConnection();
        ResultSet rsStock = stmt.executeQuery(DbQuery.getCreateMapStockQty(idCart, idClient));

        Map<Integer, Integer> stockMap = new HashMap<>();
        while (rsStock.next()) {
            stockMap.put(rsStock.getInt("idStock"), rsStock.getInt("qty"));
        }
        return stockMap;
    }


    //////////////////////CHECKOUT BLOCK

    public static BigDecimal getTotalPrice(int idCart, int idClient) throws SQLException {
        BigDecimal totalPrice = null;
        Statement stmt = DbManagement.makeConnection();
        String query = "SELECT SUM(product.sellprice) AS totalprice" +
                " FROM cart" +
                " JOIN product ON cart.idProduct = product.id" +
                " WHERE cart.idClient = " + idClient + " AND cart.idCart = " + idCart + " AND cart.status = 1;";
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            totalPrice = rs.getBigDecimal("totalprice");
        }
        if (totalPrice == null) {
            totalPrice = BigDecimal.valueOf(0);
        }
        System.out.println("The total price of the items in your cart is " + totalPrice);
        return totalPrice;
    }

    public static BigDecimal getTotalPriceArr(ArrayList<Product> listProdCart) throws SQLException {
        BigDecimal totalPrice = null;
        for(Product i : listProdCart){
            totalPrice.add(i.getSellPrice());
        }
        if (totalPrice == null) {
            totalPrice = BigDecimal.valueOf(0);
        }
        System.out.println("The total price of the items in your cart is " + totalPrice);
        return totalPrice;
    }


    public static ArrayList<Product> myOrder(int idClient) {
        ArrayList<Product> choiceOrder = new ArrayList<>();
        ArrayList<Orders> listOrder = new ArrayList<>();
        try {
            Scanner in = new Scanner(System.in);

            Statement stmt = DbManagement.makeConnection();
            ResultSet rs = stmt.executeQuery(DbQuery.getMyOrder(idClient));

            while (rs.next()) {
                if (!(listOrder.contains(DbManagement.costructOrder(rs)))) {
                    listOrder.add(DbManagement.costructOrder(rs));
                }
            }

            stampMyOrder(listOrder);

            System.out.println("Which order do you want to display ? Indicate ID ");
            int idOrder = in.nextInt();

            ResultSet rs1 = stmt.executeQuery(DbQuery.getVisualOrderQuery(idClient, idOrder));

            while (rs1.next()) {
                choiceOrder.add(DbManagement.costructProd(rs1));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println(e.getMessage());
        }
        return choiceOrder;
    }

    public static void stampMyOrder(ArrayList<Orders> listOrder) {
        for (Orders i : listOrder) {
            System.out.println(i);
        }
    }

    public static void addProductToCart(int idCart, int idClient) throws SQLException {
        Scanner sc = new Scanner(System.in);
        boolean stay = true;
        while (stay) {
            System.out.println("That's the list of the products currently available in our store.");
            whichOperationCustomer.stampResult(DbManagement.stampStockDb());
            System.out.println("Please write the brand of the product you would like to add to your cart.");
            String brandName = sc.nextLine();
            System.out.println("Please write the model of the product you would like to add to your cart.");
            String modelName = sc.nextLine();
            Statement stmt = DbManagement.makeConnection();

            ResultSet rs = stmt.executeQuery(DbQuery.getSelectBrandModel(brandName, modelName));
            if (!rs.next()) {
                System.out.println("We are sorry. We couldn't find any product matching your request.");
            } else {
                if (preventAdditionByNameIfCartBigger(createBooleanProductMap(countItemsByProduct(createCartProductList(idCart, idClient)), createMapStockQty(idCart, idClient)), brandName, modelName)) {
                    stmt.executeUpdate("INSERT INTO cart (idCart, idProduct, idClient)" +
                            " VALUES ('" + idCart + "', '" + rs.getString("id") + "', '" + idClient + "')");
                    System.out.println("The product was added to your cart.");
                } else {
                    System.out.println("It seems like the product quantity you are trying to add to the cart exceeds that currently available in our stock.");
                }

            }
            boolean stay2 = true;
            while (stay2) {
                System.out.println("Would you like to try to add one more item to your cart? YES / NO");
                String reply = sc.nextLine().toLowerCase();
                switch (reply) {
                    case "yes":
                        stay = true;
                        stay2 = false;
                        break;
                    case "no":
                        stay = false;
                        stay2 = false;
                        break;
                    default:
                        System.out.println("Please insert a valid answer.");
                        stay = true;
                        stay2 = true;
                }
            }
        }
    }

    public static void getEmptyCart(int idClient) {
        try {
            Statement stmt = DbManagement.makeConnection();
            System.out.println("Do you want to get an empty cart?\nIf you are sure press : 1(Yes) or 2(No)");
            stampYourCart(DbCartManagment.cartStatus(idClient));
            Scanner sc = new Scanner(System.in);
            int choice = sc.nextInt();
            if (choice == 1) {
                stmt.execute(DbQuery.getEmptyCart(idClient));
            } else {
                System.out.println("You have chosen not to empty your cart!");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static BigDecimal avarageAmountSpent(int idClient, int idCart) {
        BigDecimal sum = null;
        try {
            Statement stmt = DbManagement.makeConnection();
            System.out.println("Your avarage amount is : ");
            ResultSet rs = stmt.executeQuery(DbQuery.getAverageSpent(idClient, idCart));
            while (rs.next()) {
                sum = BigDecimal.valueOf(rs.getInt("sum"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return sum;
    }

    public static void refreshCart(int idCart, int idClient) throws SQLException {
        if (!DbCartManagment.availabilityCheck(idCart, idClient)) {
            DbCartManagment.cartUpdate(idCart, idClient);
            System.out.println("Dear customer, it appears that some of the items you added to the cart have become unavailable. Here's your cart based on the currently available products.");
            cartManagement.checkCartEmpty(idClient);
            DbCartManagment.getTotalPriceArr(cartStatus(idClient));
        } else {
            System.out.println("No changes have been detected in product availability.");
        }
    }

    // Metodi agginta prodotto al magazzino con relativa quantità

    public static void inputAddCompany() throws SQLException {


        Scanner in = new Scanner(System.in);
        System.out.println("Which brand do you want to add? ");
        String brand = in.nextLine();
        System.out.println("Which model do you want to add? ");
        String model = in.nextLine();
        System.out.println("Which description do you want to add? ");
        String descr = in.nextLine();
        System.out.println("What size of display do you want to add? ");
        double displaySize = in.nextDouble();
        System.out.println("What capacity do you want to add? ");
        int storageCap = in.nextInt();
        System.out.println("What purchase price do you want to add? ");
        BigDecimal purchasePrice = in.nextBigDecimal();
        System.out.println("What sell price do you want to add?");
        BigDecimal sellPrice = in.nextBigDecimal();
        System.out.println("How many products do you want to add");
        int qty = in.nextInt();

        String query = choiceType(brand, model, descr, displaySize, storageCap, purchasePrice, sellPrice);

        insertAddCompany(query, qty);

        System.out.println(" Product added to the warehouse. ");

    }

    public static String choiceType(String brand, String model, String descr, double displaySize, int storageCap, BigDecimal purchasePrice, BigDecimal sellPrice){
        String ret = null;
        try {
            Scanner in = new Scanner(System.in);
            System.out.println("What type of product do you want to select ? 1) tablet - 2) notebook - 3) smartphone");
            int type = in.nextInt();
            switch (type){
                case 1 :
                    ret = DbQuery.getInsertAddCompanyTablet(brand, model, descr, displaySize, storageCap, purchasePrice, sellPrice);
                    break;
                case 2 :
                    ret = DbQuery.getInsertAddCompanyNotebook(brand, model, descr, displaySize, storageCap, purchasePrice, sellPrice);
                    break;
                case 3 :
                    ret = DbQuery.getInsertAddCompanySmartphonne(brand, model, descr, displaySize, storageCap, purchasePrice, sellPrice);
                    break;
            }
        }catch (InputMismatchException e){
            System.out.println(e.getMessage());
        }
        return ret;
    }

    public static void insertAddCompany(String query, int qty){
        try{
            Statement stmt = DbManagement.makeConnection();
            int id = -1;
            stmt.executeUpdate(query);
            ResultSet rs2 = stmt.executeQuery(DbQuery.getProdIdMax());
            while (rs2.next()){
                id = rs2.getInt("lastID");
            }
            System.out.println(id);
            System.out.println(qty);
            stmt.executeUpdate(DbQuery.getInsertNewProdStock(id, qty));


        }catch (SQLException e ){
            System.out.println(e.getMessage());
        }
    }

}
