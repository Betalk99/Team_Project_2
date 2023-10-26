package Product;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Ricerca {
    public static ArrayList<Product> ricercaPerTipo(ArrayList<Product> magazzino) throws InputMismatchException{
        ArrayList<Product> dispositiviTipo = new ArrayList<>();
        boolean isTrue = false;
        while (isTrue == false){
            try {
            Scanner in = new Scanner(System.in);

                System.out.println("Salve gentile cliente selezioni una tra le seguenti categorie: \n 1 = tablet \n 2 = notebook \n 3 = smartphone");
                int categoria = in.nextInt();
                switch (categoria) {
                    case 1:
                        for (Product prodotti : magazzino) {
                            if (prodotti.getTipo() == TipoProdotti.tablet) {
                                dispositiviTipo.add(prodotti);
                                isTrue = true;
                            }
                        }
                        break;
                    case 2:
                        for (Product prodotti : magazzino) {
                            if (prodotti.getTipo() == TipoProdotti.notebook) {
                                dispositiviTipo.add(prodotti);
                                isTrue = true;
                            }
                        }
                        break;
                    case 3:
                        for (Product prodotti : magazzino) {
                            if (prodotti.getTipo() == TipoProdotti.smartphone) {
                                dispositiviTipo.add(prodotti);
                                isTrue = true;
                            }
                        }
                        break;
                    default:
                        System.out.println("Questa categoria non c'è l abbiamo ancora siamo poveri!!!");
                        isTrue = false;
                }

            }catch (InputMismatchException e){
                System.out.println("Utilizza un carattere tra 1, 2 o 3");
                isTrue=false;
        }

        }
        return dispositiviTipo;
    }

    public static void ricercaPerModello(ArrayList<Product> magazzino){
         System.out.println("Ricerca per modello dei dispositivi in magazzino con relativo ID : ");
         for(int i=0;i< magazzino.size();i++){
             System.out.println("> " + i + " < " + magazzino.get(i).getModel());
         }
    }

    public static void ricercaPerRangePrezzo(ArrayList<Product> magazzino) throws InputMismatchException{

        boolean isTrue = false;
        while (isTrue == false){
            try {
                Scanner in = new Scanner(System.in);
                System.out.println("Ricerca dispositivi per Range ");
                System.out.println("Da");
                double rangeIni = in.nextDouble();
                System.out.println("A");
                double rangeFin = in.nextDouble();
                boolean inRange = false;
                for(int i=0;i< magazzino.size();i++){
                    if(magazzino.get(i).getSellPrice() > rangeIni && magazzino.get(i).getSellPrice() < rangeFin){
                        System.out.println(magazzino.get(i));
                        inRange = true;
                    }
                }
                if(inRange == false){
                    System.out.println("Non abbiamo prodotti con questo range nel nostro magazzino ");
                }

                System.out.println("Vuoi effettuare altra ricerca per range di prezzo? 1/Si - 2/No");
                int stay = in.nextInt();
                if(stay == 2){
                    isTrue = true;
                }
            }catch (InputMismatchException e){
                System.out.println("Utilizza un numero");
                isTrue = false;
            }

        }
    }





}
