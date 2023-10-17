package Carrello;

import Product.Product;

import java.util.ArrayList;
public class Cart {
        public void addItem (Product product1, ArrayList<Product> carrello, ArrayList<Product> magazzino) {
            carrello.add(product1);
            magazzino.remove(product1);
        }
        // Metodo per l'aggiunta dei prodotti al carrello e la contestuale
        // rimozione dal magazzino
        public void removeItem (Product product1, ArrayList<Product> carrello, ArrayList<Product> magazzino) {
            if(carrello.isEmpty()) {
                System.out.println("Il carrello è vuoto.");
            } else {
                carrello.remove(product1);
                magazzino.add(product1);
            }
        }
        // Metodo per la rimozione dei prodotti dal carrello e la contestuale
        // aggiunta dei medesimi al magazzino.
        public static double totalPrice (ArrayList<Product> cart) {
            double priceSum = 0;
            for(int i = 0; i < cart.size(); i++) {
                priceSum += cart.get(i).getSellPrice();
            }
            return priceSum;
        }
        // Metodo per il calcolo del prezzo complessivo dei prodotti aggiunti
        // al carrello

    }