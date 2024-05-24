package Controller;

public class Costumer extends Person {

    private String email;
    private String password;
  
    public Costumer(String email, String password){
        this.email = email;
        this.password = password;
      
    }

    public String getEmail(){
        return this.email;
    }

    public String getPassword(){
        return this.password;
    }

   
    public String toString(){
        return this.email+"  "+this.password;
    }
    
}
