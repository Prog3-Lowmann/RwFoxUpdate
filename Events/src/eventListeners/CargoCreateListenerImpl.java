package eventListeners;


import eventSystem.CargoCreateEvent;
import eventSystem.Listener;
import warehouse.WarehouseManagement;


public class CargoCreateListenerImpl implements Listener<CargoCreateEvent> {
    private WarehouseManagement warehouseManagement;

    public CargoCreateListenerImpl(WarehouseManagement warehouseManagement) {
        this.warehouseManagement = warehouseManagement;
    }


    @Override
    public void onCRUDevent(CargoCreateEvent event) throws ClassNotFoundException {

        try {
            this.warehouseManagement.insertCargo(event.getCargoDetails());
        } catch (Exception e) {
            System.out.println("something went wrong while adding cargo");
        }
    }
}
