package Model;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DataBase <E> { //Essa clase trata de objectos genéricos para salvar em arquivos serializado

    private String table;
    

    public DataBase(String serializefilename){
        this.table = serializefilename;
    }

    public boolean save(E O) {


        try{
            FileOutputStream file = new FileOutputStream(this.table);
            ObjectOutputStream object = new ObjectOutputStream(file);
            
            object.writeObject(O);
    
        }
        catch(IOException ex){

            System.err.println(ex + " Excepcao disparada! ");
            return false;
        }
        
        return true;
    }

    public E read(){

        try{

            FileInputStream file = new FileInputStream(this.table);
            ObjectInputStream object = new ObjectInputStream(file);

            return (E) object.readObject();

        }
        catch(Exception ex){

            System.err.println(ex + " Excepcao disparada! ");
            return null;
        }

    }
}