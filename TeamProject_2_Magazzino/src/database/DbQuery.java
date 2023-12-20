package database;

import clients.Clients;
import product.Product;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DbQuery {
    public static String getSelectType() {
        return "SELECT type FROM product";
    }

    public static String getStampStock() {
        return "SELECT * FROM product" +
                " INNER JOIN stock ON product.id = stock.idStock" +
                " WHERE stock.qty > 0;";
    }

    public static String getByModel(String model) {
        return "SELECT * FROM product" +
                " INNER JOIN stock ON product.id = stock.idStock" +
                " WHERE stock.qty > 0 AND model LIKE '%" + model + "%';";
    }

    public static String getBySellPriceRange(double[] range) {
        return "SELECT * FROM product " +
                " INNER JOIN stock ON product.id = stock.idStock" +
                " WHERE stock.qty > 0 AND product.sellprice > '" + range[0] + "' AND product.sellprice <= '" + range[1] + "';";
    }

    public static String getByTypeProducts(String type) {
        return "SELECT * FROM product" +
                " INNER JOIN stock ON product.id = stock.idStock" +
                " WHERE stock.qty > 0 AND type = '" + type + "';";
    }

    public static String getByBrand(String selectedBrand) {
        return "SELECT * FROM product" +
                " INNER JOIN stock ON product.id = stock.idStock" +
                " WHERE stock.qty > 0 AND brand = '" + selectedBrand + "';";
    }

    public static String getIdClient(Clients c) {
        return "SELECT * FROM client WHERE email = '" + c.getEmail() + "';";
    }

    public static String getIdCart(int idClient) {
        return "SELECT * FROM cart " +
                "WHERE cart.idClient = "+idClient+" " +
                "AND cart.status = 1 ;";
    }

    public static String getIdCartMax() {
        return "SELECT MAX(idCart) AS lastID FROM cart";
    }

    public static String getProdIdMax() {
        return "SELECT MAX(id) AS lastID FROM product";
    }

    public static String getCartStatus(int idClient) {
        return "SELECT * FROM product\n" +
                "JOIN cart ON cart.idProduct = product.id\n" +
                "WHERE cart.idClient = " + idClient + " AND cart.status = 1;";
    }

    public static String getAddIdProd(int idCart, int idClient, int idProd) {
        return "INSERT INTO `projectteam`.`cart`\n" +
                "(idCart,\n" +
                "idProduct,\n" +
                "idClient)\n" +
                "VALUES\n" +
                "(" + idCart + ",\n" +
                " " + idProd + ",\n" +
                " " + idClient + ");\n";
    }

    public static String getRemoveProdId(int idCart, int idClient, int idProd) {
        return "UPDATE cart" +
                " SET cart.status = 0" +
                " WHERE cart.idClient = " + idClient +
                " AND cart.idCart = " + idCart +
                " AND cart.idProduct = " + idProd +
                " AND cart.status = 1" +
                " LIMIT 1;";
    }

    public static String getCheckout(int idCart, DateFormat dateFormat, Date date) {
        return "INSERT INTO orders (idCart, date)" +
                "VALUES (" + idCart + ", '" + dateFormat.format(date) + "' )";
    }

    public static String getUpdateStatusCart(int idCart, int idClient) {
        return "UPDATE cart AS c " +
                "SET c.status = 0 " +
                "WHERE c.idClient = " + idClient + " AND c.idCart = " + idCart + ";";
    }

    public static String getUpdateQtyStock(ArrayList<Product> cartList,int i) {
        return "UPDATE stock" +
                " SET stock.qty = stock.qty - 1" +
                " WHERE " + cartList.get(i).getItemId() + " = stock.idStock;";
    }

    public static String getUpdateCart(int idCart, int idClient, int valueForUpdate, Product product) {
        return "UPDATE cart" +
                " SET cart.status = 0" +
                " WHERE cart.idProduct = " + product.getItemId() +
                " AND cart.idCart = " + idCart +
                " AND cart.idClient = " + idClient +
                " AND cart.status = 1" +
                " LIMIT " + valueForUpdate + ";";
    }

    public static String getCreateCartProductList(int idCart, int idClient) {
        return "SELECT * FROM product" +
                " INNER JOIN cart ON product.id = cart.idProduct" +
                " WHERE cart.idCart = " + idCart + " AND cart.idClient = " + idClient + " AND cart.status = 1;";
    }

    public static String getCreateMapStockQty(int idCart, int idClient) {
        return "SELECT stock.idStock, stock.qty" +
                " FROM stock" +
                " INNER JOIN product ON stock.idStock = product.id" +
                " INNER JOIN cart ON product.id = cart.idProduct" +
                " WHERE cart.idCart = " + idCart +
                " AND idClient = " + idClient +
                " AND cart.status = 1" +
                " GROUP BY stock.idStock ;";
    }

    public static String getMyOrder(int idClient) {
        return "SELECT * FROM orders AS o\n" +
                "JOIN cart AS c ON o.idCart = c.idCart\n" +
                "WHERE c.idClient = " + idClient + "; ";
    }

    public static String getVisualOrderQuery(int idClient, int idOrder) {
        return "SELECT p.id, p.`type`, p.brand, p.model, p.description, p.displaysize, p.storagecap, p.purchaseprice, p.sellprice FROM orders AS o\n" +
                "JOIN cart AS c ON o.idCart = c.idCart\n" +
                "JOIN product AS p ON c.idProduct = p.id\n" +
                "WHERE c.idClient = " + idClient + " AND c.idCart = " + idOrder + ";";
    }

    public static String getSelectBrandModel(String brandName, String modelName) {
        return "SELECT * FROM product" +
                " WHERE brand COLLATE utf8mb4_general_ci = '" + brandName +
                "' AND model COLLATE utf8mb4_general_ci = '" + modelName + "'";
    }

    public static String getEmptyCart(int idClient) {
        return "DELETE FROM `projectteam`.`cart` " +
                "WHERE `cart`.`idClient` = " + idClient + " " +
                "AND `cart`.`status` = 1 ;";
    }

    public static String getAverageSpent(int idClient, int idCart) {
        return "SELECT SUM(p.sellprice) AS sum FROM cart AS c " +
                "JOIN product AS p ON c.idProduct = p.id " +
                "WHERE c.idClient = " + idClient + " AND c.idCart = " + idCart + ";";
    }

    public static String getAddUserCustom(String name, String surname, String email, String username, String password, String numTel){
        return "INSERT INTO client " +
                "(`type`, `name`, `surname`, `email`, `username`,`password`,`phoneNumber`)\n" +
                "VALUES " +
                "('Customer', '" + name + "', '" + surname + "', '" + email + "', '" + username + "', '"+ password + "', '" + numTel +"');";
    }

    public static String getAddUserCompany(String name, String vat, String email, String username, String password, String numTel){
        return "INSERT INTO client " +
                "(`type`, `name`, `email`, `username`,`password`,`phoneNumber`, `vat`)\n" +
                "VALUES " +
                "('Company', '" + name + "', '" + email + "', '" + username + "', '" + password + "', '"+ numTel + "', '" + vat +"');";
    }

    public static String getCheckMail(String mail){
        return "SELECT * FROM client WHERE email = '" + mail + "';";
    }

    public static String getResetPsw(String mail, String psw){
        return "UPDATE client SET password = '" + psw + "' WHERE email = '" + mail + "';";
    }

    public static String getInsertAddCompanyTablet(String brand, String model, String descr, double displaySize, int storageCap, BigDecimal purchasePrice, BigDecimal sellPrice){
        return "INSERT INTO `projectteam`.`product` (`type`, `brand`, `model`, `description`, `displaysize`, `storagecap`, `purchaseprice`, `sellprice`) " +
                "VALUES ('tablet', '" + brand + "', '" + model + "', '" + descr + "', " + displaySize  + ", " + storageCap  + ", " + purchasePrice   + ", " + sellPrice + ");";
    }
    public static String getInsertAddCompanyNotebook(String brand, String model, String descr, double displaySize, int storageCap, BigDecimal purchasePrice, BigDecimal sellPrice){
        return "INSERT INTO `projectteam`.`product` (`type`, `brand`, `model`, `description`, `displaysize`, `storagecap`, `purchaseprice`, `sellprice`) " +
                "VALUES ('notebook', '" + brand + "', '" + model + "', '" + descr + "', " + displaySize  + ", " + storageCap  + ", " + purchasePrice   + ", " + sellPrice + ");";
    }
    public static String getInsertAddCompanySmartphonne(String brand, String model, String descr, double displaySize, int storageCap, BigDecimal purchasePrice, BigDecimal sellPrice){
        return "INSERT INTO `projectteam`.`product` (`type`, `brand`, `model`, `description`, `displaysize`, `storagecap`, `purchaseprice`, `sellprice`) " +
                "VALUES ('smartphone', '" + brand + "', '" + model + "', '" + descr + "', " + displaySize  + ", " + storageCap  + ", " + purchasePrice   + ", " + sellPrice + ");";
    }

    public static String getInsertNewProdStock(int id, int qty){
        return "INSERT INTO `projectteam`.`stock` (`idStock`, `qty`) " +
                "VALUES ('" + id + "','" + qty + "');";
    }







}
