package clients;

import java.math.BigInteger;

public class Customer extends Clients{
    private String name;
    private String surname;
    public Customer(ClientType type,String name, String surname, String email, String username, String password, BigInteger numTel) {
        super(type, email, username, password, numTel);
        this.name = name;
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
