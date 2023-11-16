package clients;

import cart.Cart;
import stock.Stock;
import product.Product;
import choice.whichOperationCompany;
import choice.whichOperationCustomer;

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

    public static void registerAcces(ListClients list, Stock stock, Cart cart, ArrayList<Product> arrayTemp) {
        Scanner in = new Scanner(System.in);
        boolean stay = false;
        while (!stay) {
            System.out.println("""
                    Hello dear user, please select one of the following options:\s
                     1) Log In\s
                     2) Register \s
                     3) Reset Password\s
                     """);
            String selection = in.nextLine();
            if (!selection.equals("1") && !selection.equals("2") && !selection.equals("3")) {
                System.out.println("The selected option is not available.\n");
            }
            switch (selection) {
                case "1":
                    accessPage(list, stock, cart, arrayTemp);
                    stay = true;
                    break;
                case "2":
                    registerPage(list);
                    stay = true;
                    break;
                case "3":
                    checkresetPsw(list);
                    stay = true;
                    break;
                default:
                    break;
            }
        }
    }


    public static void accessPage(ListClients list, Stock stock, Cart cart, ArrayList<Product> arrayTemp) {
        Scanner in = new Scanner(System.in);
        boolean isTrue = false;
        while (!isTrue) {
            boolean find = false;
            System.out.println("Insert Email: ");
            String a = in.next();
            System.out.println("Insert Password: ");
            String b = in.next();

            for (Clients i : list.getList()) {
                if (i.getEmail().equals(a) && i.getPassword().equals(b)) {
                    find = true;
                    Clients c1 = i;
                    whatType(c1, stock, cart, arrayTemp);
                }
            }
            if (!find) {
                System.out.println("Username e/o Password Wrong");
            }
        }

    }

    public static ListClients registerPage(ListClients list) {
        Scanner in = new Scanner(System.in);
        System.out.println("Welcome to registration, you are Company or Customer? 1/Company - 2/Customer");
        int b = in.nextInt();
        if (b == 1) {
            list.getList().add(registerCompany());
        } else if (b == 2) {
            list.getList().add(registerCustomer());
        }

        return list;
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
