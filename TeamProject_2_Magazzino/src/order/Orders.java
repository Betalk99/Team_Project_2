package order;

import java.util.Objects;

public class Orders {

    private int idCart;
    private String date;

    public Orders(int idCart, String date) {
        this.idCart = idCart;
        this.date = date;
    }

    public int getIdCart() {
        return idCart;
    }

    public void setIdCart(int idCart) {
        this.idCart = idCart;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Orders orders = (Orders) o;
        return idCart == orders.idCart && Objects.equals(date, orders.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCart, date);
    }

    @Override
    public String toString() {
        return "Orders{" +
                "idCart=" + idCart +
                ", date='" + date + '\'' +
                '}';
    }
}
