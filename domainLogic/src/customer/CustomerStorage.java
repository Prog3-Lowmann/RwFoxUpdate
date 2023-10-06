package customer;

import Interfaces.CustomerStorageInterface;
import administration.Customer;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CustomerStorage implements CustomerStorageInterface, Serializable {
    /*
   * https://howtodoinjava.com/java/serialization/serialversionuid/
   * https://mkyong.com/intellij/how-to-generate-serialversionuid-in-intellij-idea/
   * https://www.geeksforgeeks.org/serialversionuid-in-java/
   * */

    private static final long serialVersionUID = 11111L;

    private Map<String, Customer> storage; // to store customer name -> Customer Obj
    public CustomerStorage(){
        storage = new HashMap<String, Customer>();
    }

    @Override
    public void addCustomer(Customer customer) {
        storage.put(customer.getName(), customer);
    }

    @Override
    public void updateCustomer(Customer customer) {
        storage.put(customer.getName(),customer);
    }

    @Override
    public void deleteCustomer(Customer customer) {
        storage.remove(customer.getName());
    }

    @Override
    public Customer getCustomer(String name) {
        return storage.get(name);
    }

    @Override
    public Collection<Customer> getAllCustomers() {
        return storage.values();
    }

    public Map<String, Customer> getStorage(){
        return storage;
    }

    public void setStorage(Map<String, Customer> storage){
        this.storage = storage;
    }

}
