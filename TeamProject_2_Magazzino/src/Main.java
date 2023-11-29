import cart.*;
import product.*;
import stock.*;
import clients.*;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ListClients list = new ListClients();
        list.listBase();

        ArrayList<Product> arrayTemp = new ArrayList<>();

        Stock stock = new Stock();
//        stock.setListaProdotti(ProductBase.baseProd(stock.getListaProdotti()));
        Cart cart = new Cart();

        AccessOrRegister.inputSelect(list,stock,cart,arrayTemp);
    }
}
