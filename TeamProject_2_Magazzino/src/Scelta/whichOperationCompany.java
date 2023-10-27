package Scelta;

import Product.*;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class whichOperationCompany {
    public static void oper(ArrayList<Product> stock) throws InputMismatchException {
        boolean isTrue = false;
        while (!isTrue) {
            try {
                Scanner in = new Scanner(System.in);
                System.out.println("Hello dear user, please select one of the following categories: \n 1-Adding product/s to stock \n 2-Product/s unloading from stock \n 3-Cart creation" +
                        "\n 4-Add product/s to cart \n 5-View all products  \n 6-Search by product type \n 7-Search by brand type \n 8-Search by model type \n 9-Search by purchase price range");
                int category = in.nextInt();
                switch (category) {
                    case 1: //aggiunta prodotto a magazzino
                        break;
                    case 2: //scarico merce da magazzino ?
                        break;
                    case 3: //creazione carrello
                        break;
                    case 4: // aggiunta elemento al carrello
                        break;
                    case 5: // stampare tutti i dispositivi nel magazzino
                        break;
                    case 6: // ricerca per tipo di dispositivo fatta da Antonio Troiano
                        System.out.println(Search.byType(stock));
                        break;
                    case 7: // ricerca per produttore
                        break;
                    case 8: // ricerca per modello
                        Search.byModel(stock);
                        break;
                    case 9: // ricerca per range di prezzo di acquisto
                        break;
                    default:
                        System.out.println("Warning! Unlisted operation!");
                        break;

                }
                System.out.println("Do you want to perform other operation? 1-Yes - 2-No");
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
