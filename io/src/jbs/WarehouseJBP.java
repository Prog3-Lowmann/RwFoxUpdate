package jbs;

import Interfaces.WarehouseInterface;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;

public class WarehouseJBP {
    public static void persistJBP(String filepath, WarehouseInterface customerStorage) throws IOException {
//        File customerFile = new File(filepath);
//        FileOutputStream customerFos = new FileOutputStream(customerFile);
//        XMLEncoder customerEncoder = new XMLEncoder(customerFos);
//        customerEncoder.writeObject(customerStorage);
//        customerEncoder.close();
//        customerFos.close();

        File customerFile = new File(filepath);
        FileOutputStream customerFos = new FileOutputStream(customerFile);
        XMLEncoder customerEncoder = new XMLEncoder(customerFos);

        // Serialize the WarehouseInterface object using custom serialization
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(customerStorage);
        oos.flush();
        oos.close();
        customerEncoder.writeObject(baos.toByteArray());

        customerEncoder.close();
        customerFos.close();
    }

    public static WarehouseInterface initializeJBP(String filepath) throws IOException {
//        File file  = new File(filepath);
//        if (!file.exists()) {
//            throw new FileNotFoundException("File not found: " + filepath);
//        }
//        FileInputStream customerFis = new FileInputStream(file);
//        XMLDecoder customerDecoder = new XMLDecoder(customerFis);
//        WarehouseInterface customerStorage = (WarehouseInterface) customerDecoder.readObject();
//        customerDecoder.close();
//        customerFis.close();
//        return customerStorage;

        File customerFile = new File(filepath);
        FileInputStream customerFis = new FileInputStream(customerFile);
        XMLDecoder customerDecoder = new XMLDecoder(customerFis);

        // Deserialize the WarehouseInterface object using custom deserialization
        byte[] serializedData = (byte[]) customerDecoder.readObject();
        ByteArrayInputStream bais = new ByteArrayInputStream(serializedData);
        ObjectInputStream ois = new ObjectInputStream(bais);

        try {
            return (WarehouseInterface) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new IOException("Failed to deserialize WarehouseInterface: " + e.getMessage());
        } finally {
            customerDecoder.close();
            customerFis.close();
        }
    }
}
