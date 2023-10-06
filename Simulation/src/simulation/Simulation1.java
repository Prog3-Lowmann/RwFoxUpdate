package simulation;

import administration.Customer;
import cargo.Cargo;
import cargo.Hazard;
import warehouse.WarehouseCustomer;
import warehouse.WarehouseManagement;

import java.math.BigDecimal;
import java.util.*;


public class Simulation1 {
    private static WarehouseManagement warehouseManagement;

     private class RetrieveThread extends Thread{

        public void run() {
            while (true) {
                retrieveCargo();
                try {
                    Thread.sleep(0);  // in case of simulate delay: new Random().nextInt(5000) + 1000
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

     private class InsertThread extends Thread{

        public void run() {
            while (true) {
                try {
                    insertCargo();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                try {
                    Thread.sleep(new Random().nextInt(5000) + 100);  // Simulate delay
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void insertCargo() throws Exception {
         Random random = new Random();
         String customerName = "customer" + random.nextInt();
         warehouseManagement.insertCustomer(customerName);
         warehouseManagement.insertCargo("LiquidAndDryBulkCargo " +customerName+" 4004,50 flammable,toxic true 10");
         System.out.println("cargo inserted");


    }

    private void retrieveCargo() {
         Map<Integer,Cargo> cargos =  warehouseManagement.getWarehouse().readAll();
         System.out.println(cargos);
         Set<Integer> sets = cargos.keySet();


         if(!sets.isEmpty()) {
             int randomIndex = new Random().nextInt(sets.size());
             Integer[] integerArray = sets.toArray(new Integer[0]);
             Integer randomInteger = integerArray[randomIndex];
             warehouseManagement.removeCargo(String.valueOf(randomInteger));
         }


    }

    public Simulation1() {
         warehouseManagement = new WarehouseManagement();
    }


    //for other testing purposes is the methods below.
    private static String generateRandomCargoType() {
        String[] cargoTypes = {"DryBulkCargo", "LiquidBulkCargo", "DryBulkAndUnitisedCargo", "LiquidAndDryBulkCargo", "LiquidBulkAndUnitisedCargo", "UnitisedCargo"};
        Random random = new Random();
        int index = random.nextInt(cargoTypes.length);
        return cargoTypes[index];
    }

    private static Customer generateRandomCustomerName() {
        String[] customerNames = { "Alice", "Bob", "John", "David", "Alien", "Emily"};
        Random random = new Random();
        int index = random.nextInt(customerNames.length);
        String randomName = customerNames[index];
        return new WarehouseCustomer(randomName);
    }

    private static BigDecimal generateRandomValue() {
        Random random = new Random();
        double value = random.nextDouble() * 1000;
        return BigDecimal.valueOf(value);
    }

    private static Collection<Hazard> generateRandomHazards() {
        List<Hazard> allHazards = Arrays.asList(Hazard.values());
        Collections.shuffle(allHazards);
        // Generate a random number of hazards (up to allHazards.size())
        Random random = new Random();
        //int index = random.nextInt(10);
        int numHazards = random.nextInt(allHazards.size() + 1);

        // Select the first numHazards from the shuffled list as the random hazards
        List<Hazard> randomHazards = allHazards.subList(0, numHazards);

        return randomHazards;
    }
    public void startSimulation() {
        Simulation1 simulation = new Simulation1();
        InsertThread insertThread = simulation.new InsertThread();
        RetrieveThread retrieveThread = simulation.new RetrieveThread();

        insertThread.start();
    }
    }





