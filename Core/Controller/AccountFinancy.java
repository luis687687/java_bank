package Controller;

public class AccountFinancy extends Account{
    
    
    public AccountFinancy(){
        super();
        this.percent = Configurations.percent_financy_account;
    }
    public boolean setCredite(double value){
        return false; //nao pode setar credito
    }
}
