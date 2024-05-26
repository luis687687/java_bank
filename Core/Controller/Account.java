package Controller;
public class Account {
    
    private double money;

    
    
    public void setMoney(double money){
        this.money = money;
    }
    public double getMoney(){
        return this.money;
    }

    public boolean depositMoney(double money){
        if(money > 0){
            this.money += money;
            return true;
        }
        return false;
    }

    public boolean removeMoney(double money){
            if(this.money > 0){
                this.money -= money;
                return true;
            }
            return false;
       
    }

    public boolean hasMoney(){
        return this.money > 0;
    }

}
