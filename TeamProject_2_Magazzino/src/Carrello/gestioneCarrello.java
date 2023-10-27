package Carrello;

import Product.Product;

import java.util.*;

public class gestioneCarrello {

    public static ArrayList<Product> creazioneCarrello () {
        ArrayList<Product> carrello = new ArrayList<Product>();
        return carrello;
    }
    public static ArrayList<Product> gestione (ArrayList<Product> carrello, ArrayList<Product> magazzino) {
        ArrayList<Product> arrayTemp = new ArrayList<>();
        arrayTemp.clear();

        arrayTemp = magazzino;

        Scanner in = new Scanner(System.in);
        boolean stay = true;
        while(stay) {
            boolean stay4 = true;
            while (stay4) {
                try {

                    System.out.println("Prego selezionare l'indice del prodotto che intende aggiungere al carrello.");
                    for (int i = 0; i < magazzino.size(); i++) {
                        System.out.println(magazzino.get(i).getProducer() + "" + magazzino.get(i).getModel() + " Indice: " + (i + 1));
                    }
                    int risposta = in.nextInt();
                    if(1 <= risposta && risposta <= magazzino.size()) {
                        carrello.add(arrayTemp.get(risposta-1));
                        arrayTemp.remove(risposta-1);
                        stay4 = false;
                    } else {
                        System.out.println("Non hai inserito un indice valido.");
                        stay4 = true;
                    }

                } catch (InputMismatchException e) {
                    System.out.println("Non hai inserito un indice valido.");
                    stay4 = true;
                }
            }
            boolean stay2 = true;
            while(stay2) {
                System.out.println("Vuoi continuare la tua spesa? Digitare 'Sì' / 'No' ");
                String answer = in.next();
                if (Objects.equals(answer.toLowerCase(), "no")) {
                    stay = false;
                    stay2 = false;
                } else if (Objects.equals(answer.toLowerCase(), "sì") || Objects.equals(answer.toLowerCase(), "si")) {
                    stay = true;
                    stay2 = false;
                } else {
                    System.out.println("Non hai digitato una risposta valida.");
                    stay2 = true;
                }
            }
        }
        boolean stay3 = true;
        while (stay3) {
            try {
                System.out.println("Per procedere con l'acquisto dei beni presenti nel carrello digitare '1'. \n Per svuotare il carrello digitare '2'.");
                int answer = in.nextInt();
                if (answer == 1) {
                    magazzino = arrayTemp;
                    stay3 = false;
                } else if (answer == 2) {
                    carrello.clear();
                    stay3 = false;
                } else {
                    System.out.println("Non hai digitato una risposta valida.");
                    stay3 = true;
                }
            }catch (NoSuchElementException e){
                System.out.println("Inserisci un numero");
            }
        }

        return magazzino;
    }

}
