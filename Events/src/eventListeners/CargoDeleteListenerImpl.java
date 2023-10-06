package eventListeners;

import eventSystem.CargoDeleteEvent;
import eventSystem.Listener;
import warehouse.WarehouseManagement;

public class CargoDeleteListenerImpl implements Listener<CargoDeleteEvent> {
    private WarehouseManagement warehouseManagement;

    public CargoDeleteListenerImpl(WarehouseManagement warehouseManagement) {
        this.warehouseManagement = warehouseManagement;
    }

    @Override
    public void onCRUDevent(CargoDeleteEvent event) {

        try {
            this.warehouseManagement.removeCargo(event.getCargoDetails());
        } catch (Exception e) {
            System.out.println("Something wrong while removing cargo");
        }

    }
}
