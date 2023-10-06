package viewControler;

import eventSystem.*;
import warehouse.WarehouseManagement;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AlternativeCLI {

    private Operator currentCase;

    private WarehouseManagement warehouseManagement;

    private String customerDBPath;
    private String cargoDBPath;

    private List<Handler> handlerList = new ArrayList<>();

    Handler insertEventHandler = null;
    Handler removeEventHandler = null;
    Handler updateEventHandler = null;

    Handler readEventHandler = null;

    public void addHandler(Handler handler) {
        handlerList.add(handler);
    }


    public AlternativeCLI() {
        warehouseManagement = new WarehouseManagement();
    }

    public Handler getInsertEventHandler() {
        return insertEventHandler;
    }

    public void setInsertEventHandler(Handler insertEventHandler) {
        this.insertEventHandler = insertEventHandler;
    }

    public Handler getRemoveEventHandler() {
        return removeEventHandler;
    }

    public void setRemoveEventHandler(Handler removeEventHandler) {
        this.removeEventHandler = removeEventHandler;
    }

    public Handler getUpdateEventHandler() {
        return updateEventHandler;
    }

    public void setUpdateEventHandler(Handler updateEventHandler) {
        this.updateEventHandler = updateEventHandler;
    }

    public Handler getReadEventHandler() {
        return readEventHandler;
    }

    public void setReadEventHandler(Handler readEventHandler) {
        this.readEventHandler = readEventHandler;
    }

    public Operator getCurrentCase() {
        return currentCase;
    }

    public void setCurrentCase(Operator currentCase) {
        this.currentCase = currentCase;
    }

    public WarehouseManagement getWarehouseManagement() {
        return warehouseManagement;
    }

    public void setWarehouseManagement(WarehouseManagement warehouseManagement) {
        this.warehouseManagement = warehouseManagement;
    }

    public String getCustomerDBPath() {
        return customerDBPath;
    }

    public void setCustomerDBPath(String customerDBPath) {
        this.customerDBPath = customerDBPath;
    }

    public String getCargoDBPath() {
        return cargoDBPath;
    }

    public void setCargoDBPath(String cargoDBPath) {
        this.cargoDBPath = cargoDBPath;
    }


    public void execute() {
        System.out.println("Please enter desired command to execute the program:");
        System.out.println(":c switch to INSERTION mode");
        System.out.println(":d switch to DELETE mode");
        System.out.println(":r switch to DISPLAY mode");
        System.out.println(":u switch to UPDATE mode");
        System.out.println(":p switch to PERSISTENCE mode");
        System.out.println(":e to quit the program");
        System.out.println("\n");
        System.out.println("Enter command here: ");
        try (Scanner s = new Scanner(System.in)) {
            do {
                try {
                    String input = s.nextLine().trim().toLowerCase();

                    if (input.startsWith(":")) {
                        switchCommand(input);
                    } else {
                        try {
                            executeCommand(input);
                        } catch (Exception e) {
                            System.out.println("Wrong option");
                        }

                    }

                } catch (StringIndexOutOfBoundsException e) {
                    System.out.println("Invalid command! Please enter a valid command.");

                }
            } while (true);
        }
    }

    private void executeCommand(String input) throws Exception {
        Scanner scanner = new Scanner(System.in);
        switch (currentCase) {
            case INSERT:
                if (input.equalsIgnoreCase("cargo")) {
                    System.out.println("Please enter cargo details: ");
                    String cargoDetails = scanner.nextLine();
                    if (cargoDetails.startsWith(":")) {
                        switchCommand(cargoDetails);
                        break;
                    }

                    CargoCreateEvent cargoCreateEvent = new CargoCreateEvent(this, cargoDetails);
                    handlerList.get(0).handle(cargoCreateEvent);

                } else if (input.equalsIgnoreCase("customer")) {
                    System.out.println("Please enter Customer name: ");
                    String customerName = scanner.nextLine();
                    if (customerName.startsWith(":")) {
                        switchCommand(customerName);
                        break;
                    }

                    CustomerCreateEvent customerCreateEvent = new CustomerCreateEvent(this, customerName);
                    handlerList.get(4).handle(customerCreateEvent);

                } else {
                    System.out.println("Invalid option");
                }
                break;
            case REMOVE:
                if (input.equalsIgnoreCase("cargo")) {

                    try {
                        System.out.println("Please enter cargo details: ");
                        String cargoDetails = scanner.nextLine();
                        if (cargoDetails.startsWith(":")) {
                            switchCommand(cargoDetails);
                            break;
                        }
                        CargoDeleteEvent cargoDeleteEvent = new CargoDeleteEvent(this, cargoDetails);
                        this.handlerList.get(1).handle(cargoDeleteEvent);
                    } catch (Exception e) {
                        System.out.println("Something went wrong");
                    }

                } else if (input.equalsIgnoreCase("customer")) {
                    System.out.println("Please enter Customer name: ");
                    String customerName = scanner.nextLine();
                    if (customerName.startsWith(":")) {
                        switchCommand(customerName);
                        break;
                    }
                    CustomerDeleteEvent customerDeleteEvent = new CustomerDeleteEvent(this, customerName);
                    this.handlerList.get(5).handle(customerDeleteEvent);

                } else {
                    System.out.println("Invalid option");
                }
                break;
            case INSPECT:

                CargoUpdateEvent cargoUpdateEvent = new CargoUpdateEvent(this, input);
                this.handlerList.get(2).handle(cargoUpdateEvent);
                break;

            case READ:
                if (input.contains("cargo") || input.contains("Cargo")) {

                    String[] cargos = input.split(" ");
                    String type = "";
                    if (cargos.length > 1) {
                        type = cargos[1];
                    }

                    CargoReadEvent cargoReadEvent = new CargoReadEvent(this, type);
                    this.handlerList.get(3).handle(cargoReadEvent);

                } else if (input.equalsIgnoreCase("customer")) {

                    CustomerReadEvent customerReadEvent = new CustomerReadEvent(this, input);
                    this.handlerList.get(6).handle(customerReadEvent);


                } else if (input.equalsIgnoreCase("hazards i")) {
                    HazardIReadEvent hazardIReadEvent = new HazardIReadEvent(this, input);
                    this.handlerList.get(11).handle(hazardIReadEvent);

                } else if (input.equalsIgnoreCase("hazards e")) {

                    HazardEReadEvent hazardEReadEvent = new HazardEReadEvent(this, input);
                    this.handlerList.get(12).handle(hazardEReadEvent);
                } else {
                    System.out.println("Invalid option");
                }
                break;
            case PERSISTENCE:


                try {
                    if (input.equals("1")) {

                        try {
                            SaveJosEvent saveJosEvent = new SaveJosEvent(this, input);
                            this.handlerList.get(7).handle(saveJosEvent);
                            System.out.println("Data saved through JOS");
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println("some error occurred during save JOS");
                        }


                    }
                    if (input.equals("2")) {

                        try {
                            InitializeJosEvent initializeJosEvent = new InitializeJosEvent(this, input);
                            this.handlerList.get(10).handle(initializeJosEvent);
                            System.out.println("data initialized thorough JOS");
                        } catch (Exception e) {
                            System.out.println("some error occurred ");
                        }


                    }
                    if (input.equals("3")) {

                        try {
                            SaveJBPEvent saveJBPEvent = new SaveJBPEvent(this, input);
                            this.handlerList.get(8).handle(saveJBPEvent);
                            System.out.println("data saved through JBP");
                        } catch (Exception e) {
                            System.out.println("Some error occurred in saving data through JBP");
                        }


                    }
                    if (input.equals("4")) {

                        try {
                            InitializeJBPEvent initializeJBPEvent = new InitializeJBPEvent(this, input);
                            this.handlerList.get(9).handle(initializeJBPEvent);
                            System.out.println("data initialized through JBP");
                        } catch (Exception e) {
                            System.out.println("error in initializing data though JBP");
                        }


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case QUIT:
                System.exit(0);
                break;
            default:
                System.out.println("Invalid input - execute command");
        }
    }

    public void switchCommand(String input) {

        Command command = new Command(input);


        switch (command.operator) {
            case INSERT:
                currentCase = Operator.INSERT;
                System.out.println("Switched to " + command.operator + " mode");
                System.out.println("Choose an option: ");
                System.out.println("Cargo");
                System.out.println("Customer");
                break;
            case REMOVE:
                currentCase = Operator.REMOVE;
                System.out.println("Switched to " + command.operator + " mode");
                System.out.println("Choose an option: ");
                System.out.println("Cargo");

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

                break;
            case QUIT:
                System.out.println("\u001B[35m Exit .. \u001B[0m");
                System.exit(0);
                break;

            case PERSISTENCE:
                currentCase = Operator.PERSISTENCE;

                System.out.println("Switched to " + command.operator + " mode");
                System.out.println("type command :\n 1.Save JOS \n 2.Load JOS\n 3.Save JBP\n 4.Load JBP");
                break;
            default:
                System.out.println("Error");

        }

    }


}
