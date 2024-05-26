package Controller;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;

/****
 * 
 * As validacoes de insersao de employeds e clients devem estar na camada do arquivo software
 */

public class Agency implements Serializable{
    
    private HashMap<String, Person> clients;
    private HashMap<String, Person> employeds;
    private Client selected_client;
    private Employed selected_employed;

    private String code;

    
    public Agency(String code){
        this.clients = new HashMap<>();
        this.employeds = new HashMap<>();
        this.code = code;
    }

    public HashMap<String, Person> getClients(){
        return this.clients;
    }
    public HashMap<String, Person> getEmplyeds(){
        return this.employeds;
    }
    
    public String getCode(){
        return this.code;
    }
    public Client getSelectedClient(){
        return this.selected_client;
    }
    public Employed getSelectEmployed(){
        return this.selected_employed;
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


    public boolean appendClient(Client client){
        clients.put(client.getCode(), client);
        return true;
    }

    public boolean setSelecteClient(String code){
        selected_client = this.hasClientWithSameCode(code);
        if((selected_client instanceof Client))
            return false;
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

    public Employed removeEmployed(String email){
        Employed employed2 = checkEmployedEmail(email);
        if(!(employed2 instanceof Employed))
            return null;
        return (Employed) employeds.remove(employed2.getEmail());
    }

    public Client hasClientWithSameCode(String code){
        for(Person client2 : clients.values()){
            Client client3 = (Client) client2;
            if(client3.getCode().equals(code))
                return client3;
        }

        return null;
    }

    
    public String toString(){
        StringBuffer st = new StringBuffer();
        st.append("Agency: ");
        st.append(code);

        if(employeds.size() > 0){
            st.append("\n");
            st.append("======== Empregados ========");
            st.append("\n");
            for (Person person : employeds.values()) {
                Employed costumer = ((Employed)person);
                st.append("\n");
                st.append(costumer);
               
            }
        }
        if(clients.size() > 0){
            st.append("\n");
            st.append("=========== Clientes ===========");
            st.append("\n");
            for(Person client : clients.values()){
                st.append("\n");
                st.append(((Client)client));
                
            }
        }
        return st.toString();
    }
    
}
