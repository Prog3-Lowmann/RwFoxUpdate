package eventListeners;

import cargo.Cargo;
import eventSystem.CargoReadEvent;
import eventSystem.Listener;
import warehouse.WarehouseManagement;

import java.util.ArrayList;
import java.util.List;

public class CargoReadListenerImpl implements Listener<CargoReadEvent> {
    private WarehouseManagement warehouseManagement;


    public CargoReadListenerImpl(WarehouseManagement warehouseManagement) {
        this.warehouseManagement = warehouseManagement;
    }

    public void onCRUDevent(CargoReadEvent event) {
        List<Cargo> cargos = new ArrayList<>();

        if (event.getCargoDetails().isEmpty()) {
            cargos = this.warehouseManagement.readAllCargo();
        } else {
            cargos = this.warehouseManagement.getCargoOfTye(event.getCargoDetails());
        }


        if (cargos == null || cargos.size() == 0) {
            System.out.println("There is no cargo of this type");
        } else {
            for (Cargo cargo : cargos) {
                System.out.println(cargo);
            }
        }


    }

}
