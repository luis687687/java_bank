package Controller;

import java.io.Serializable;



public class Person implements Serializable{

    String phone;
    BI bi;

    public Person(){

    }
    public Person(BI bi, String phone){
        this.phone = phone;
        this.bi = bi;
    }

    public String toString(){

        return this.bi.fullname;
    }

    
}