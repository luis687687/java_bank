package Controller;

import java.io.Serializable;



public class Person implements Serializable{

    String phone;
    BI bi;

    public Person(){

    }
    public Person(String name){
        this.bi = new BI();
        this.bi.fullname = name;
    }

    public String toString(){

        return this.bi.fullname;
    }

    
}