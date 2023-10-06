package warehouse;

import administration.Customer;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class WarehouseCustomer implements Customer, Serializable {

    private String name;
    private Set<String> customers;

    public WarehouseCustomer() {
    }

    public WarehouseCustomer(String name) {
        this.name = name;
        customers = new HashSet<>();
    }


    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getCustomers() {
        return customers;
    }

    public void setCustomers(Set<String> customers) {
        this.customers = customers;
    }

}
