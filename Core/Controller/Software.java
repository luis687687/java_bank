package Controller;

import java.util.ArrayList;
import java.util.HashMap;

import Model.DataBase;

public class Software  {
    
    

    private static HashMap<String, Admin> admins = new HashMap<>();
    private static HashMap<String, Agency> agencies = new HashMap<>();
    private static DataBase<HashMap> tableAgency = new DataBase<>(Configurations.FILE_AGENCIES);
    private static DataBase<HashMap> tableAdmins = new DataBase<>(Configurations.FILE_ADMINS);
    private static Agency actual_Agency; //IMPORTANTE, quando seleccionar em abrir uma agencia especifica

    private static Employed logged_emplyed;
    
    public Software(){
        admins.put(Configurations.defaultadmin.getEmail(), Configurations.defaultadmin);
        Software.loadState();
      
    }
   
   public static boolean appendAgency(Agency agency){
        if(!isAdmin())
            return false;
        for (Agency agency2 : agencies.values()) {   
            if(agency2.getCode().equals(agency.getCode())){
                return false;
            }
        }
        agencies.put(agency.getCode() , agency);
        return true;
   }

   public static boolean appendAdmin(Admin admin){
        if(!isAdmin())
            return false;
        if(checkEmailInSystem(admin.getEmail()) instanceof PairEmployedAgency)
            return false;
        admins.put(admin.getEmail() , admin);
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
   public static void showActualAgency(){
  
    System.out.println(actual_Agency);
    System.out.println("");
   }
   public static void showLoggedEmplyed(){
    System.out.println(logged_emplyed);
   }

   public static void showAdmins(){
    StringBuffer st = new StringBuffer();
    for (Admin admin : admins.values()) {
        st.append("\n\n ");
        st.append(admin);
    }
    System.out.println(st);
   }

   public static boolean setActualAgency(String code){
        actual_Agency = getAgency(code);
        return actual_Agency instanceof Agency;
   }


   public static boolean agencyAppendEmployed(Agency agency, Employed employed){
    if(!isAdmin())
        return false;
    if(checkEmailInSystem(employed.getEmail()) instanceof PairEmployedAgency)
        return false;
    agency.appendEmployed(employed);
    updateAgencyMap(agency);
    return true;
   }

   public static boolean actualAgencyAppendEmployed(Employed employed){
        return agencyAppendEmployed(actual_Agency, employed);
   }


   //verifica se email está no sistema, para nao permitir duplicates no sistema
   private static PairEmployedAgency checkEmailInSystem(String email){ //retorna um par, de agencia e empregado
    Employed employed2;
    for(Admin admin : admins.values())
        if(admin.getEmail().equals(email))
            return new PairEmployedAgency(null, admin); //returno o object pois o email exist sim, mas o par de empregado e agencia, não condizem, pos é um admin, só é útil no acto de login
    for (Agency agency : agencies.values()) {
        employed2 = checkEmailOneAgency(agency, email);
       if(employed2 instanceof Employed)
            return new PairEmployedAgency(agency, employed2);
    }
    return null;
   }
   //verifica email presente em uma agencia para nao permitir duplicates na agencia
   public static Employed checkEmailOneAgency(Agency agency, String email){
        Employed employed2 = (agency.checkEmployedEmail(email));
        if(employed2 instanceof Employed)
            return employed2;
        return null;
   }


   private static void updateAgencyMap(Agency agency){ //privado porque é restrito para o uso interno, pois a actualizar uma agencia individualmente no array
    agencies.put(agency.getCode(), agency);
   }

   //transferir funcionario com email de agencia from para agencia to
   public static boolean transfereEmployed(String email, Agency from, Agency to){
    if(to.checkEmployedEmail(email) instanceof Employed)
        return false; //já pertence neste distino
    if(!(from.checkEmployedEmail(email) instanceof Employed))
        return false; //nao pertence nesta origem
    to.appendEmployed(from.remove(email));
    System.out.println("Transferido com sucesso");
    updateAgencyMap(from);
    updateAgencyMap(to);
    return true;
   }

   public static void removeAgency(String code){
    agencies.remove(code);
   }

   //login de funcionários de agencias
   public static boolean login(String email, String pass){
    PairEmployedAgency pair;
    Employed employed2;
    pair = (checkEmailInSystem(email));
    if(pair instanceof PairEmployedAgency){
        employed2 = pair.employed;
        if(employed2.getPassword().equals(pass)){
            actual_Agency = pair.agency;
            logged_emplyed = employed2;
            return true;
        }
    }
    return false;
   }


   //Salvar tudo
   public static boolean saveAgencyState(){
    return tableAgency.save(agencies);
   }
   public static boolean saveAdminState(){
    return tableAdmins.save(admins);
   }
   public static boolean loadState(){
    HashMap<String, Agency> tb_agencies = tableAgency.read();
    HashMap<String, Admin> tb_admins = tableAdmins.read();

    if(tb_agencies instanceof HashMap && tb_admins instanceof HashMap){
        agencies = tb_agencies;
        admins = tb_admins;
        return true;
    }
    return false;
    
   }

   public static void logout(){
    logged_emplyed = null;
    actual_Agency = null;
   }

   public Admin checkAdminEmail(String email){        
        for (Person admin : admins.values()) {
            Admin admin2 = (Admin) admin;
            if(email.equals(admin2.getEmail()))
                return admin2;
        }
        return null;
    }
    private static boolean isAdmin(){
       
        if(logged_emplyed instanceof Admin)
            return true;
        System.out.println("Faça login com admin Válido");
        return false;
    }
   
}
