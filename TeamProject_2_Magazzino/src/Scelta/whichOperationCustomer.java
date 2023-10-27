package Scelta;

import Carrello.cartManagement;
import Product.*;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class whichOperationCustomer {
    public static void oper(ArrayList<Product> stock) throws InputMismatchException {
        boolean isTrue = false;
        while (!isTrue) {
            try {
                Scanner in = new Scanner(System.in);
                System.out.println("""
                        Hello dear customer, please select one of the following options:
                         1) Cart creation\s
                         2) Add product/s to cart\s
                         3) View products\s
                         4) Search by product type\s
                         5) Search by product brand\s
                         6) Search by product model\s
                         7) Search by product's price range""");

                int category = in.nextInt();
                switch (category) {
                    case 1: //creazione carrello
                        cartManagement.creation();
                        break;
                    case 2: // aggiunta elemento al carrello
                        cartManagement.management(cartManagement.creation(), stock);
                        break;
                    case 3: // stampare tutti i dispositivi nel magazzino
                        break;
                    case 4: // ricerca per tipo di dispositivo fatta da Antonio Troiano
                        System.out.println(Search.byType(stock));
                        break;
                    case 5: // ricerca per brand
                        break;
                    case 6: // ricerca per modello
                        Search.byModel(stock);
                        break;
                    case 7: // ricerca per range di prezzo (sell price/prezzo di vendita)
                        Search.bySellPriceRange(stock);
                        break;
                    case 8: // ricerca per range di costo (cost /prezzo di costo)
                        Search.byCostPriceRange(stock);
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
            }
        }
    }
}
