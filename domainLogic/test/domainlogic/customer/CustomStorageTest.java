package domainlogic.customer;

import administration.Customer;
import customer.CustomerStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import warehouse.WarehouseCustomer;

import java.util.Collection;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.spy;

public class CustomStorageTest {
    private CustomerStorage customerStorage;
    private CustomerStorage spyCustomerStorage;

    @BeforeEach
    void setUp() {
        customerStorage = new CustomerStorage();
        spyCustomerStorage = spy(customerStorage);
    }

    @Test
    void addCustomerTest() {
        Customer customer = new WarehouseCustomer("John Doe");
        spyCustomerStorage.addCustomer(customer);

        Map<String, Customer> storage = spyCustomerStorage.getStorage();
        assertEquals(1, storage.size());
        assertEquals(customer, storage.get("John Doe"));
    }

    @Test
    void updateCustomerTest() {
        Customer customer = new WarehouseCustomer("Jane Smith");
        spyCustomerStorage.addCustomer(customer);

        Customer updatedCustomer = new WarehouseCustomer("Jane Smith");
        spyCustomerStorage.updateCustomer(updatedCustomer);

        Map<String, Customer> storage = spyCustomerStorage.getStorage();
        assertEquals(1, storage.size());
        assertEquals(updatedCustomer, storage.get("Jane Smith"));
    }

    @Test
    void deleteCustomerTest() {
        Customer customer = new WarehouseCustomer("Mike Johnson");
        spyCustomerStorage.addCustomer(customer);

        spyCustomerStorage.deleteCustomer(customer);

        Map<String, Customer> storage = spyCustomerStorage.getStorage();
        assertTrue(storage.isEmpty());
    }

    @Test
    void getCustomerTest() {
        Customer customer = new WarehouseCustomer("Alice");
        spyCustomerStorage.addCustomer(customer);

        assertEquals(customer, spyCustomerStorage.getCustomer("Alice"));
        assertNull(spyCustomerStorage.getCustomer("Bob"));
    }

    @Test
    void getAllCustomersTest() {
        Customer customer1 = new WarehouseCustomer("Alice");
        Customer customer2 = new WarehouseCustomer("Bob");
        spyCustomerStorage.addCustomer(customer1);
        spyCustomerStorage.addCustomer(customer2);

        Collection<Customer> customers = spyCustomerStorage.getAllCustomers();

        assertEquals(2, customers.size());
        assertTrue(customers.contains(customer1));
        assertTrue(customers.contains(customer2));
    }
}
