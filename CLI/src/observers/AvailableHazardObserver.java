package observers;

import cargo.Hazard;
import observers.Interfaces.Observer;
import warehouse.WarehouseManagement;

import java.io.Serializable;
import java.util.Set;


public class AvailableHazardObserver implements Observer, Serializable {
    private WarehouseManagement warehouseManagement;


    public Set<Hazard> availableHazards;

    public AvailableHazardObserver(WarehouseManagement warehouseManagement) {
        this.warehouseManagement = warehouseManagement;
    }

    @Override
    public void update() {

        checkAvaibleHazard(warehouseManagement);
    }


    public void checkAvaibleHazard(WarehouseManagement warehouseManagement) {

        Set<Hazard> availableHazard = warehouseManagement.getExcludedHazards();
        this.availableHazards = availableHazard;
        //System.out.println("Available Hazard = " + availableHazard);

    }


}




