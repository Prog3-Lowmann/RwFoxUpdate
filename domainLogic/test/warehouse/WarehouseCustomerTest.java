package warehouse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

class WarehouseCustomerTest {
    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link WarehouseCustomer#WarehouseCustomer()}
     *   <li>{@link WarehouseCustomer#setCustomers(Set)}
     *   <li>{@link WarehouseCustomer#setName(String)}
     *   <li>{@link WarehouseCustomer#getCustomers()}
     *   <li>{@link WarehouseCustomer#getName()}
     * </ul>
     */
    @Test
    void testConstructor() {
        WarehouseCustomer actualWarehouseCustomer = new WarehouseCustomer();
        HashSet<String> stringSet = new HashSet<>();
        actualWarehouseCustomer.setCustomers(stringSet);
        actualWarehouseCustomer.setName("Name");
        assertSame(stringSet, actualWarehouseCustomer.getCustomers());
        assertEquals("Name", actualWarehouseCustomer.getName());
    }

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link WarehouseCustomer#WarehouseCustomer(String)}
     *   <li>{@link WarehouseCustomer#setCustomers(Set)}
     *   <li>{@link WarehouseCustomer#setName(String)}
     *   <li>{@link WarehouseCustomer#getCustomers()}
     *   <li>{@link WarehouseCustomer#getName()}
     * </ul>
     */
    @Test
    void testConstructor2() {
        WarehouseCustomer actualWarehouseCustomer = new WarehouseCustomer("Name");
        HashSet<String> stringSet = new HashSet<>();
        actualWarehouseCustomer.setCustomers(stringSet);
        actualWarehouseCustomer.setName("Name");
        assertSame(stringSet, actualWarehouseCustomer.getCustomers());
        assertEquals("Name", actualWarehouseCustomer.getName());
    }
}

