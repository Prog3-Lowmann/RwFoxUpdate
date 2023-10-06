package eventListeners;

import eventListeners.CargoCreateListenerImpl;
import eventSystem.CargoCreateEvent;
import warehouse.WarehouseManagement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

class CargoCreateListenerImplTest {

    private WarehouseManagement warehouseManagement;
    private CargoCreateListenerImpl cargoCreateListener;

    @BeforeEach
    void setUp() {
        warehouseManagement = mock(WarehouseManagement.class);
        cargoCreateListener = new CargoCreateListenerImpl(warehouseManagement);
    }

    @Test
    void onCRUDevent_InsertsCargoDetails() throws Exception {
        CargoCreateEvent cargoCreateEvent = new CargoCreateEvent(warehouseManagement,"cargo details");
        cargoCreateListener.onCRUDevent(cargoCreateEvent);

        verify(warehouseManagement, times(1)).insertCargo(any());
    }


}
