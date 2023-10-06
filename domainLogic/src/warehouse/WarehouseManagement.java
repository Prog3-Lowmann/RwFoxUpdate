package warehouse;

import Interfaces.CustomerStorageInterface;
import Interfaces.WarehouseInterface;
import administration.Customer;
import administration.Storable;
import cargo.*;
import customer.CustomerStorage;
import javafx.util.Pair;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

//facade classe

public class WarehouseManagement implements Serializable {
    private WarehouseInterface warehouse;
    private CustomerStorageInterface customerStorage;


    public WarehouseManagement() {
        warehouse = new Warehouse();
        customerStorage = new CustomerStorage();
    }

    public WarehouseManagement(WarehouseInterface warehouse) {
        this.warehouse = warehouse;
        customerStorage = new CustomerStorage();
    }

    public WarehouseManagement(CustomerStorageInterface customerStorage) {
        this.customerStorage = customerStorage;
    }

    public WarehouseManagement(WarehouseInterface warehouse, CustomerStorageInterface customerStorage) {
        this.warehouse = warehouse;
        this.customerStorage = customerStorage;
    }


    public WarehouseInterface getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(WarehouseInterface warehouse) {
        this.warehouse = warehouse;
    }

    public CustomerStorageInterface getCustomerStorage() {
        return customerStorage;
    }

    public void setCustomerStorage(CustomerStorageInterface customerStorage) {
        this.customerStorage = customerStorage;
    }

    public List<Cargo> readAllCargo() {
        return new ArrayList<>(warehouse.readAll().values());
    }

    public Set<Hazard> getIncludedHazards() {
        Set<Hazard> includedHazards = new HashSet<>();
        List<Cargo> warehouse = readAllCargo();

        for (Cargo cargo : warehouse) {
            includedHazards.addAll(cargo.getHazards());
        }

        return includedHazards;
    }

    public Set<Hazard> getExcludedHazards() {
        Set<Hazard> includedHazards = this.getIncludedHazards();
        Set<Hazard> excludedHazards = new HashSet<>(Arrays.asList(Hazard.values()));
        excludedHazards.removeAll(includedHazards);

        if (excludedHazards.size() == 0) {
            return null;
        }
        return excludedHazards;
    }

    @Override
    public String toString() {
        return "WarehouseManagement{" +
                "warehouse=" + warehouse +
                ", customerStorage=" + customerStorage +
                '}';
    }

    public Collection<Cargo> readCargoByType(String input) throws ClassNotFoundException { //Collection<Storable> readCargoByType
        Class classtype = Class.forName(input);
        Collection<Cargo> items = warehouse.readAll().values().stream().filter(storable -> {
            if (storable.getClass().getCanonicalName().contains(classtype.getCanonicalName()))
                return true;
            else
                return false;
        }).collect(Collectors.toList());
        return items;
    }

    public Collection<Pair<String, Integer>> getCustomersWithTotalCargo() {
        Collection<Pair<String, Integer>> customersWithTotalCargo = new ArrayList();
        Collection<Customer> customers = customerStorage.getAllCustomers();
        for (Customer customer : customers) {
            Collection<Cargo> cargo = warehouse.getAllCustomerCargo(customer);
            customersWithTotalCargo.add(new Pair<>(customer.getName(), cargo.size()));
        }
        return customersWithTotalCargo;
    }

    public void inspectCargo(String input) {
        String[] args = input.split(" ");
        if (args.length == 1) {
            int storageLocation = Integer.parseInt(args[0]);
            Cargo cargo = warehouse.getCargo(storageLocation);

            if (cargo == null) {
                System.out.println("Cannot find cargo at location: " + storageLocation);
            } else {
                warehouse.updateCargo(storageLocation);
                System.out.println("Cargo on position: " + storageLocation + " has been updated");
            }


        }
    }

    public void removeCargo(String input) {
        String[] args = input.split(" ");
        if (args.length == 1) {
            int storageLocation = Integer.parseInt(args[0]);
            warehouse.deleteCargo(storageLocation);
            updateCargoStorageLocation();
            System.out.println("Cargo on position: " + storageLocation + " has been deleted");
        }

    }


    public void insertCargo(String input) throws Exception {


        if (warehouse.readAll().size() >= warehouse.getCapacity()) {
            System.out.println("Capacity Reached cargo cannot be inserted..");
            return;
        }
        // System.out.println("insert cargo method called");
        // System.out.println(input);
        String[] args = input.trim().split(" ");


        String cargoType = args[0].trim().toLowerCase();
        String customerName = args[1].trim();
        //System.out.println(args[2].replaceAll(",",".").trim());
        BigDecimal value = new BigDecimal(args[2].replaceAll(",", ".").trim());
        String[] hazardStrings = args[3].split(",");

        List<Hazard> hazardList = new ArrayList<>();

        for (String hazard : hazardStrings) {
            switch (hazard) {
                case "explosive":
                    hazardList.add(Hazard.explosive);
                    break;
                case "flammable":
                    hazardList.add(Hazard.flammable);
                    break;
                case "toxic":
                    hazardList.add(Hazard.toxic);
                    break;
                case "radioactive":
                    hazardList.add(Hazard.radioactive);
                    break;
            }
        }

        switch (cargoType) {
            case "drybulkcargo":
                int grainSize = parseInt(args[4]);

                Customer customer = customerStorage.getCustomer(customerName);
                if (customer == null) {
                    System.out.println("customer: " + customer + " not found please create it first");
                    throw new Exception("customer: " + customer + " not found please create it first");
                }
                DryBulkCargo dryBulkCargo = new DryBulkCargoImpl(customer, new Date(), new Date(), value, hazardList, grainSize);
                warehouse.addCargo(dryBulkCargo);
                System.out.println("Created a new drybulkcargo with owner: " + customer.getName());
                break;
            case "drybulkandunitisedcargo":
                grainSize = parseInt(args[4]);
                boolean fragile = Boolean.parseBoolean(args[5]);
                customer = customerStorage.getCustomer(customerName);
                if (customer == null) {
                    System.out.println("customer: " + customer + " not found please create it first");
                    throw new Exception("customer: " + customer + " not found please create it first");
                }
                DryBulkAndUnitisedCargo dryBulkAndUnitisedCargo = new DryBulkAndUnitizedCargoImpl(customer, new Date(), new Date(), value, hazardList, fragile, grainSize);
                warehouse.addCargo(dryBulkAndUnitisedCargo);
                System.out.println("Created a new dryBulkAndUnitisedCargo with owner: " + customer.getName());
                break;
            case "liquidanddrybulkcargo":
                //System.out.println("customer size" + this.customerStorage.getAllCustomers().size());
                grainSize = parseInt(args[5]);
                boolean pressurized = Boolean.parseBoolean(args[4]);
                customer = customerStorage.getCustomer(customerName);

                if (customer == null) {
                    System.out.println("customer : " + customer + " not found please create it first");
                    throw new Exception("customer : " + customer + " not found please create it first");
                }
                LiquidAndDryBulkCargo liquidAndDryBulkCargo = new LiquidAndDryBulkCargoImpl(customer, new Date(), new Date(), value, hazardList, grainSize, pressurized);
                warehouse.addCargo(liquidAndDryBulkCargo);
                System.out.println("Created a new liquidAndDryBulkCargo with owner: " + customer.getName());
                break;

            case "liquidbulkandunitisedcargo":
                pressurized = Boolean.parseBoolean(args[4]);
                fragile = Boolean.parseBoolean(args[5]);
                customer = customerStorage.getCustomer(customerName);
                if (customer == null) {
                    System.out.println("customer: " + customer + " not found please create it first");
                    throw new Exception("customer: " + customer + " not found please create it first");
                }
                LiquidBulkAndUnitisedCargo liquidBulkAndUnitisedCargo = new LiquidBulkAndUnitizedCargoImpl(customer, new Date(), new Date(), value, hazardList, pressurized, fragile);
                warehouse.addCargo(liquidBulkAndUnitisedCargo);
                System.out.println("Created a new liquidBulkAndUnitisedCargo with owner: " + customer.getName());
                break;
            case "liquidbulkcargo":
                System.out.println("size= " + customerStorage.getAllCustomers().size());
                pressurized = Boolean.parseBoolean(args[4]);
                customer = customerStorage.getCustomer(customerName);
                if (customer == null) {
                    System.out.println("customer: " + customer + " not found please create it first");
                    throw new Exception("customer: " + customer + " not found please create it first");
                }
                LiquidBulkCargo liquidBulkCargo = new LiquidBulkCargoImpl(customer, new Date(), new Date(), value, hazardList, pressurized);
                warehouse.addCargo(liquidBulkCargo);
                System.out.println("Created a new liquidBulkCargo with owner: " + customer.getName());
                break;
            case "unitisedcargo":
                fragile = Boolean.parseBoolean(args[4]);
                customer = customerStorage.getCustomer(customerName);
                if (customer == null) {
                    System.out.println("customer: " + customer + " not found please create it first");
                    throw new Exception("customer: " + customer + " not found please create it first");
                }
                UnitisedCargo unitisedCargo = new UnitisedCargoImpl(customer, new Date(), new Date(), value, hazardList, fragile);
                warehouse.addCargo(unitisedCargo);
                System.out.println("Created a new unitisedCargo with owner: " + customer.getName());
                break;

        }

    }


    public Customer insertCustomer(String input) {

        String[] args = input.split(" ");
        if (args.length == 1) {
            String customerName = args[0];
            Customer customer = new WarehouseCustomer(customerName);
            this.getCustomerStorage().addCustomer(customer);
            return customer;
        }

        return null;
    }


    public void updateCargoStorageLocation() {
        this.getWarehouse().updateCargoStorageLocation();
    }

    public void removeCustomer(String input) {
        String[] args = input.split(" ");
        if (args.length == 1) {
            String customerName = args[0];
            Customer customer = getCustomerStorage().getCustomer(customerName);
            if (customer == null) {
                System.out.println("Customer does not exist");
                return;
            }
            Collection<Cargo> cargo = warehouse.getAllCustomerCargo(customer);
            for (Storable item : cargo) {
                warehouse.deleteCargo(item.getStorageLocation());
            }
            this.getCustomerStorage().deleteCustomer(customer);
            updateCargoStorageLocation();
            System.out.println("customer " + input + " successfully deleted");
        }
    }

    public List<Cargo> getCargoOfTye(String type) {
        if (type.equals("unitisedcargo")) {
            return readAllCargo().stream().filter((cargo) -> cargo instanceof UnitisedCargoImpl).collect(Collectors.toList());
        }
        if (type.equals("drybulkcargo")) {
            return readAllCargo().stream().filter((cargo) -> cargo instanceof DryBulkCargoImpl).collect(Collectors.toList());
        }
        if (type.equals("liquidbulkcargo")) {
            return readAllCargo().stream().filter((cargo) -> cargo instanceof LiquidAndDryBulkCargo).collect(Collectors.toList());
        }
        if (type.equals("drybulkandunitisedcargo")) {
            return readAllCargo().stream().filter((cargo) -> cargo instanceof DryBulkAndUnitizedCargoImpl).collect(Collectors.toList());
        }
        if (type.equals("liquidanddrybulkcargo")) {
            return readAllCargo().stream().filter((cargo) -> cargo instanceof LiquidAndDryBulkCargo).collect(Collectors.toList());
        }
        if (type.equals("liquidbulkanddrybulkcargo")) {
            return readAllCargo().stream().filter((cargo) -> cargo instanceof LiquidAndDryBulkCargo).collect(Collectors.toList());
        } else {
            return null;
        }
    }
}
