package Scelta;
import Magazzino.*;
import Product.*;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class whichOperationUser {
    public static void oper(ArrayList<Product> magazzino) throws InputMismatchException {
        boolean isTrue = false;
        while (isTrue == false){
            try {
                Scanner in = new Scanner(System.in);
                System.out.println("Salve gentile cliente selezioni una tra le seguenti categorie: \n 1-Creazione carrello \n 2-Aggiunta elemento al carrello \n 3-Stampare dispositivi " +
                        "\n 4-Ricerca per tipo di dispositivo \n 5-Ricerca produttore \n 6-Ricerca modello \n 7-Ricerca per range di prezzo");
                int categoria = in.nextInt();
                switch (categoria){
                    case 1: //creazione carrello
                        break;
                    case 2: // aggiunta elemento al carrello
                        break;
                    case 3: // stampare tutti i dispositivi nel magazzino
                        break;
                    case 4: // ricerca per tipo di dispositivo fatta da Antonio Troiano
                            System.out.println(Ricerca.ricercaPerTipo(magazzino));
                        break;
                    case 5: // ricerca per produttore
                        break;
                    case 6: // ricerca per modello
                            Ricerca.ricercaPerModello(magazzino);
                        break;
                    case 7: // ricerca per range di prezzo (sell price/prezzo di vendita)
                            Ricerca.ricercaPerRangePrezzo(magazzino);
                        break;
                    default:
                        System.out.println("Operazione non in elenco");
                        break;

                }
                System.out.println("Vuoi effettuare altra operazione ? 1-Si - 2-No");
                int stay = in.nextInt();
                if(stay == 2){
                    isTrue = true;
                }


            }catch (InputMismatchException e){
                System.out.println("Utilizza un carattere tra 1, 2 o 3");
                isTrue=false;
            }

        }



    }




}
