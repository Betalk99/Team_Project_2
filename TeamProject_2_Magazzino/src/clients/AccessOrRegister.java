package clients;

import carrello.Cart;
import magazzino.Stock;
import product.Product;
import scelta.whichOperationCompany;
import scelta.whichOperationCustomer;

import java.math.BigInteger;
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

    public static void registerAcces(ListClients list, Stock stock, Cart cart, ArrayList<Product> arrayTemp){
            Scanner in = new Scanner(System.in);
            System.out.println("""
                    Hello dear user, please select one of the following options:\s
                     1) Log In\s
                     2) Register \s
                     3) Reset Password\s
                     """);
            int a = in.nextInt();
            switch (a) {
                case 1:
                    accessPage(list, stock, cart, arrayTemp);
                    break;
                case 2:
                    registerPage(list);
                    break;
                case 3:
                    checkresetPsw(list);
                    break;
                default:
                    break;
            }
    }


    public static void accessPage(ListClients list, Stock stock, Cart cart, ArrayList<Product> arrayTemp){
        Scanner in = new Scanner(System.in);
        boolean isTrue = false;
        while (!isTrue) {
            boolean find = false;
            System.out.println("Insert Email: ");
            String a = in.next();
            System.out.println("Insert Password: ");
            String b = in.next();

            String result = checkCredentials(list, a,b);

            switch (result) {
                case "valid":
                    Clients validClient = getClientByUsername(list, a);
                    whatType(validClient, stock, cart, arrayTemp);
                    break;
                case "email":
                    System.out.println("\n Email error");
                    System.out.println("Please re-enter your credentials \n");
                    break;
                case "password":
                    System.out.println("\n Password error");
                    System.out.println("Please re-enter your credentials \n");
                    break;
                case "notfound":
                    System.out.println("\n Username e/o Password Wrong");
                    System.out.println("Please re-enter your credentials \n");
                    break;
            }
        }
    }
    public static String checkCredentials(ListClients list, String inputEmail, String inputPassword) {
        for (Clients client : list.getList()) {
            if (checkMailAccesPage(client, inputEmail) && checkPswAccesPage(client, inputPassword)) {
                return "valid"; // Le credenziali sono valide
            } else if (!checkMailAccesPage(client, inputEmail)) {
                return "email"; // L'email è errata
            } else if (!checkPswAccesPage(client, inputPassword)) {
                return "password"; // La password è errata
            }
        }
        return "notfound"; // Nessun utente corrisponde all'input
    }

    public static boolean checkMailAccesPage(Clients i, String email){
        return i.getEmail().equals(email);
    }

    public static boolean checkPswAccesPage(Clients i, String psw){
        return i.getPassword().equals(psw);
    }

    public static Clients getClientByUsername(ListClients list, String a){
        Clients c1 = null;
        for(Clients i : list.getList()){
            if(i.getEmail().equals(a)){
                c1 = i;
                return c1;
            }
        }
        return c1;
    }
    public static ListClients registerPage(ListClients list){
        try{
        Scanner in = new Scanner(System.in);
        System.out.println("Welcome to registration, you are Company or Customer? 1/Company - 2/Customer");
        int b = in.nextInt();
        if(b == 1){
            list.getList().add(registerCompany());
        }else if(b == 2){
            list.getList().add(registerCustomer());
        }

        return list;
        }catch (InputMismatchException e){
            System.out.println("Please use a character between 1 or 2");
        }

        return list;
    }

    public static Company registerCompany(){
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


        Company c1 = new Company(ClientType.Company,name,email,pIVA,username,password,numTel);

        return c1;
    }

    public static Customer registerCustomer(){
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

        Customer c1 = new Customer(ClientType.Customer,name,surname,email,username,password,numTel );

        return c1;
    }
    public static void whatType(Clients c1, Stock stock, Cart cart, ArrayList<Product> arrayTemp){
        if(c1.getType().equals(ClientType.Customer)){
            whichOperationCustomer.oper(stock,cart, arrayTemp);
        }else if(c1.getType().equals(ClientType.Company)){
            whichOperationCompany.oper(stock, cart, arrayTemp);
        }
    }
    public static ListClients checkresetPsw(ListClients list){
        Scanner in = new Scanner(System.in);
        System.out.println("To reset your password, give me the email address associated with your account: ");
        String mail = in.next();
        if(checkMail(list, mail)){
            resetPsw(list, mail);
        }else {
            System.out.println("Invalid Mail");
        }
        return list;
    }
    public static boolean checkMail(ListClients list, String mail){
        for (Clients i : list.getList()){
            if(i.getEmail().equals(mail)){
                System.out.println("Correct Mail");
                return true;
            }
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

}
