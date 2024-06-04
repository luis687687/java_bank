package Controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;


/****
 * iban generator method i seted up as private
 */

public class Account implements Serializable {
    
    private double money;
    private String iban;
    private ArrayList<Moviment> moviments;
    private double creditemoney;
    private double totalcreditemoney;
    private double payedcredit;
    protected double percent;
    private Date datecredite_start;
    protected Date dateaccount_created;


    //auxiliares
    private long last_time_to_pay_credite; //ultima vez que pagou o credito
    private long last_time_to_pay_account; //ultima vez que pagou a manutenção
    

    public Account(){
        this.ibanGenerator();
        this.moviments = new ArrayList<>();
        this.percent = Configurations.percent_normal_account;
        this.dateaccount_created = new Date();
        
    }
    
    public void setMoney(double money){
        this.money = money;
    }
    public double getMoney(){
        return this.money;
    }


    //cobrar manutenção
    protected boolean demandMaintance(){
        long seconds = seconds(dateaccount_created);
        int cont = countMultiplesTime(seconds, last_time_to_pay_account, Configurations.)
        return true;
    }

    public boolean setCredite(double value){
        if(hasCredite())
            return false;

        if(Configurations.maximus_credite >= value){
           
            this.datecredite_start = new Date();
            this.creditemoney = value;
            this.totalcreditemoney = value;
            moviments.add(new Moviment(value, Configurations.mov_type_credite_request, this.money));
            return true;
        }
        
        System.out.println("Limite de creditos");
        return false;
    }
    public boolean hasCredite(){
        //tem crédito não pago, então não pessa ,m
        return this.totalcreditemoney > 0 || this.money < 0;
    }

    
    protected long seconds(Date datestart){
        Date actualdate = new Date();
        long spendedtime = actualdate.getTime() - datestart.getTime();
        long seconds = spendedtime / 1000;
        return seconds;
    }
    protected int countMultiplesTime(long startsecond, long lastsecond, long divisor){
        long diference = startsecond - lastsecond;
        int cont = (int) (diference / divisor);
        return cont;
    }

    public double getCredite(){
        if(this.datecredite_start instanceof Date)
        {
            double totalpay = 0;
            long seconds = seconds(this.datecredite_start);
            totalpay = countMultiplesTime(seconds, last_time_to_pay_credite, Configurations.second_time_waiting_to_pay_credite_account)*this.percent*this.totalcreditemoney;
            payedcredit += totalpay;
            if(totalpay != 0)
                if(this.totalcreditemoney > payedcredit ){ //pode remover o crédito
                    this.money -= totalpay;
                    this.totalcreditemoney -= totalpay;
                    last_time_to_pay_credite = seconds;
                    moviments.add(new Moviment(-totalpay, Configurations.mov_type_credite_pay, this.money));
                }
                else{
                    this.money -= this.totalcreditemoney;
                    this.payedcredit += this.totalcreditemoney;
                    this.datecredite_start = null;
                    this.totalcreditemoney = 0;
                    moviments.add(new Moviment(-this.creditemoney, Configurations.mov_type_credite_pay, this.money));
                }
        }
        else{
            this.totalcreditemoney = 0;
            this.payedcredit = 0;
        }
        return this.totalcreditemoney;
    }

    public double getTotalCredite(){
        return this.totalcreditemoney;
    }

    
    public void setIban(String iban){
        this.iban = iban;
    }
    public String getIban(){
        return this.iban;
    }
    private void ibanGenerator(){

        StringBuffer intermediary = new StringBuffer();
        intermediary.append(Configurations.defaultibancode);
        intermediary.append(Math.random());

        
       this.iban = intermediary.toString();
       this.iban = this.iban.replace(".", "");

        
    }

    public boolean depositMoney(double money){
        double finalmoney = this.money + money;
        if(money > 0){
            if(this.money < 0){
                
                if(finalmoney >= 0) //descontou tudo que estava lá
                    this.moviments.add(new Moviment(-this.money, Configurations.mov_type_credite_take_money_on_deposit, finalmoney));
                else //desconte tudo que entrou
                    this.moviments.add(new Moviment(-money, Configurations.mov_type_credite_take_money_on_deposit, finalmoney));
            }
            this.money = finalmoney;
            this.moviments.add(new Moviment(money, Configurations.mov_type_deposit, finalmoney));
            
            return true;
        }
        return false;
    }

    public boolean removeMoney(double money){
        return this.internalRemoveMoney(money);
    }
    protected boolean internalRemoveMoney(double money){
        if(this.money > 0){
            this.money -= money;
            this.moviments.add(new Moviment(-money, Configurations.mov_type_remov, this.money));
            return true;
        }
        return false;
}

    public boolean hasMoney(){
        return this.money > 0;
    }
    
    public boolean transfere(Double money, String destinationiban, Date dateend){
       return this.internalTransfere(money, destinationiban, dateend);
    }
    protected boolean internalTransfere(Double money, String destinationiban, Date dateend){
        if(this.money < money)
            return false;
        if(destinationiban == this.iban)
            return false;
        this.money -= money;
        this.moviments.add(new Moviment(-money, new Date(), dateend, this.iban, destinationiban, this.money));
        return true;
    }

    public boolean receive(Double money, String originiban, Date dateend){
        if(money <= 0)
            return false;
        this.money += money;
        this.moviments.add(new Moviment(money, new Date(), dateend, originiban, this.iban, this.money));
        return true;
    }

    public ArrayList<Moviment> getMoviments(){
        return this.moviments;
    }

}
