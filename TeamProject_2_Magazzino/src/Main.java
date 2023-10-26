import Product.*;
import Magazzino.*;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Product> magazzino = new ArrayList<>();  // arraylist magazzino
        Stock mag = new Stock(magazzino);

        ProductBase.baseProd(magazzino); //richiamare oggetti precaricati arraylist magazzino

        System.out.println(Ricerca.ricercaPerTipo(magazzino)); //ricerca per tipo
        Ricerca.ricercaPerModello(magazzino); //ricerca per modello
        Ricerca.ricercaPerRangePrezzo(magazzino); //ricerca per range di prezzo (sell price/prezzo di vendita)
    }
}
