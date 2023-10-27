import Product.*;
import Magazzino.*;
import Scelta.*;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        ArrayList<Product> magazzino = new ArrayList<>();  // arraylist magazzino
        Stock mag = new Stock(magazzino);
        ProductBase.baseProd(magazzino); //richiamare oggetti precaricati arraylist magazzino
        ArrayList<Product> carrello = new ArrayList<>();



        usoGestionaleScelta(magazzino); // richiamo funzione per accedere alla scelta

    }

    public static void usoGestionaleScelta(ArrayList<Product> magazzino)throws InputMismatchException{
        boolean isTrue = false;
        while (isTrue == false) {
            try {
                Scanner in = new Scanner(System.in);
                System.out.println("Salve, chi desidera utilizzare questo gestionale: \n1-Utente \n2-Azienda ");
                int a = in.nextInt();
                if(a == 1){
                    whichOperationUser.oper(magazzino);
                }else if(a == 2){
                    whichOperationAgency.oper(magazzino);
                }
                System.out.println("Vuoi effettuare altra ricerca? 1-Si - 2-No");
                int stay = in.nextInt();
                if(stay == 2){
                    isTrue = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Utilizza un numero tra 1, 2");
                isTrue = false;
            }

        }

    }


}
