package eventListeners;

import eventSystem.CargoReadEvent;
import eventSystem.CustomerReadEvent;
import eventSystem.Listener;
import javafx.util.Pair;
import warehouse.WarehouseManagement;

import java.util.Collection;

public class CustomerReadListenerImpl implements Listener<CustomerReadEvent> {

    private WarehouseManagement warehouseManagement;


    public CustomerReadListenerImpl(WarehouseManagement warehouseManagement) {
        this.warehouseManagement = warehouseManagement;
    }

    public void onCRUDevent(CargoReadEvent event) {

        System.out.println("read customer listener");
    }

    @Override
    public void onCRUDevent(CustomerReadEvent event) throws ClassNotFoundException {
        Collection<Pair<String, Integer>> customersWithTotalCargo = warehouseManagement.getCustomersWithTotalCargo();
        //todo print customersWithTotalCargo --> check
        for (Pair<String, Integer> customer : customersWithTotalCargo) {
            System.out.println("Customer: " + customer.getKey() + ", Total Cargo: " + customer.getValue());
        }
    }
}
