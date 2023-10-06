package Interfaces;

import administration.Customer;

import java.io.Serializable;
import java.util.Collection;

public interface CustomerStorageInterface extends Serializable {
    void addCustomer(Customer customer);

    void updateCustomer(Customer customer);

    void deleteCustomer(Customer customer);

    Customer getCustomer(String name);

    Collection<Customer> getAllCustomers();
}
