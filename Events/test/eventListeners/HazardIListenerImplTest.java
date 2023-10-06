package eventListeners;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import eventSystem.CargoReadEvent;
import eventSystem.HazardIReadEvent;

import java.io.IOException;
import java.util.HashSet;

import org.junit.jupiter.api.Test;
import warehouse.WarehouseManagement;

class HazardIListenerImplTest {


    @Test
    void testOnCRUDevent() {
        HazardIListenerImpl hazardIListenerImpl = new HazardIListenerImpl(new WarehouseManagement());
        CargoReadEvent cargoReadEvent = new CargoReadEvent("Source", "Cargo");

        hazardIListenerImpl.onCRUDevent(cargoReadEvent);
        assertEquals("Cargo", cargoReadEvent.getCargoDetails());
    }


    @Test
    void testOnCRUDevent4() {
        HazardIListenerImpl hazardIListenerImpl = new HazardIListenerImpl(new WarehouseManagement());
        CargoReadEvent cargoReadEvent = new CargoReadEvent("Source", "");

        hazardIListenerImpl.onCRUDevent(cargoReadEvent);
        assertEquals("", cargoReadEvent.getCargoDetails());
    }


    @Test
    void testOnCRUDevent11() throws IOException, ClassNotFoundException {
        WarehouseManagement warehouseManagement = mock(WarehouseManagement.class);
        when(warehouseManagement.getIncludedHazards()).thenReturn(new HashSet<>());
        HazardIListenerImpl hazardIListenerImpl = new HazardIListenerImpl(warehouseManagement);
        hazardIListenerImpl.onCRUDevent(new HazardIReadEvent("Source", "Cargo"));
        verify(warehouseManagement).getIncludedHazards();
    }
}

