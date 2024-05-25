package Controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Agency implements Serializable{
    
    public HashMap<String, Person> clients;
    public HashMap<String, Person> employeds;
    private static int auto_increment_id = -1;
    private String code;

    
    public Agency(String code){
        this.clients = new HashMap<>();
        this.employeds = new HashMap<>();
        this.code = code;
    }

    public HashMap<String, Person> showClients(){
        return this.clients;
    }
    public String getCode(){
        return this.code;
    }
    

    public boolean appendEmployed(Employed person){
        for(Person costumer : employeds.values()){
            Employed employed2 = (Employed) costumer;
            if(employed2.getEmail().equals(person.getEmail())){ // prevejo que isso nunca vai acontecer, uma vez que o configure controla tudo
                System.out.println("Não pode criar uma conta com email já catalogado!");
                return false;
            }
        }
        this.employeds.put(person.getEmail(), person); //Funcionário salvo
        return true;
    }

    public Employed checkEmployedEmail(String email){        
        for (Person employed2 : employeds.values()) {
            Employed costumer3 = (Employed) employed2;
            if(email.equals(costumer3.getEmail()))
                return costumer3;
        }
        return null;
    }

    public Employed remove(String email){
        Employed employed2 = checkEmployedEmail(email);
        if(!(employed2 instanceof Employed))
            return null;
        return (Employed) employeds.remove(employed2.getEmail());
    }

    
    public String toString(){
        StringBuffer st = new StringBuffer();
        st.append("Agency: ");
        st.append(code);
        st.append(" id: ");
        if(employeds.size() > 0){
            st.append("\n");
            st.append("======== USER ========");
            for (Person person : employeds.values()) {
                Employed costumer = ((Employed)person);
                st.append("\n");
                st.append(costumer);
               
            }
        }
        return st.toString();
    }
    
}
