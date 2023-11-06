package Clients;

import java.math.BigInteger;

public class Company extends Clients{

    private String name;
    private String pIva;

    public Company(ClientType type, String name,String email,String pIva, String username, String password, BigInteger numTel) {
        super(type, email, username, password, numTel);
        this.name = name;
        this.pIva = pIva;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getpIva() {
        return pIva;
    }

    public void setpIva(String pIva) {
        this.pIva = pIva;
    }
}
