import Carrello.*;
import Product.*;
import Magazzino.*;
import Scelta.*;
import Clients.*;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ListClients list = new ListClients();
        list.listBase();

        ArrayList<Product> arrayTemp = new ArrayList<>();

        Stock stock = new Stock();
        stock.setListaProdotti(ProductBase.baseProd(stock.getListaProdotti()));
        Cart cart = new Cart();

        AccessOrRegister.inputSelect(list,stock,cart,arrayTemp);
    }
}