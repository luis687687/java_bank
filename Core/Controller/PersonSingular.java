package Controller;

public class PersonSingular  extends Person implements IClient{
    
    BI bi;
    Account account;
    public PersonSingular(BI bi, String phone, String optionalphone){
        super(phone, optionalphone);
        this.phone = phone;
        this.bi = bi;
    }

    public String toString(){
        return this.bi.fullname;
    }

    public String getName(){
        return this.bi.fullname;
    }
    public String getCode(){
        return this.bi.getNumber();
    }
    public Account getAccount(){
        return this.account;
    }
}
