package Controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Agency implements Serializable{
    
    public HashMap<String, Person> clients;
    public HashMap<String, Person> costumers;
    private static int auto_increment_id = -1;
    private String code;

    
    public Agency(String code){
        this.clients = new HashMap<>();
        this.costumers = new HashMap<>();
        this.code = code;
    }

    public HashMap<String, Person> showClients(){
        return this.clients;
    }
    public String getCode(){
        return this.code;
    }
    

    public boolean appendCostumer(Costumer person){
        for(Person costumer : costumers.values()){
            Costumer costumer2 = (Costumer) costumer;
            if(costumer2.getEmail().equals(person.getEmail())){ // prevejo que isso nunca vai acontecer, uma vez que o configure controla tudo
                System.out.println("Não pode criar uma conta com email já catalogado!");
                return false;
            }
        }
        this.costumers.put(person.getEmail(), person); //Funcionário salvo
        return true;
    }

    public Costumer checkCostumerEmail(String email){        
        for (Person costumer2 : costumers.values()) {
            Costumer costumer3 = (Costumer) costumer2;
            if(email.equals(costumer3.getEmail()))
                return costumer3;
        }
        return null;
    }

    public Costumer remove(String email){
        Costumer costumer2 = checkCostumerEmail(email);
        if(!(costumer2 instanceof Costumer))
            return null;
        return (Costumer) costumers.remove(costumer2.getEmail());
    }

    
    public String toString(){
        StringBuffer st = new StringBuffer();
        st.append("Agency: ");
        st.append(code);
        st.append(" id: ");
        if(costumers.size() > 0){
            st.append("\n");
            st.append("======== USER ========");
            for (Person person : costumers.values()) {
                Costumer costumer = ((Costumer)person);
                st.append("\n");
                st.append(costumer);
               
            }
        }
        return st.toString();
    }
    
}
