package simulation;

import administration.Customer;
import cargo.Cargo;
import cargo.Hazard;
import warehouse.WarehouseCustomer;
import warehouse.WarehouseManagement;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;




public class Simulation3 {
    private static WarehouseManagement warehouseManagement;

    private Object lock = new Object();

    public void startSimulation() {
        Simulation3 simulation = new Simulation3();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter number of insert threads : ");
        int noOfInsertThread = sc.nextInt();
        System.out.println("Enter no of delete thread : ");
        int noOfDeleteThread  = sc.nextInt();
        System.out.println("Enter of No of Inspect Thread : ");
        int noOfInspectThread = sc.nextInt();
        System.out.println("Enter interval time in mili seconds : ");
        int  time = sc.nextInt();

        simulation.startSimulation(noOfInsertThread,noOfDeleteThread,noOfInspectThread,time);

    }


    private class DisplayThread extends Thread {

        public void run() {
            while (true) {

                System.out.println("Current state of cargo container:");
                Collection<Cargo> cargos =  warehouseManagement.getWarehouse().readAll().values();
                if(cargos.size() == 0 || cargos == null) {
                    System.out.println("Warehouse is empty");
                }
                else {
                    for (Cargo cargoItem :cargos) {
                        System.out.println(cargoItem.getOwner() + " (Inspection Date: " + cargoItem.getLastInspectionDate() + ")");
                    }
                }


                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private class DeleteThread extends Thread{

        public void run() {
            while (true) {
                deleteCargo();
                try {
                    Thread.sleep(0);  // Simulate delay: new Random().nextInt(5000) + 1000
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void deleteCargo() {
        Map<Integer,Cargo> cargos =  warehouseManagement.getWarehouse().readAll();
        System.out.println(cargos);

        if(!cargos.isEmpty()) {
            List<Map.Entry<Integer, Cargo>> cargoEntries = new ArrayList<>(cargos.entrySet());
            cargoEntries.sort(Comparator.comparing(entry -> entry.getValue().getLastInspectionDate()));
            int key = cargoEntries.get(0).getKey();
            warehouseManagement.removeCargo(String.valueOf(key));

        }



    }

    private class InspectThread extends Thread{

        public void run() {
            while (true) {
                inspectThread();
                try {
                    Thread.sleep(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void startSimulation(int numInsertThreads, int numDeleteThreads, int numInspectThreads, int interval) {
        ExecutorService executorService = Executors.newFixedThreadPool(numInsertThreads + numDeleteThreads + numInspectThreads);
        executorService.execute(() -> displayStatePeriodically(interval));

        for (int i = 0; i < numInsertThreads; i++) {
            executorService.execute(() -> {
                while (true) {
                    synchronized (lock) {
                        try {
                            insertCargo();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        lock.notifyAll();
                    }

                    try {
                        Thread.sleep(0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        for (int i = 0; i < numDeleteThreads; i++) {
            executorService.execute(() -> {
                while (true) {
                    synchronized (lock) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        deleteCargo();
                        lock.notifyAll();
                    }
                }
            });
        }

        for (int i = 0; i < numInspectThreads; i++) {
            executorService.execute(() -> {
                while (true) {
                    synchronized (lock) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        try {
                            insertCargo();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        lock.notifyAll();
                    }
                }
            });
        }
    }

    private void displayStatePeriodically(int interval) {
        System.out.println("Current state of cargo container:");
        for (Cargo cargoItem : warehouseManagement.getWarehouse().readAll().values()) {
            System.out.println(cargoItem.getOwner() + " (Inspection Date: " + cargoItem.getLastInspectionDate() + ")");
        }

        try {
            Thread.sleep(interval);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void inspectThread() {
        Map<Integer,Cargo> cargos =  warehouseManagement.getWarehouse().readAll();
        System.out.println(cargos);
        Set<Integer> sets = cargos.keySet();


        if(!sets.isEmpty()) {
            int randomIndex = new Random().nextInt(sets.size());
            Integer[] integerArray = sets.toArray(new Integer[0]);
            Integer randomInteger = integerArray[randomIndex];
            warehouseManagement.inspectCargo(String.valueOf(randomInteger));
            warehouseManagement.inspectCargo(String.valueOf(randomInteger));
            System.out.println("cargo removed");
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
                    Thread.sleep(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void insertCargo() throws Exception {
        Random random = new Random();
        String customerName = "customer" + random.nextInt();

        if(warehouseManagement.getWarehouse().readAll().size() < warehouseManagement.getWarehouse().getCapacity()) {
            warehouseManagement.insertCustomer(customerName);
            warehouseManagement.insertCargo("LiquidAndDryBulkCargo " +customerName+" 4004,50 flammable,toxic true 10");
            System.out.println("cargo inserted");
        }
        else  {
            System.out.println("Warehouse is full");
        }




    }



    public Simulation3() {
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

}




