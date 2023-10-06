package jos;

import Interfaces.CustomerStorageInterface;
import customer.CustomerStorage;

import java.io.*;

public class CustomJOS {
    public static CustomerStorage initializeJOS(String filePath) throws IOException, ClassNotFoundException {
        try{
        CustomerStorage customerStorage = null;
        FileInputStream fileInputStream = new FileInputStream(filePath);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        customerStorage = (CustomerStorage) objectInputStream.readObject();
        return customerStorage;
        }catch (Exception e){

        }
        return null;
    }


    public static void persistJOS(String filepath, CustomerStorageInterface customerStorage) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(filepath);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(customerStorage);
    }
}
