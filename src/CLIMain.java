import observers.Interfaces.Observer;
import eventListeners.*;
import eventSystem.*;
import observers.AvailableHazardObserver;
import observers.CapacityObserver;
import warehouse.WarehouseManagement;

import viewControler.Console;

import java.util.Collections;


public class CLIMain {

    public static void main(String[] args) {

        Console console = new Console();




        WarehouseManagement warehouseManagement = new WarehouseManagement();
        if(args.length == 1) {
            if(args[0].equals("TCP") || args[0].equals("tcp")) {
                
                TCPClient client = new TCPClient();
                client.start();
            }
            else if(args[0].equals("UDP") || args[0].equals("udp")) {
                UDPClient client = new UDPClient();
                client.start();
            }
            else {
                try {
                    int value = Integer.valueOf(args[0]);
                    warehouseManagement.getWarehouse().setCapacity(value);
                }
                catch (Exception e) {

                }
            }
        }
        Observer capacityObserver = new CapacityObserver(warehouseManagement);
        Observer hazardObserver = new AvailableHazardObserver(warehouseManagement);
        warehouseManagement.getWarehouse().setObservers(Collections.singletonList(capacityObserver));
        warehouseManagement.getWarehouse().setObservers(Collections.singletonList(hazardObserver));

        console.setWarehouseManagement(warehouseManagement);




        Handler<CargoCreateEvent> cargoCreateEventHandler = new Handler<>();
        Handler<CargoDeleteEvent> cargoDeleteEventHandler = new Handler<>();
        Handler<CargoUpdateEvent> cargoUpdateEventHandler = new Handler<>();
        Handler<CargoReadEvent> cargoReadEventHandler = new Handler<>();
        Handler<CustomerCreateEvent> customerCreateEventHandler = new Handler<>();
        Handler<CustomerReadEvent> customerReadEventHandler = new Handler<>();
        Handler<CustomerDeleteEvent> customerDeleteEventHandler = new Handler<>();
        Handler<SaveJBPEvent> saveJBPEventHandler = new Handler<>();
        Handler<SaveJosEvent> saveJosEventHandler = new Handler<>();
        Handler<InitializeJBPEvent> initializeJBPEventHandler = new Handler<>();
        Handler<InitializeJosEvent> initializeJosEventHandler = new Handler<>();
        Handler<HazardIReadEvent> hazardIReadEventHandler = new Handler<>();
        Handler<HazardEReadEvent> hazardEReadEventHandler = new Handler<>();


        Listener<CargoCreateEvent> cargoCreateListener = new CargoCreateListenerImpl(warehouseManagement);
        Listener<CargoDeleteEvent> cargoDeleteListener = new CargoDeleteListenerImpl(warehouseManagement);
        Listener<CargoUpdateEvent> cargoUpdateListener = new CargoUpdateListenerImpl(warehouseManagement);
        Listener<CargoReadEvent> cargoReadListener = new CargoReadListenerImpl(warehouseManagement);
        Listener<CustomerCreateEvent> customerCreateListener = new CustomerCreateListenerImpl(warehouseManagement);
        Listener<CustomerDeleteEvent> customerDeleteListener = new CustomerDeleteListenerImpl(warehouseManagement);
        Listener<CustomerReadEvent> customerReadListener = new CustomerReadListenerImpl(warehouseManagement);
        Listener<SaveJosEvent> saveJosListener = new SaveJosListenerImpl(warehouseManagement);
        Listener<SaveJBPEvent> saveJBPListener = new SaveJBPListenerImpl(warehouseManagement);
        Listener<InitializeJosEvent> initializeJosListener = new InitializeJosListenerImpl(warehouseManagement);
        Listener<InitializeJBPEvent> initializeJBPListener = new InitializeJBPListenerImpl(warehouseManagement);
        Listener<HazardIReadEvent> hazardIReadEventListener = new HazardIListenerImpl(warehouseManagement);
        Listener<HazardEReadEvent> hazardEReadEventListener = new HazardEListenerImpl(warehouseManagement);




        cargoCreateEventHandler.add(cargoCreateListener);
        cargoDeleteEventHandler.add(cargoDeleteListener);
        cargoUpdateEventHandler.add(cargoUpdateListener);
        cargoReadEventHandler.add(cargoReadListener);
        customerCreateEventHandler.add(customerCreateListener);
        customerDeleteEventHandler.add(customerDeleteListener);
        customerReadEventHandler.add(customerReadListener);
        saveJosEventHandler.add(saveJosListener);
        saveJBPEventHandler.add(saveJBPListener);
        initializeJosEventHandler.add(initializeJosListener);
        initializeJBPEventHandler.add(initializeJBPListener);
        hazardIReadEventHandler.add(hazardIReadEventListener);
        hazardEReadEventHandler.add(hazardEReadEventListener);

        console.addHandler(cargoCreateEventHandler); //0
        console.addHandler(cargoDeleteEventHandler); //1
        console.addHandler(cargoUpdateEventHandler); //2
        console.addHandler(cargoReadEventHandler); //3
        console.addHandler(customerCreateEventHandler); //4
        console.addHandler(customerDeleteEventHandler); //5
        console.addHandler(customerReadEventHandler); //6
        console.addHandler(saveJosEventHandler); //7
        console.addHandler(saveJBPEventHandler); //8
        console.addHandler(initializeJBPEventHandler); //9
        console.addHandler(initializeJosEventHandler); //10
        console.addHandler(hazardIReadEventHandler);//11
        console.addHandler(hazardEReadEventHandler);//12



        console.execute();
    }
}
