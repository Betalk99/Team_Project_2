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





    @Override
    public String toString() {
        return "Ricerca{}";
    }
}
