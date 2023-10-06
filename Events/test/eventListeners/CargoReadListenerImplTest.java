package eventListeners;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import eventSystem.CargoReadEvent;
import org.junit.jupiter.api.Test;
import warehouse.WarehouseManagement;

class CargoReadListenerImplTest {

    @Test
    void testOnCRUDevent() {
        CargoReadListenerImpl cargoReadListenerImpl = new CargoReadListenerImpl(new WarehouseManagement());
        CargoReadEvent cargoReadEvent = new CargoReadEvent("Source", "Cargo");

        cargoReadListenerImpl.onCRUDevent(cargoReadEvent);
        assertEquals("Cargo", cargoReadEvent.getCargoDetails());
    }

    @Test
    void testOnCRUDevent4() {
        CargoReadListenerImpl cargoReadListenerImpl = new CargoReadListenerImpl(new WarehouseManagement());
        CargoReadEvent cargoReadEvent = new CargoReadEvent("Source", "");

        cargoReadListenerImpl.onCRUDevent(cargoReadEvent);
        assertEquals("", cargoReadEvent.getCargoDetails());
    }

}

