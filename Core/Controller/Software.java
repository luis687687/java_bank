package Controller;

import java.util.ArrayList;
import java.util.HashMap;

import Model.DataBase;

public class Software  {
    
    

    private static HashMap<String, Person> admins = new HashMap<>();
    private static HashMap<String, Agency> agencies = new HashMap<>();
    private static DataBase<HashMap> tableAgency = new DataBase<>(Configurations.FILE_AGENCIES);
    private static DataBase<HashMap> tableAdmins = new DataBase<>(Configurations.FILE_ADMINS);
    private static Agency actual_Agency; //IMPORTANTE, quando seleccionar em abrir uma agencia especifica
    private static Admin actual_admin;

   
   public static boolean appendAgency(Agency agency){
        for (Agency agency2 : agencies.values()) {   
            if(agency2.getCode().equals(agency.getCode())){
                return false;
            }
        }
        agencies.put(agency.getCode() , agency);
        return true;
   }

   public static Agency getAgency(String code){
        for(Agency agency : agencies.values())
            if(agency.getCode().equals(code))
                return agency;
        return null;
   }


   public static void showAgencies(){
        StringBuffer st = new StringBuffer();
        for (Agency agency : agencies.values()) {
            st.append("\n\n ");
            st.append(agency);
        }

       System.out.println(st);
   }

   public static boolean setActualAgency(String code){
        actual_Agency = getAgency(code);
        return actual_Agency instanceof Agency;
   }


   public static boolean agencyAppendCostumer(Agency agency, Costumer costumer){
    if(!(agency instanceof Agency)) //prevejo que não aconteça esse caso
        return false;
    if(checkEmailAgencyList(costumer.getEmail()) instanceof Costumer)
        return false;
    agency.appendCostumer(costumer);
    updateAgencyMap(agency);
    return true;
   }

   public static boolean actualAgencyAppendCostumer(Costumer costumer){
        return agencyAppendCostumer(actual_Agency, costumer);
   }

   private static Costumer checkEmailAgencyList(String email){
    Costumer costumer2;
    for (Agency agency : agencies.values()) {
        costumer2 = checkEmailOneAgency(agency, email);
       if(costumer2 instanceof Costumer)
            return costumer2;
    }
    return null;
   }
   public static Costumer checkEmailOneAgency(Agency agency, String email){
        Costumer costumer2 = (agency.checkCostumerEmail(email));
        if(costumer2 instanceof Costumer)
            return costumer2;
        return null;
   }


   private static void updateAgencyMap(Agency agency){ //privado porque é restrito para o uso interno, pois a actualizar uma agencia individualmente no array
    agencies.put(agency.getCode(), agency);
   }

   //transferir funcionario com email de agencia from para agencia to
   public static boolean transfereCostumer(String email, Agency from, Agency to){
    if(to.checkCostumerEmail(email) instanceof Costumer)
        return false; //já pertence neste distino
    if(!(from.checkCostumerEmail(email) instanceof Costumer))
        return false; //nao pertence nesta origem
    to.appendCostumer(from.remove(email));
    System.out.println("Transferido com sucesso");

    updateAgencyMap(from);
    updateAgencyMap(to);
    return true;
   }

   public static void removeAgency(String code){
    agencies.remove(code);
   }

   //login de funcionários de agencias
   public Costumer costumerLogin(Costumer costumer){
    Costumer costumer2;
    costumer2 = (checkEmailAgencyList(costumer.getEmail()));
    if(costumer2 instanceof Costumer){
        if(costumer2.getPassword().equals(costumer.getPassword())){
            costumer = costumer2;
            return costumer2;
        }
    }
    costumer = null;
    return costumer2;
   }


   //Salvar tudo
   public static boolean saveAgencyState(){
   
    return tableAgency.save(agencies);
   }

   public static boolean loadState(){

    HashMap hashmap = tableAgency.read();
    if(hashmap instanceof HashMap){
        agencies = hashmap;
        return true;
    }
    return false;
    
   }
   
}
