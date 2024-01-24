package choice;

import cart.cartManagement;
import clients.Clients;
import database.DbCartManagment;
import database.DbManagement;
import product.Product;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class whichOperationCompany {

    public static void stampResult(ArrayList<Product> a){
        System.out.printf("%-8s %-15s %-15s %-25s %-20s %-20s %-20s %-20s","ID", "Type", "Brand", "Model", "Display Size","Storage Cap", "Purchase Price", "Sell Price");
        System.out.println();
        for (Product i : a){
            System.out.printf("%-8s %-15s %-15s %-25s %-20.1f %-20.2f %-20.2f %-20.2f", i.getItemId(), i.getType(), i.getBrand(),
                    i.getModel(), i.getDisplaySize(), i.getStorageCap(), i.getPurchasePrice(),
                    i.getSellPrice());
            System.out.println();
        }
        System.out.println();
    }

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
                         8) Search by product's cost range\s
                         9) Search Customer order list by mail 
                         10) Exit to previous menu""");

                int category = in.nextInt();
                switch (category) {
                    case 1: //aggiunta prodotto a magazzino
                        DbCartManagment.inputAddCompany();
                        break;
                    case 2: //scarico merce da magazzino
                        DbCartManagment.removeCompanyProd();
                        break;
                    case 3: //creazione carrello
                        int idCliente = DbManagement.idClient(c);
                        int idCart = DbManagement.idCart(idCliente);
                        cartManagement.operCar(idCart,idCliente,c);
                        break;
                    case 4: // stampare tutti i dispositivi nel magazzino
                        stampResult(DbManagement.stampStockDb());
                        break;
                    case 5: // ricerca per tipo di dispositivo fatta da Antonio Troiano
                        DbManagement.byType(); //updated on 03.12.23, correctly working based on DB.
                        break;
                    case 6: // ricerca per brand
                        stampResult(DbManagement.byBrand()); //updated on 02.12.23, correctly working based on DB.
                        break;
                    case 7: // ricerca per modello
                        stampResult(DbManagement.byModelDb());
                        break;
                    case 8: // ricerca per range di prezzo di acquisto
                        stampResult(DbManagement.bySellPriceRangeDbCompany() );
                        break;
                    case 9: // ricerca per range di prezzo di acquisto
                        stampResult(DbManagement.customerOrdersEmail());
                        break;
                    case 10: // ritorna al menù precedente
                        isTrue = true;
                        break;
                    default:
                        System.out.println("Unlisted operation");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Please use a character between 1, 2 or 3");
                isTrue = false;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
