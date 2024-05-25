import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.function.BiConsumer;

import Controller.Admin;
import Controller.Agency;
import Controller.Software;
import Controller.Employed;
import Controller.Person;


public class Main {
    
    public static void main(String ...args) throws Exception {

        new Software();

        Scanner sc = new Scanner(System.in);

        String response_str;
        int response_int;
        boolean response_logic;
       

        while (true) {
            
           System.out.println("1 -criar agencia ");
           System.out.println("2 - ver lista de agencias");
           System.out.println("3 - seleccionar agencia e adicionar funcionario na agencia ");
           System.out.println("4- Transferir funcionario para outra agencia ");
           System.out.println("5 - Eliminar agencia ");
           System.out.println("6 - Mostrar dados de login");
           System.out.println("7 - Login Emplyed");
           System.out.println("8 - Add admin");
           System.out.println("9 - show admin");
           System.out.println("x - terminar sessao");
           System.out.println("* sair");

            response_str = sc.next();
            sc.nextLine();
            if(response_str.equals("1")){
                System.out.println("Code da agencia");
                response_str = sc.next();
                sc.nextLine();
                Agency agency = new Agency(response_str);
                response_logic = Software.appendAgency(agency);
                if(response_logic){
                    System.out.println("Well");
                }
                
                
            }
            else{
                if(response_str.equals("2"))
                    Software.showAgencies();
                else{
                   
                    if(response_str.equals("3"))
                    {   
                        System.out.println("Adicionar funcionário");
                        System.out.println("Choose the agency code");
                        response_str = sc.next();
                        sc.nextLine();
                        response_logic = Software.setActualAgency(response_str);
                        if(response_logic){
                            System.out.println("Informe o email: ");
                            String email = sc.nextLine();
                            System.out.println("A passe: ");
                            String pass = sc.nextLine();

                            Employed costumer = new Employed(email, pass);

                            Software.actualAgencyAppendEmployed(costumer);
                            
                        }
                    }
                    else{
                        if(response_str.equals("4")){
                            
                            System.out.println("Transferir funcionario");
                            System.out.println("Code Origem");
                            response_str = sc.next();
                            sc.nextLine();
                            Agency agency = Software.getAgency(response_str);
                            if(agency instanceof Agency){
                                boolean repeat = false;
                                do{
                                    System.out.println("Informe o email: ");
                                    String email = sc.nextLine();
                                    System.out.println("Informe a agencia destino (code): ");
                                    String code = sc.nextLine();
                                    Agency agency2 = Software.getAgency(code);

                                    Software.transfereEmployed(email, agency, agency2);
                                }while(repeat);
                               
                               
                                
                            }
                        }
                        else{

                            if(response_str.equals("5")){

                            }
                            else{
                                if(response_str.equals("6")){
                                    Software.showLoggedEmplyed();
                                    Software.showActualAgency();

                                }
                                else{
                                    if(response_str.equals("7")){
                                       System.out.println("Login de Email e Pass");
                                       Software.login(
                                        sc.nextLine(), sc.nextLine()
                                       );
                                       
    
                                    }
                                    else{
                                        if(response_str.equals("8")){
                                            System.out.println("Add admin");
                                            System.out.println("Email e senha");
                                            Admin admin = new Admin(sc.nextLine(), sc.nextLine());
                                            Software.appendAdmin(admin);
                                         }
                                         else{
                                            if(response_str.equals("9")){
                                                System.out.println("Show");
                                                Software.showAdmins();
                                                
             
                                             }
                                             else{
                                                if(response_str.equals("x")){
                                                   Software.logout();
                                                 }
                                                 else{
                                                     System.out.println("Salvando...");
                                                     Software.saveAgencyState();
                                                     Software.saveAdminState();
                                                     Software.logout();
                                                     return;
                                                 }
                                             }
                                         }
                                    }
                                   
                                }
                                
                            }
                        }
                    }
                }
            }
        }
    }
}
