package Clients;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;

public class ListClients {

    private ArrayList<Clients> list = new ArrayList<>();

    public ArrayList<Clients> getList() {
        return list;
    }

    public void setList(ArrayList<Clients> list) {
        this.list = list;
    }

    public ArrayList<Clients> listBase (){
        Customer c1 = new Customer(ClientType.Customer, "Bruno", "Orlandi", "bruno@gmail.com", "brunoorlandi", "ciaociao", BigInteger.valueOf(311123456));
        Customer c2 = new Customer(ClientType.Customer, "Antonio", "Troiano", "antoniot@gmail.com", "antoniotroiano", "ciaociao", BigInteger.valueOf(322123456));
        Customer c3 = new Customer(ClientType.Customer, "Luca", "Di Grigoli", "luca@gmail.com", "lucadigrigoli", "ciaociao", BigInteger.valueOf(333123456));
        Customer c4 = new Customer(ClientType.Customer, "Antonio", "Buonanno", "antoniob@gmail.com", "antoniobuonanno", "ciaociao", BigInteger.valueOf(344123456));
        this.list.add(c1);
        this.list.add(c2);
        this.list.add(c3);
        this.list.add(c4);

        Company cc1 = new Company(ClientType.Company,"PippoParino ", "pippopaperino@pippo.com", "12345678901", "pippopap", "ciaoabc", BigInteger.valueOf(355123456));
        Company cc2 = new Company(ClientType.Company,"Pippo ", "pippo@pippo.com", "21312312453", "pippo", "ciaoabc", BigInteger.valueOf(366123456));
        Company cc3 = new Company(ClientType.Company,"Parino ", "paperino@paperino.com", "56778909821", "pap", "ciaoabc", BigInteger.valueOf(377123456));
        Company cc4 = new Company(ClientType.Company,"PippoParinoabc ", "pippopaperinoabc@pippoabc.com", "01237485940", "pippopapaabc", "ciaoabc", BigInteger.valueOf(388123456));
        this.list.add(cc1);
        this.list.add(cc2);
        this.list.add(cc3);
        this.list.add(cc4);

        return this.list;
    }




    public void stampList(){
        for (Clients i : this.list){
            System.out.println(i);
        }
    }

    @Override
    public String toString() {
        return "ListClients{" +
                "list=" + list +
                '}';
    }
}
