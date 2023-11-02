package Scelta;

import Carrello.cartManagement;
import Product.*;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class whichOperationCompany {
    public static void oper(ArrayList<Product> stock,ArrayList<Product> cart) throws InputMismatchException {
        boolean isTrue = false;
        while (!isTrue) {
            try {
                Scanner in = new Scanner(System.in);
                System.out.println("""
                        Hello dear user, please select one of the following options:\s
                         1) Add product/s to stock\s
                         2) Remove product/s from stock\s
                         3) Cart creation\s
                         4) Add product/s to cart\s
                         5) View products \s
                         6) Search by product type\s
                         7) Search by product brand\s
                         8) Search by product model\s
                         9) Search by product's cost range""");

                int category = in.nextInt();
                switch (category) {
                    case 1: //aggiunta prodotto a magazzino
                        break;
                    case 2: //scarico merce da magazzino
                        break;
                    case 3: //creazione carrello
                        cartManagement.operCar(stock,cart);
                        break;
                    case 4: // aggiunta elemento al carrello
                        cartManagement.management(cart, stock);
                        break;
                    case 5: // stampare tutti i dispositivi nel magazzino
                        Search.productsView(stock);
                        break;
                    case 6: // ricerca per tipo di dispositivo fatta da Antonio Troiano
                        System.out.println(Search.byType(stock));
                        break;
                    case 7: // ricerca per brand
                            Search.byBrand(stock);
                        break;
                    case 8: // ricerca per modello
                        Search.byModel(stock);
                        break;
                    case 9: // ricerca per range di prezzo di acquisto
                        Search.bySellPriceRange(stock);
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
