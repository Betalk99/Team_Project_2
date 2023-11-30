package clients;

import cart.Cart;
import database.DbManagement;
import stock.Stock;
import product.Product;
import choice.whichOperationCompany;
import choice.whichOperationCustomer;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AccessOrRegister {

    public static void inputSelect(ListClients list, Stock stock, Cart cart, ArrayList<Product> arrayTemp) {
        boolean isTrue = false;
        while (!isTrue) {
            try {
                Scanner in = new Scanner(System.in);
                registerAcces(list, stock, cart, arrayTemp);
                System.out.println("Would you like to make other researches? 1)Yes   2)No");
                int stay = in.nextInt();
                if (stay == 2) {
                    isTrue = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Please use a character between 1, 2 or 3");
                isTrue = false;
            }
        }
    }

    public static void registerAcces(ListClients list, Stock stock, Cart cart, ArrayList<Product> arrayTemp) {
       try {
           Scanner in = new Scanner(System.in);
           boolean stay = false;
           while (!stay) {
               System.out.println("""
                    Hello dear user, please select one of the following options:\s
                     1) Log In\s
                     2) Register \s
                     3) Reset Password\s
                     """);
               int selection = in.nextInt();
               switch (selection) {
                   case 1 :
                       accessPage(list, stock, cart, arrayTemp);
                       stay = true;
                       break;
                   case 2 :
                       registerPage();
                       stay = true;
                       break;
                   case 3 :
                       resetPswDb();
                       stay = true;
                       break;
                   default:
                       System.out.println("The selected option is not available.\n");
                       break;
               }
           }
       }catch (InputMismatchException e){
           System.out.println("Please use a character between 1 or 3");
       } catch (SQLException e) {
           throw new RuntimeException(e);
       }

    }


    public static void accessPage(ListClients list, Stock stock, Cart cart, ArrayList<Product> arrayTemp) throws SQLException {
        Scanner in = new Scanner(System.in);
        boolean isTrue = false;
        boolean isOk = false;
        while (!isTrue) {
            boolean find = false;
            System.out.println("Insert Email: ");
            String mail = in.next();
            if (checkMailDb(mail)) {
                Clients c = getClientByUsernameDb(mail);
                while(!isOk) {
                System.out.println("Insert Password: ");
                String b = in.next();
                    if (checkPassword(b, c)) {
                        whatType(c, stock, cart, arrayTemp);
                        isOk = true;
                    } else {
                        System.out.println("\nPassword error");
                        System.out.println("Please re-enter your password \n");
                    }
                }
            } else {
                System.out.println("\nEmail error");
                System.out.println("Please re-enter your email \n");
            }
        }
    }


    public static Clients getClientByUsernameDb(String mail){

        try{
            Statement stmt = DbManagement.makeConnection();
            ResultSet rs = stmt.executeQuery("SELECT * FROM client WHERE email = '" + mail + "';");

            while (rs.next()){
                String checkMail = rs.getString("email");
                String typeClient = rs.getString("type");
                if(checkMail.equals(mail)){
                    if(typeClient.equals("Customer")){
                        Customer c1 = new Customer(ClientType.valueOf(rs.getString("type")), rs.getString("name"), rs.getString("surname"), rs.getString("email"), rs.getString("username"),rs.getString("password"), BigInteger.valueOf(rs.getInt("phoneNumber")));
                        return c1;
                    }else {
                        Company cc1 = new Company(ClientType.valueOf(rs.getString("type")), rs.getString("name"), rs.getString("email"), rs.getString("vat"), rs.getString("username"), rs.getString("password"), BigInteger.valueOf(rs.getInt("phoneNumber")));
                        return cc1;
                    }
                }
            }

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }




    public static void registerPage() {
        try{
        Scanner in = new Scanner(System.in);
        System.out.println("Welcome to registration, you are Company or Customer? 1/Company - 2/Customer");
        int b = in.nextInt();
        if (b == 1) {
            registerCompanyDb();
        } else if (b == 2) {
            registerCustomerDb();
        }

        }catch (InputMismatchException e){
            System.out.println("Please use a character between 1 or 2");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static Company registerCompany() {
        Scanner in = new Scanner(System.in);
        System.out.println("Insert name Company: ");
        String name = in.next();
        System.out.println("Insert email Company: ");
        String email = in.next();
        System.out.println("Insert pIVA Company: ");
        String pIVA = in.next();
        System.out.println("Insert username Company: ");
        String username = in.next();
        System.out.println("Insert password Company: ");
        String password = in.next();
        System.out.println("Insert number phone Company: ");
        BigInteger numTel = in.nextBigInteger();


        Company c1 = new Company(ClientType.Company, name, email, pIVA, username, password, numTel);

        return c1;
    }

    public static Customer registerCustomer() {
        Scanner in = new Scanner(System.in);
        System.out.println("Insert name Customer: ");
        String name = in.next();
        System.out.println("Insert surname Customer: ");
        String surname = in.next();
        System.out.println("Insert email Customer: ");
        String email = in.next();
        System.out.println("Insert username Customer: ");
        String username = in.next();
        System.out.println("Insert password Customer: ");
        String password = in.next();
        System.out.println("Insert number phone Customer: ");
        BigInteger numTel = in.nextBigInteger();

        Customer c1 = new Customer(ClientType.Customer, name, surname, email, username, password, numTel);

        return c1;
    }

    public static void registerCustomerDb() throws SQLException{
        try{

            Statement stmt = DbManagement.makeConnection();

            Scanner in = new Scanner(System.in);
            System.out.println("Insert name Customer: ");
            String name = in.nextLine();
            System.out.println("Insert surname Customer: ");
            String surname = in.nextLine();
            System.out.println("Insert email Customer: ");
            String email = in.nextLine();
            System.out.println("Insert username Customer: ");
            String username = in.nextLine();
            System.out.println("Insert password Customer: ");
            String password = in.nextLine();
            System.out.println("Insert number phone Customer: ");
            String numTel = in.nextLine();

            String userCust =
                    "INSERT INTO client " +
                            "(`type`, `name`, `surname`, `email`, `username`,`password`,`phoneNumber`)\n" +
                            "VALUES " +
                            "('Customer', '" + name + "', '" + surname + "', '" + email + "', '" + username + "', '"+ password + "', '" + numTel +"');";

            stmt.execute(userCust);


        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public static void registerCompanyDb() throws SQLException{
        try{

            Statement stmt = DbManagement.makeConnection();

            Scanner in = new Scanner(System.in);
            System.out.println("Insert name Company: ");
            String name = in.nextLine();
            System.out.println("Insert email Company: ");
            String email = in.nextLine();
            System.out.println("Insert username Company: ");
            String username = in.nextLine();
            System.out.println("Insert password Company: ");
            String password = in.nextLine();
            System.out.println("Insert numTel Company: ");
            String numTel = in.nextLine();
            System.out.println("Insert vat Company: ");
            String vat = in.nextLine();

            String userCust =
                    "INSERT INTO client " +
                            "(`type`, `name`, `email`, `username`,`password`,`phoneNumber`, `vat`)\n" +
                            "VALUES " +
                            "('Company', '" + name + "', '" + email + "', '" + username + "', '" + password + "', '"+ numTel + "', '" + vat +"');";

            stmt.execute(userCust);


        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }



    public static void whatType(Clients c1, Stock stock, Cart cart, ArrayList<Product> arrayTemp) {
        if (c1.getType().equals(ClientType.Customer)) {
            whichOperationCustomer.oper(stock, cart, arrayTemp);
        } else if (c1.getType().equals(ClientType.Company)) {
            whichOperationCompany.oper(stock, cart, arrayTemp);
        }
    }

    public static ListClients checkresetPsw(ListClients list) {
        Scanner in = new Scanner(System.in);
        System.out.println("To reset your password, give me the email address associated with your account: ");
        String mail = in.next();
        if (checkMail(list, mail)) {
            resetPsw(list, mail);
        } else {
            System.out.println("Invalid Mail");
        }
        return list;
    }

    public static boolean checkMail(ListClients list, String mail) {
        for (Clients i : list.getList()) {
            if (i.getEmail().equals(mail)) {
                System.out.println("Correct Mail");
                return true;
            }
        }
        return false;
    }


    public static boolean checkMailDb(String mail)throws SQLException {
        try{
            Statement stmt = DbManagement.makeConnection();
            ResultSet rs = stmt.executeQuery("SELECT * FROM client WHERE email = '" + mail + "';");

            while (rs.next()){
                String checkMail = rs.getString("email");
                if(checkMail.equals(mail)){
                    System.out.println("Correct Mail");
                    return true;
                }
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }




    public static boolean checkPassword(String psw, Clients c) {
            if (c.getPassword().equals(psw)) {
                System.out.println("Correct Password");
                return true;
            }

        return false;
    }



    public static ListClients resetPsw(ListClients list, String mail) {
        Scanner in = new Scanner(System.in);
        for (Clients i : list.getList()) {
            if (i.getEmail().equals(mail)) {
                System.out.println("Insert new password: ");
                String psw = in.next();
                i.setPassword(psw);
            }
        }
        return list;
    }


    public static void resetPswDb() throws SQLException{
        Scanner in = new Scanner(System.in);
        try{
            Statement stmt = DbManagement.makeConnection();
            System.out.println("Insert Email: ");
            String mail = in.nextLine();
            if(checkMailDb(mail)){
                System.out.println("Insert new password: ");
                String psw = in.nextLine();
                String resetPsw = "UPDATE client SET password = '" + psw + "' WHERE email = '" + mail + "';";
                stmt.execute(resetPsw);
            }else{
                System.out.println("email not present");
            }


        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

}

