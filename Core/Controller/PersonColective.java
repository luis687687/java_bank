package Controller;

public class PersonColective extends Person implements IClient {
    
    Comertial comertial;
    Account account;
    public PersonColective(Comertial comertial){
        super(comertial.phone, comertial.optionalphone);
        this.comertial = comertial;
    }

    public String getName(){
        return this.comertial.name;
    }
    public String getCode(){
        return this.comertial.comertialcode;
    }
    public Account getAccount(){
        return this.account;
    }
}
