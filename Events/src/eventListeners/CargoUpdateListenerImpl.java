package eventListeners;

import eventSystem.CargoUpdateEvent;
import eventSystem.Listener;
import warehouse.WarehouseManagement;

public class CargoUpdateListenerImpl implements Listener<CargoUpdateEvent> {
    private WarehouseManagement warehouseManagement;

    public CargoUpdateListenerImpl(WarehouseManagement warehouseManagement) {
        this.warehouseManagement = warehouseManagement;
    }

    @Override
    public void onCRUDevent(CargoUpdateEvent event) {
        this.warehouseManagement.inspectCargo(event.getCargoDetails());
    }
}
