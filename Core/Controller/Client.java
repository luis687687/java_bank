package Controller;
public class Client extends Person {
    
    private double money;

    public Client(BI bi, String phone){
        super(bi, phone);
        
    }

    public void setMoney(double money){
        this.money = money;
    }
    public double getMoney(){
        return this.money;
    }

    public String getCode(){
        return this.bi.getNumber();
    }

    public void depositMoney(double money){
        if(money > 0)
            this.money += money;
    }
    public boolean removeMoney(double money){
        
            if(money > 0){
                this.money -= money;
                return true;
            }
            return false;
       
    }

    public boolean hasMoney(){
        return this.money > 0;
    }

    public String toString(){
        return this.bi.fullname+" saldo "+this.money;
    }
}
