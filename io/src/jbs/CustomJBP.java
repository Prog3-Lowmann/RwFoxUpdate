package jbs;

import Interfaces.CustomerStorageInterface;
import customer.CustomerStorage;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;

/**
 * https://openbook.rheinwerk-verlag.de/java8/07_010.html
 *
 */

public class CustomJBP {
    public static void persistJBP(String filepath, CustomerStorageInterface customerStorage) throws IOException {
        File customerFile = new File(filepath);
        FileOutputStream customerFos = new FileOutputStream(customerFile);
        XMLEncoder customerEncoder = new XMLEncoder(customerFos);
        customerEncoder.writeObject(customerStorage);
        customerEncoder.close();
        customerFos.close();
    }

    public static CustomerStorage initializeJBP(String filepath) throws IOException {
        File file  = new File(filepath);
        if (!file.exists()) {
            throw new FileNotFoundException("File not found: " + filepath);
        }
        FileInputStream customerFis = new FileInputStream(file);
        XMLDecoder customerDecoder = new XMLDecoder(customerFis);
        CustomerStorage customerStorage = (CustomerStorage) customerDecoder.readObject();
        customerDecoder.close();
        customerFis.close();
        return customerStorage;
    }
}
