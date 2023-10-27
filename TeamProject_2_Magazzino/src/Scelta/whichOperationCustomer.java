package Scelta;

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
                System.out.println("Dear customer, please select one of the following categories: \n 1-Cart creation \n 2-Add product to cart \n 3-View all products" +
                        "\n 4-Search by device type \n 5-Search by brand \n 6-Search by model \n 7-Search by price range");
                int category = in.nextInt();
                switch (category) {
                    case 1: //creazione carrello
                        break;
                    case 2: // aggiunta elemento al carrello
                        break;
                    case 3: // stampare tutti i dispositivi nel magazzino
                        break;
                    case 4: // ricerca per tipo di dispositivo fatta da Antonio Troiano
                        System.out.println(Search.byType(stock));
                        break;
                    case 5: // ricerca per produttore
                        break;
                    case 6: // ricerca per modello
                        Search.byModel(stock);
                        break;
                    case 7: // ricerca per range di prezzo (sell price/prezzo di vendita)
                        Search.bySellPriceRange(stock);
                        break;
                    default:
                        System.out.println("Unlisted operation");
                        break;
                }
                System.out.println("Would you like to make other operations? 1) Yes   2) No");
                int stay = in.nextInt();
                if (stay == 2) {
                    isTrue = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Please select a character between 1, 2 or 3");
                isTrue = false;
            }
        }
    }
}
