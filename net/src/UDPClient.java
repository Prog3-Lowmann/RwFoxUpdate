import viewControler.Command;
import viewControler.Operator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UDPClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 8000;
    private static final int BUFFER_SIZE = 1024;

    private Operator currentCase;

    public void switchCommand(String input) {
        Command command = new Command(input);

        switch (command.operator) {
            case INSERT:
                currentCase = Operator.INSERT;
                System.out.println("Switched to " + command.operator + " mode");
                //System.out.println("Enter cargo details:"); //aq xo ar chamovitano is maglidan?
                System.out.println("Choose an option: ");
                System.out.println("Cargo");
                System.out.println("Customer");
                break;
            case REMOVE:
                currentCase = Operator.REMOVE;
                System.out.println("Switched to " + command.operator + " mode");
                //System.out.println("to delete cargo please enter storage location");
                System.out.println("Choose an option: ");
                System.out.println("Cargo");
                System.out.println("Customer");
                break;
            case INSPECT:
                currentCase = Operator.INSPECT;
                System.out.println("Switched to " + command.operator + " mode");
                System.out.println("to inspect cargo please enter storage location");
                break;
            case READ:
                currentCase = Operator.READ;
                System.out.println("Switched to " + command.operator + " mode");
                System.out.println("Choose an option: ");
                System.out.println("Customer");
                System.out.println("read cargo mode, please enter \u001B[33m 'cargos' \u001B[0m and then one of the cargo types: " +
                        "\n* DryBulkCargo \n* LiquidBulkCargo \n* UnitisedCargo  \n* DryBulkAndUnitisedCargo \n* LiquidAndDryBulkCargo" +
                        "\n* LiquidBulkAndDryBulkCargo ");
                System.out.println("Hazards [included (i) / excluded (e)]");
                break;
            case QUIT:
                System.out.println("\u001B[35m Exit .. \u001B[0m");
                //this.persistDB(customerDBPath,cargoDBPath);
                System.exit(0);
                break;

            case PERSISTENCE:
                currentCase = Operator.PERSISTENCE;

                System.out.println("Switched to " + command.operator + " mode");
                System.out.println("type command :\n 1.Save Jos \n 2.Load Jos\n 3.Save JBP\n 4.Load JBP");
                break;
            default:
                System.out.println("Error");

        }
    }
    public void startProgram(InetAddress serverAddress, DatagramSocket socket) {
        System.out.println("Please enter desired command to execute the action:");
        System.out.println(":c Wechsel in den Einfügemodus");
        System.out.println(":d Wechsel in den Löschmodus");
        System.out.println(":r Wechsel in den Anzeigemodus");
        System.out.println(":u Wechsel in den Änderungsmodus");
        System.out.println(":p Wechsel in den Persistenzmodus");
        System.out.println(":e to quit the program");
        System.out.println("\n");
        System.out.println("Enter command here: ");
        try (Scanner s = new Scanner(System.in)) {
            do {
                try {
                    String input = s.nextLine().trim().toLowerCase();

                    if (input.startsWith(":")) {
                        switchCommand(input);
                    }
                    else {
                        try {
                            executeCommand(input,serverAddress,socket);
                        }
                        catch (Exception e) {
                            System.out.println("Wrong option");
                        }

                    }

                }catch (StringIndexOutOfBoundsException e){
                    System.out.println("Invalid command! Please enter a valid command.");

                }
            }while(true) ;
        }
    }

    private void executeCommand(String input, InetAddress serverAddress,DatagramSocket socket) throws Exception{
        Scanner scanner = new Scanner(System.in);
        switch (currentCase) {
            case INSERT:
                if(input.equalsIgnoreCase("cargo")){
                    System.out.println("Please enter cargo details: ");
                    String cargoDetails = scanner.nextLine();
                    sendRequest("insertcargo"+cargoDetails,serverAddress,socket);
                    printResponse(socket);
                } else if (input.equalsIgnoreCase("customer")){
                    System.out.println("Please enter Customer name: ");
                    String customerName = scanner.nextLine();

                    sendRequest("insertcustomer"+customerName,serverAddress,socket);
                    printResponse(socket);
                }

                else {
                    System.out.println("Invalid option");
                }
                break;
            case REMOVE:
                if(input.equalsIgnoreCase("cargo")){
                    System.out.println("Please enter cargo details: ");
                    String cargoDetails = scanner.nextLine();

                    sendRequest("removecargo"+cargoDetails,serverAddress,socket);
                    printResponse(socket);
                }
                else if(input.equalsIgnoreCase("customer")){
                    System.out.println("Please enter Customer name: ");
                    String customerName = scanner.nextLine();
                    sendRequest("removecustomer"+customerName,serverAddress,socket);
                    printResponse(socket);

                } else {
                    System.out.println("Invalid option");
                }
                break;
            case INSPECT:
                sendRequest("inspectcargo"+input,serverAddress,socket);
                printResponse(socket);
                break;

            case READ: //todo: reading cargoByType does not work
                if(input.contains("cargo") || input.contains("Cargo") ) {

                    Pattern pattern = Pattern.compile("cargo[s]*\\s*\\[\\[([^]]+)\\]\\]");
                    Matcher matcher = pattern.matcher(input);
                    String type = "";
                    if(matcher.find()) {
                        type = matcher.group(1);
                    }

                    sendRequest("readcargo"+type,serverAddress,socket);
                    printResponse(socket);


                }
                else if(input.equalsIgnoreCase("customer")){

                    System.out.println("Please enter Customer name: ");
                    String customerName = scanner.nextLine();

                    sendRequest("readcustomer"+customerName,serverAddress,socket);
                    printResponse(socket);

                }
                else if (input.equalsIgnoreCase("hazards i")){

                    sendRequest("readhazardsi",serverAddress,socket);
                    printResponse(socket);
                    //Set<Hazard> includedHazards = warehouseManagement.getIncludedHazards();
                    // System.out.println("here are following hazards included: " + includedHazards);
                }
                else if (input.equalsIgnoreCase("hazards e")){

                    sendRequest("readhazardse",serverAddress,socket);
                    printResponse(socket);
                    //Set<Hazard> excludedHazards = warehouseManagement.getExcludedHazards();
                    // System.out.println("here are following hazards excluded: " + excludedHazards);
                }
                else {
                    System.out.println("Invalid option");
                }
                break;
            case PERSISTENCE:


                try {
                    if(input.equals("1")) {

                        sendRequest("persisteddatajos",serverAddress,socket);
                        printResponse(socket);
                        // CustomJOS.persistJOS("persisteddata/customerjosdata.txt", (CustomerStorage) this.warehouseManagement.getCustomerStorage());
                        // WarehouseJOS.persistWithJOS("persisteddata/warehousejosdata.txt",this.warehouseManagement.getWarehouse());
                        System.out.println("Data persisted through JOS");
                    }
                    if(input.equals("2")) {

                        sendRequest("initalizeddatajos",serverAddress,socket);
                        printResponse(socket);
                        //  CustomerStorage customerStorage =   CustomJOS.initializeJOS("persisteddata/customerjosdata.txt");
                        //  warehouseManagement.setCustomerStorage(customerStorage);
                        //  Warehouse warehouse = WarehouseJOS.initializeWithJOS("persisteddata/warehousejosdata.txt");

                        //  warehouseManagement.setWarehouse(warehouse);

                    }
                    if(input.equals("3")) {
                        sendRequest("persisteddatajbp",serverAddress,socket);
                        printResponse(socket);
                        //  CustomJBP.persistJBP("persisteddata/customerjbpdata.txt",this.warehouseManagement.getCustomerStorage());
                        //  WarehouseJBP.persistJBP("persisteddata/warehousejbpdata.txt",this.warehouseManagement.getWarehouse());

                        // System.out.println("Data persisted through JBP ");
                    }
                    if(input.equals("4")) {
                        sendRequest("initalizeddatajbp",serverAddress,socket);
                        printResponse(socket);
                        //  CustomerStorage customerStorage =   CustomJBP.initializeJBP("persisteddata/customerjbpdata.txt");
                        //  warehouseManagement.setCustomerStorage(customerStorage);
                        //  WarehouseInterface warehouse = WarehouseJBP.initializeJBP("persisteddata/warehousejbpdata.txt");

                        //warehouseManagement.setCustomerStorage(customerStorage);
                        // warehouseManagement.setWarehouse(warehouse);
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case QUIT:
                //this.persistDB(customerDBPath,cargoDBPath);
                System.exit(0);
                break;
            default:
                System.out.println("Invalid input - execute command");
        }
    }

    public void start() {
        try (DatagramSocket socket = new DatagramSocket();
             BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {

            InetAddress serverAddress = InetAddress.getByName(SERVER_ADDRESS);

            System.out.println("Connected to server. Type 'exit' to quit.");

            String userInput;
            startProgram(serverAddress,socket);
            while (true) {
////                System.out.print("Enter your request: ");
////                userInput = reader.readLine();
////
////                if (userInput.equalsIgnoreCase("exit")) {
////                    break;
////                }
//                startProgram(serverAddress,socket)
//
//                byte[] requestData = userInput.getBytes();
//                DatagramPacket requestPacket = new DatagramPacket(requestData, requestData.length, serverAddress, SERVER_PORT);
//                socket.send(requestPacket);
//
//                byte[] responseData = new byte[BUFFER_SIZE];
//                DatagramPacket responsePacket = new DatagramPacket(responseData, responseData.length);
//                socket.receive(responsePacket);
//
//                String response = new String(responsePacket.getData(), 0, responsePacket.getLength());
//                System.out.println("Response: " + response);
            }

            //System.out.println("Disconnected from server.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendRequest(String input, InetAddress serverAddress, DatagramSocket socket) throws IOException {
        byte[] requestData = input.getBytes();
        DatagramPacket requestPacket = new DatagramPacket(requestData, requestData.length, serverAddress, SERVER_PORT);
        socket.send(requestPacket);
    }

    public void printResponse (DatagramSocket socket) throws IOException {
            String response = "";
            byte[] responseData = new byte[BUFFER_SIZE];
            DatagramPacket responsePacket = new DatagramPacket(responseData,responseData.length);
            socket.receive(responsePacket);

            response = new String(responsePacket.getData(),0,responsePacket.getLength());
            System.out.println(response);

        }


}
