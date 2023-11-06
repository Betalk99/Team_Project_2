package Clients;


import java.math.BigInteger;

public class Clients {
    private ClientType type;
    private String email;
    private String username;
    private String password;
    private BigInteger numTel;

    public Clients(ClientType type, String email, String username, String password, BigInteger numTel) {
        this.type = type;
        this.email = email;
        this.username = username;
        this.password = password;
        this.numTel = numTel;
    }

    public ClientType getType() {
        return type;
    }

    public void setType(ClientType type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BigInteger getNumTel() {
        return numTel;
    }

    public void setNumTel(BigInteger numTel) {
        this.numTel = numTel;
    }

    @Override
    public String toString() {
        return "Clients{" +
                "type=" + type +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", numTel=" + numTel +
                '}';
    }
}
