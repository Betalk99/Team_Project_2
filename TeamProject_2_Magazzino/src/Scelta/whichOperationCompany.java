package Scelta;

import Carrello.*;
import Magazzino.*;
import Product.*;
import Clients.*;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class whichOperationCompany {
    public static void oper(Stock stock, Cart cart, ArrayList<Product> arrayTemp) throws InputMismatchException {
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
                        addProduct(stock);
                        break;
                    case 2: //scarico merce da magazzino
                        removeProduct(stock);
                        break;
                    case 3: //creazione carrello
                        cartManagement.inz(stock,cart,arrayTemp);
                        break;
                    case 4: // stampare tutti i dispositivi nel magazzino
                        Search.productsView(stock);
                        break;
                    case 5: // ricerca per tipo di dispositivo fatta da Antonio Troiano
                        System.out.println(Search.byType(stock));
                        break;
                    case 6: // ricerca per brand
                            Search.byBrand(stock);
                        break;
                    case 7: // ricerca per modello
                        Search.inputByModel(stock);
                        break;
                    case 8: // ricerca per range di prezzo di acquisto
                        Search.inputRange(stock);
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

    public static Stock addProduct(Stock stock){
        boolean stay = false;
        int x;
        int y;
        Scanner in = new Scanner(System.in);

        try {
            while (stay == false) {

                System.out.println("Which product do you want to insert ? \n 1-Tablet \n 2-Smartphone \n 3-Notebook \n");
                x = in.nextInt();

                switch (x) {
                    case 1:
                        Tablet tablet = Tablet.inputKeyboard();
//                        stock.add(tablet);
                        stock.getListaProdotti().add(tablet);
                        break;
                    case 2:
                        Smartphone smartphone = Smartphone.inputKeyboard();
//                        stock.add(smartphone);
                        stock.getListaProdotti().add(smartphone);
                        break;
                    case 3:
                        Notebook notebook = Notebook.inputKeyboard();
//                        stock.add(notebook);
                        stock.getListaProdotti().add(notebook);
                        break;
                    default:
                        System.out.println("Error ");
                        break;

                }
                System.out.println("You want to add another product? 1-Yes / 2-No");
                y = in.nextInt();
                if (y == 2) {
                    stay = true;
                }
            }
        }catch (InputMismatchException e){
        System.out.println("Please use a character between 1, 2 or 3");
        stay = false;
    }

        return stock;

    }


    public static Stock removeProduct (Stock stock){

        boolean stay = false;
        int x;
        int y;
        Scanner in = new Scanner(System.in);
        int cout=1;
        try {
            while (stay == false) {
                for (Product i : stock.getListaProdotti()) {
                    System.out.println(i);
                    System.out.println(cout++);
                }
                System.out.println("Which product do you want to delete ? \n Indicate id");
                x = in.nextInt();

                stock.getListaProdotti().remove(x - 1);

                System.out.println("You want to remove another product? 1-Yes / 2-No");
                y = in.nextInt();
                if (y == 2) {
                    stay = true;
                }
            }
        }catch (InputMismatchException e){
            System.out.println("Please use a character between 1, 2 or 3");
            stay = false;
        }
        return stock;
    }







}
