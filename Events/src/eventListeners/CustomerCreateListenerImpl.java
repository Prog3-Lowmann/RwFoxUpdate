package eventListeners;

import administration.Customer;
import eventSystem.CustomerCreateEvent;
import eventSystem.Listener;
import warehouse.WarehouseManagement;

public class CustomerCreateListenerImpl implements Listener<CustomerCreateEvent> {
    private WarehouseManagement warehouseManagement;

    public CustomerCreateListenerImpl(WarehouseManagement warehouseManagement) {
        this.warehouseManagement = warehouseManagement;
    }


    @Override
    public void onCRUDevent(CustomerCreateEvent event) throws ClassNotFoundException {
        Customer existingCustomer = this.warehouseManagement.getCustomerStorage().getCustomer(event.getCustomerDetails());

        if (existingCustomer != null) {
            System.out.println("Customer with this name already exists");
        } else {
            Customer customer = this.warehouseManagement.insertCustomer(event.getCustomerDetails());
            if (customer != null) {
                System.out.println("Customer " + customer.getName() + " has been created");
            }
        }
    }
}
