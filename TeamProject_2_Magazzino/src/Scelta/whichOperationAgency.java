package Scelta;
import Carrello.gestioneCarrello;
import Magazzino.*;
import Product.*;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class whichOperationAgency {
    public static void oper(ArrayList<Product> magazzino) throws InputMismatchException {
        boolean isTrue = false;
        while (isTrue == false){
            try {
                Scanner in = new Scanner(System.in);
                System.out.println("Salve gentile cliente selezioni una tra le seguenti categorie: \n 1-Aggiunta prodotto a magazzino \n 2-Scarico merce da magazzino \n 3-Creazione carrello " +
                        "\n 4-Aggiunta elemento al carrello \n 5-Stampare tutti dispositivi  \n 6-Ricerca per tipo \n 7-Ricerca produttore \n 8-Ricerca modello \n 9-Ricerca per range di prezzo di acquisto");
                int categoria = in.nextInt();
                switch (categoria){
                    case 1: //aggiunta prodotto a magazzino
                        break;
                    case 2: //scarico merce da magazzino ?
                        break;
                    case 3: //creazione carrello
                        gestioneCarrello.creazioneCarrello();
                        break;
                    case 4: // aggiunta elemento al carrello
                        gestioneCarrello.gestione(gestioneCarrello.creazioneCarrello(), magazzino);
                        break;
                    case 5: // stampare tutti i dispositivi nel magazzino
                        break;
                    case 6: // ricerca per tipo di dispositivo fatta da Antonio Troiano
                        System.out.println(Ricerca.ricercaPerTipo(magazzino));
                        break;
                    case 7: // ricerca per produttore
                        break;
                    case 8: // ricerca per modello
                        Ricerca.ricercaPerModello(magazzino);
                        break;
                    case 9: // ricerca per range di prezzo di acquisto
                        Ricerca.ricercaPerRangePrezzoAcquisto(magazzino);
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
