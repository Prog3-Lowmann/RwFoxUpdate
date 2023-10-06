package eventListeners;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import administration.Customer;
import customer.CustomerStorage;
import eventSystem.CargoDeleteEvent;
import org.junit.jupiter.api.Test;
import warehouse.WarehouseManagement;

class CargoDeleteListenerImplTest {
    /**
     * Method under test: {@link CargoDeleteListenerImpl#CargoDeleteListenerImpl(WarehouseManagement)}
     */
    @Test
    void testConstructor() {

        new CargoDeleteListenerImpl(new WarehouseManagement());
    }

    /**
     * Method under test: {@link CargoDeleteListenerImpl#onCRUDevent(CargoDeleteEvent)}
     */
    @Test
    void testOnCRUDevent() {
        CargoDeleteListenerImpl cargoDeleteListenerImpl = new CargoDeleteListenerImpl(new WarehouseManagement());
        CargoDeleteEvent cargoDeleteEvent = new CargoDeleteEvent("Source", "Location");

        cargoDeleteListenerImpl.onCRUDevent(cargoDeleteEvent);
        assertEquals("Location", cargoDeleteEvent.getCargoDetails());
    }

    /**
     * Method under test: {@link CargoDeleteListenerImpl#onCRUDevent(CargoDeleteEvent)}
     */
    @Test
    void testOnCRUDevent2() {
        CargoDeleteListenerImpl cargoDeleteListenerImpl = new CargoDeleteListenerImpl(null);
        CargoDeleteEvent cargoDeleteEvent = new CargoDeleteEvent("Source", "Location");

        cargoDeleteListenerImpl.onCRUDevent(cargoDeleteEvent);
        assertEquals("Location", cargoDeleteEvent.getCargoDetails());
    }

    /**
     * Method under test: {@link CargoDeleteListenerImpl#onCRUDevent(CargoDeleteEvent)}
     */
    @Test
    void testOnCRUDevent3() {
        CargoDeleteListenerImpl cargoDeleteListenerImpl = new CargoDeleteListenerImpl(new WarehouseManagement());
        CargoDeleteEvent cargoDeleteEvent = new CargoDeleteEvent("Source", "Something wrong in removing cargo");

        cargoDeleteListenerImpl.onCRUDevent(cargoDeleteEvent);
        assertEquals("Something wrong in removing cargo", cargoDeleteEvent.getCargoDetails());
    }

    /**
     * Method under test: {@link CargoDeleteListenerImpl#onCRUDevent(CargoDeleteEvent)}
     */
    @Test
    void testOnCRUDevent4() {

        CargoDeleteListenerImpl cargoDeleteListenerImpl = new CargoDeleteListenerImpl(new WarehouseManagement());
        cargoDeleteListenerImpl.onCRUDevent(new CargoDeleteEvent("Source", "42"));
    }

    /**
     * Method under test: {@link CargoDeleteListenerImpl#onCRUDevent(CargoDeleteEvent)}
     */
    @Test
    void testOnCRUDevent5() {

        (new CargoDeleteListenerImpl(new WarehouseManagement())).onCRUDevent(null);
    }

    /**
     * Method under test: {@link CargoDeleteListenerImpl#onCRUDevent(CargoDeleteEvent)}
     */
    @Test
    void testOnCRUDevent6() {
        CargoDeleteListenerImpl cargoDeleteListenerImpl = new CargoDeleteListenerImpl(
                new WarehouseManagement(new CustomerStorage()));
        CargoDeleteEvent cargoDeleteEvent = new CargoDeleteEvent("Source", "42");

        cargoDeleteListenerImpl.onCRUDevent(cargoDeleteEvent);
        assertEquals("42", cargoDeleteEvent.getCargoDetails());
    }

    /**
     * Method under test: {@link CargoDeleteListenerImpl#onCRUDevent(CargoDeleteEvent)}
     */
    @Test
    void testOnCRUDevent7() {
        Customer customer = mock(Customer.class);
        when(customer.getName()).thenReturn("Name");

        CustomerStorage customerStorage = new CustomerStorage();
        customerStorage.addCustomer(customer);
        CargoDeleteListenerImpl cargoDeleteListenerImpl = new CargoDeleteListenerImpl(
                new WarehouseManagement(customerStorage));
        CargoDeleteEvent cargoDeleteEvent = new CargoDeleteEvent("Source", "42");

        cargoDeleteListenerImpl.onCRUDevent(cargoDeleteEvent);
        verify(customer).getName();
        assertEquals("42", cargoDeleteEvent.getCargoDetails());
    }
}

