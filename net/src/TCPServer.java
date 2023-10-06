
import Interfaces.WarehouseInterface;
import administration.Customer;
import cargo.Cargo;
import cargo.Hazard;
import customer.CustomerStorage;
import javafx.util.Pair;
import jbs.CustomJBP;
import jbs.WarehouseJBP;
import jos.CustomJOS;
import jos.WarehouseJOS;
import warehouse.Warehouse;
import warehouse.WarehouseManagement;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TCPServer {
    private static final int PORT = 8000;
    WarehouseManagement warehouseManagement;

    public TCPServer() {
        warehouseManagement = new WarehouseManagement();
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress().getHostAddress());

                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clientHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private class ClientHandler extends Thread {
        private Socket clientSocket;
        private BufferedReader reader;
        private PrintWriter writer;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
            try {
                reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                writer = new PrintWriter(clientSocket.getOutputStream(), true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                String request;
                while ((request = reader.readLine()) != null) {
                    System.out.println("request recieved :" + request);
                    String response =  resolveRequest(request);

                    writer.println(response);

                    System.out.println("Sent response: " + response);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    public synchronized String insertCustomer(String name) {
        Customer existingCustomer =  warehouseManagement.getCustomerStorage().getCustomer(name);

        if(existingCustomer != null) {
            return "Customer with this name already exists";
        }
        else {
            Customer customer = this.warehouseManagement.insertCustomer(name);
            if(customer != null) {
                return "Customer " + customer.getName() + " has been created";
            }
            else {
                return "some issue in inserting customer";
            }
        }
    }

    private String resolveRequest(String request) throws IOException, ClassNotFoundException {
        if(request.contains("insertcustomer")) {
           String name = request.substring("insertcustomer".length());
          return insertCustomer(name);

        }
        if(request.contains("insertcargo")) {
            String cargoName = request.substring("insertcargo".length());
            return insertCargo(cargoName);
        }
        if(request.contains("removecargo")) {
            String cargoNamee = request.substring("removecargo".length());
            return removeCargo(cargoNamee);
        }
        if(request.contains("removecustomer")) {
            String customerName = request.substring("removecustomer".length());
            return removeCustomer(customerName);
        }
        if(request.contains("readcustomer")) {
            String name = request.substring("readcustomer".length());
            return readCustomer(name);

        }
        if(request.contains("persisdatajos")) {
            CustomJOS.persistJOS("persisteddata/customerjosdata.txt", (CustomerStorage) this.warehouseManagement.getCustomerStorage());
            WarehouseJOS.persistWithJOS("persisteddata/warehousejosdata.txt",this.warehouseManagement.getWarehouse());

        }
        if(request.contains("initializedatajos")) {
            CustomerStorage customerStorage =   CustomJOS.initializeJOS("persisteddata/customerjosdata.txt");
            warehouseManagement.setCustomerStorage(customerStorage);
            Warehouse warehouse = WarehouseJOS.initializeWithJOS("persisteddata/warehousejosdata.txt");
            warehouseManagement.setWarehouse(warehouse);

            return "data initialised through jbp";

        }
        if(request.contains("persistdatajbp")) {
            CustomJBP.persistJBP("persisteddata/customerjbpdata.txt",this.warehouseManagement.getCustomerStorage());
            WarehouseJBP.persistJBP("persisteddata/warehousejbpdata.txt",this.warehouseManagement.getWarehouse());

            return  "Data persisted through JBP ";
        }
        if(request.contains("initializedatajbp")) {
            CustomerStorage customerStorage =   CustomJBP.initializeJBP("persisteddata/customerjbpdata.txt");
            warehouseManagement.setCustomerStorage(customerStorage);
            WarehouseInterface warehouse = WarehouseJBP.initializeJBP("persisteddata/warehousejbpdata.txt");

            //warehouseManagement.setCustomerStorage(customerStorage);
            warehouseManagement.setWarehouse(warehouse);
            return "data initialized through jbp";
        }
        if(request.contains("inspectcargo")) {
            String number = request.substring("inspectcargo".length());

            this.warehouseManagement.inspectCargo(number);
            return "cargo is updated";

        }
        if(request.contains("readcargo")) {
            String type = request.substring("readcargo".length());
            if(type.isEmpty()) {
                return warehouseManagement.readAllCargo().toString();
            }
            else {
                return warehouseManagement.readCargoByType(type).toString();
            }

        }
        if(request.contains("readhazardsi")) {
            Set<Hazard> hazardSet =   warehouseManagement.getIncludedHazards();
            return hazardSet.toString();

        }
        if(request.contains("readhazardse")) {
          Set<Hazard> hazardSet =   warehouseManagement.getExcludedHazards();
          return hazardSet.toString();
        }
        return "";
    }

    private String readCustomer(String name) {

        Collection<Pair<String,Integer>> customersWithTotalCargo = warehouseManagement.getCustomersWithTotalCargo();
        String result = "";
        for (Pair<String, Integer> customer : customersWithTotalCargo) {
                  result = result + "Customer: " + customer.getKey() + ", Total Cargo: " + customer.getValue();
                  result  = result + "\n";
        }

        return result;
    }

    private String removeCustomer(String customerName) {
        this.warehouseManagement.removeCustomer(customerName);
        return "";
    }

    private String removeCargo(String cargoNamee) {
        this.warehouseManagement.removeCargo(cargoNamee);
        return " cargo inserted";
    }

    private String insertCargo(String cargoName) {
        try {
            this.warehouseManagement.insertCargo(cargoName);
            return "";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {
        TCPServer server = new TCPServer();
        server.start();
    }
}
