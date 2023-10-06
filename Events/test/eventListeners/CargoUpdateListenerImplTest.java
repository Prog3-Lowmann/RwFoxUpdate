package eventListeners;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import eventSystem.CargoUpdateEvent;
import org.junit.jupiter.api.Test;
import warehouse.WarehouseManagement;

class CargoUpdateListenerImplTest {


    @Test
    void testOnCRUDevent2() {
        WarehouseManagement warehouseManagement = mock(WarehouseManagement.class);
        doNothing().when(warehouseManagement).inspectCargo((String) any());
        CargoUpdateListenerImpl cargoUpdateListenerImpl = new CargoUpdateListenerImpl(warehouseManagement);
        cargoUpdateListenerImpl.onCRUDevent(new CargoUpdateEvent("Source", "Location"));
        verify(warehouseManagement).inspectCargo((String) any());
    }


    @Test
    void testOnCRUDevent5() {
        CargoUpdateListenerImpl cargoUpdateListenerImpl = new CargoUpdateListenerImpl(new WarehouseManagement());
        CargoUpdateEvent cargoUpdateEvent = mock(CargoUpdateEvent.class);
        when(cargoUpdateEvent.getCargoDetails()).thenReturn("Cargo Details");
        cargoUpdateListenerImpl.onCRUDevent(cargoUpdateEvent);
        verify(cargoUpdateEvent).getCargoDetails();
    }


}

