package eventListeners;

import eventSystem.CustomerDeleteEvent;
import eventSystem.Listener;
import warehouse.WarehouseManagement;

public class CustomerDeleteListenerImpl implements Listener<CustomerDeleteEvent> {
    private WarehouseManagement warehouseManagement;

    public CustomerDeleteListenerImpl(WarehouseManagement warehouseManagement) {
        this.warehouseManagement = warehouseManagement;
    }

    @Override
    public void onCRUDevent(CustomerDeleteEvent event) throws ClassNotFoundException {
        this.warehouseManagement.removeCustomer(event.getCustomerDetails());
    }
}
