package choice;

import cart.cartManagement;
import clients.Clients;
import database.DbManagement;

import product.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class whichOperationCustomer {


    public static void stampResult(ArrayList<Product> a){
        for (Product i : a){
            System.out.println(i);
        }
    }

    public static void oper(Clients c) throws InputMismatchException {



        boolean isTrue = false;
        while (!isTrue) {
            try {
                Scanner in = new Scanner(System.in);
                System.out.println("""

                        Hello dear customer, please select one of the following options:
                         1) Cart Management\s
                         2) View products\s
                         3) Search by product type\s
                         4) Search by product brand\s
                         5) Search by product model\s
                         6) Search by product's price range""");


                int category = in.nextInt();
                switch (category) {
                    case 1: //gestisci il tuo carello
                        int idCliente = DbManagement.idClient(c);
                        int idCart = DbManagement.idCart(idCliente);
                        cartManagement.operCar(idCart,idCliente);
                        break;
                    case 2: // stampare tutti i dispositivi nel magazzino
                        stampResult(DbManagement.stampStockDb());
                        break;
                    case 3: // ricerca per tipo di dispositivo
                        DbManagement.byType();//updated on 03.12.23, correctly working based on DB.
                        break;
                    case 4: // ricerca per brand
                        stampResult(DbManagement.byBrand()); //updated on 02.12.23, correctly working based on DB.
                        break;
                    case 5: // ricerca per modello
                        stampResult(DbManagement.byModelDb());
                        break;
                    case 6: // ricerca per range di prezzo (sell price/prezzo di vendita)
                        stampResult(DbManagement.bySellPriceRangeDb());
                        break;
                    default:
                        System.out.println("Unlisted operation");
                        break;
                }
                System.out.println("Would you like to do other operations? 1)Yes   2)No");
                int stay = in.nextInt();
                if (stay == 2) {
                    isTrue = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Please use a character between 1, 2 or 3");
                isTrue = false;
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
