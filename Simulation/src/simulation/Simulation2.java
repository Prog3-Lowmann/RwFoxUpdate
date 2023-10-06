package simulation;/*
public class simulation.Simulation2 {
    private static Warehouse warehouse;

    public static void main(String[] args) {
        int n = 5; //Thread number
        warehouse = new Warehouse(10);

        List<Thread> insertThreads = new ArrayList<>();
        List<Thread> deleteThreads = new ArrayList<>();

        for (int i = 0; i < n; i++) {
        Thread insertThread = new Thread(() -> {
            Random random = new Random();
            while (true) {
                synchronized (warehouse) {
                    String cargoType = generateRandomCargoType();
                    Date lastInspectionDate = new Date();
                    int StorageLocation = random.nextInt(warehouse.getCapacity());
                    Customer customer = generateRandomCustomerName();
                    BigDecimal value = generateRandomValue();
                    boolean isFragile = random.nextBoolean();
                    int grainSize = random.nextInt(100);
                    Collection<Hazard> hazards = generateRandomHazards();
                    //    Duration durationOfStorage = Duration.ofMinutes(1);
                    long durationOfStorage = TimeUnit.MINUTES.toMillis(1);

                    //    Cargo cargo = new DryBulkCargoImpl( customer, value, lastInspectionDate,  storageDate,  hazards, grainSize);

                    Cargo cargo = new DryBulkCargoImpl(customer, value, lastInspectionDate, new Date(), hazards, grainSize);

                    if (warehouse.insertCargo(cargo))
                        System.out.println("cargo was inserted");
                    else
                        System.out.println("cargo wasn't inserted");
                    //System.out.println(cargo);
                }
                try {
                    Thread.sleep(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });



        Thread deleteThread = new Thread(() -> {
            Random random = new Random();
            while (true) {
                synchronized (warehouse) {
                    Map<Integer, Cargo> cargoMap = warehouse.readAll();
                    //    List<Cargo> cargoList = new ArrayList<Cargo>(warehouse.readAll().values());
                    if (!cargoMap.isEmpty()) {
                        //        int location = cargoMap.get(random.nextInt(cargoList.size())).getStorageLocation();
                        //        int location = new ArrayList<>(cargoMap.keySet()).get(random.nextInt(cargoMap.size()));
                        //        boolean status = warehouse.deleteCargo(location);
                        //        System.out.println("deleted status: " + status);
                        //System.out.println(" Entry deleted on: " + location);
                        List<Integer> locationList = new ArrayList<>(cargoMap.keySet());
                        int randomIndex = random.nextInt(locationList.size());
                        int location = locationList.get(randomIndex);

                        warehouse.deleteCargo(location);

                        System.out.println("Cargo deleted from location: " + location);
                    }
                }

                try {
                    Thread.sleep(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });



        insertThread.start();
        deleteThread.start();
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

 */

import administration.Customer;
import cargo.Cargo;
import cargo.Hazard;
import warehouse.WarehouseCustomer;
import warehouse.WarehouseManagement;

import java.math.BigDecimal;
import java.util.*;


public class Simulation2 {
    private static WarehouseManagement warehouseManagement;

    public void startSimulation() {
        Simulation2 simulation = new Simulation2();

        System.out.println("Enter number of thread");
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        List<InsertThread> insertThreadList = new ArrayList<>();
        List<RetrieveThread> retrieveThreadList = new ArrayList<>();
        for(int i =0; i < n;i++ ) {
            InsertThread insertThread = simulation.new InsertThread();
            insertThreadList.add(insertThread);
        }
        for(int i =0; i < n;i++ ) {
            RetrieveThread retrieveThread = simulation.new RetrieveThread();
            retrieveThreadList.add(retrieveThread);
        }

        for(int i = 0 ; i < n ;i++) {
            insertThreadList.get(i).start();;
            retrieveThreadList.get(i).start();
        }

    }

    private class RetrieveThread extends Thread{

        public void run() {
            while (true) {
                retrieveCargo();
                try {
                    Thread.sleep(0);  // Simulate delay: new Random().nextInt(5000) + 1000
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
            System.out.println("cargo removed");
        }


    }

    public Simulation2() {
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




