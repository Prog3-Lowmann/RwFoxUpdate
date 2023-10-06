package eventListeners;

import cargo.Cargo;
import cargo.Hazard;
import eventSystem.CargoReadEvent;
import eventSystem.HazardIReadEvent;
import eventSystem.Listener;
import warehouse.WarehouseManagement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class HazardIListenerImpl implements Listener<HazardIReadEvent> {

    private WarehouseManagement warehouseManagement;


    public HazardIListenerImpl(WarehouseManagement warehouseManagement) {
        this.warehouseManagement = warehouseManagement;
    }

    public void onCRUDevent(CargoReadEvent event){
        List<Cargo> cargos = new ArrayList<>();

        if(event.getCargoDetails().isEmpty()) {
            cargos = this.warehouseManagement.readAllCargo();
        }
        else {
            cargos = this.warehouseManagement.getCargoOfTye(event.getCargoDetails());
        }

        if(cargos == null || cargos.size() == 0) {
            System.out.println("No cargo of this type");
        }
        else {
            for(Cargo cargo : cargos) {
                System.out.println(cargo);
            }
        }
    }

    @Override
    public void onCRUDevent(HazardIReadEvent event) throws ClassNotFoundException, IOException {
        Set<Hazard> includedHazards = warehouseManagement.getIncludedHazards();
        System.out.println("here are following hazards included: " + includedHazards);
    }
}
