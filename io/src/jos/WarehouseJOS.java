package jos;

import Interfaces.WarehouseInterface;
import warehouse.Warehouse;

import java.io.*;

public class WarehouseJOS {
    public static Warehouse initializeWithJOS(String filePath) throws IOException, ClassNotFoundException {
        Warehouse warehouse = null;
        FileInputStream fileInputStream = new FileInputStream(filePath);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        warehouse = (Warehouse) objectInputStream.readObject();
        return warehouse;
    }

    public static void persistWithJOS(String filepath, WarehouseInterface warehouse) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(filepath);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(warehouse);
    }
}
