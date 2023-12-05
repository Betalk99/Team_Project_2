package choice;

import clients.Clients;
import database.DbManagement;
import java.util.InputMismatchException;
import java.util.Scanner;

public class whichOperationCompany {
    public static void oper(Clients c) throws InputMismatchException {
        boolean isTrue = false;
        while (!isTrue) {
            try {
                Scanner in = new Scanner(System.in);
                System.out.println("""
                        Hello dear user, please select one of the following options:\s
                         1) Add product/s to stock\s
                         2) Remove product/s from stock\s
                         3) Cart Management\s
                         4) View products in stock \s
                         5) Search by product type\s
                         6) Search by product brand\s
                         7) Search by product model\s
                         8) Search by product's cost range""");

                int category = in.nextInt();
                switch (category) {
                    case 1: //aggiunta prodotto a magazzino
//                        addProduct(stock);
                        break;
                    case 2: //scarico merce da magazzino
//                        removeProduct(stock);
                        break;
                    case 3: //creazione carrello
//                        cartManagement.inz(stock,cart,arrayTemp);
                        break;
                    case 4: // stampare tutti i dispositivi nel magazzino
//                        Search.productsView(stock);
                        break;
                    case 5: // ricerca per tipo di dispositivo fatta da Antonio Troiano
                        DbManagement.byType(); //updated on 03.12.23, correctly working based on DB.
                        break;
                    case 6: // ricerca per brand
                        DbManagement.byBrand(); //updated on 02.12.23, correctly working based on DB.
                        break;
                    case 7: // ricerca per modello
//                        Search.inputByModel(stock);
                        break;
                    case 8: // ricerca per range di prezzo di acquisto
//                        Search.inputRange(stock);
                        break;
                    default:
                        System.out.println("Unlisted operation");
                        break;
                }
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

}
