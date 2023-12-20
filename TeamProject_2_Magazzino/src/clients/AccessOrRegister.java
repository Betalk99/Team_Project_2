package clients;

import database.DbManagement;
import choice.whichOperationCompany;
import choice.whichOperationCustomer;
import database.DbQuery;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AccessOrRegister {

    public static void inputSelect() {
        boolean isTrue = false;
        while (!isTrue) {
            try {
                Scanner in = new Scanner(System.in);
                registerAcces();
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

    public static void registerAcces() {
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
                       accessPage();
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


    public static void accessPage() throws SQLException {
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
                        whatType(c);
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

            stmt.execute(DbQuery.getAddUserCustom(name, surname, email, username, password, numTel));

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

            stmt.execute(DbQuery.getAddUserCompany(name, vat, email, username, password, numTel));

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }



    public static void whatType(Clients c1) {
        if (c1.getType().equals(ClientType.Customer)) {
            whichOperationCustomer.oper(c1);
        } else if (c1.getType().equals(ClientType.Company)) {
            whichOperationCompany.oper(c1);
        }
    }

    public static boolean checkMailDb(String mail)throws SQLException {
        try{
            Statement stmt = DbManagement.makeConnection();
            ResultSet rs = stmt.executeQuery(DbQuery.getCheckMail(mail));

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


    public static void resetPswDb() throws SQLException{
        Scanner in = new Scanner(System.in);
        try{
            Statement stmt = DbManagement.makeConnection();
            System.out.println("Insert Email: ");
            String mail = in.nextLine();
            if(checkMailDb(mail)){
                System.out.println("Insert new password: ");
                String psw = in.nextLine();
                stmt.execute(DbQuery.getResetPsw(mail, psw));
            }else{
                System.out.println("email not present");
            }


        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

}

