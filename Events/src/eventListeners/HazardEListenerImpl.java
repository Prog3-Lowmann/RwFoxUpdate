package eventListeners;

import cargo.Cargo;
import cargo.Hazard;
import eventSystem.CargoReadEvent;
import eventSystem.HazardEReadEvent;
import eventSystem.HazardIReadEvent;
import eventSystem.Listener;
import warehouse.WarehouseManagement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class HazardEListenerImpl implements Listener<HazardEReadEvent> {

    private WarehouseManagement warehouseManagement;


    public HazardEListenerImpl(WarehouseManagement warehouseManagement) {
        this.warehouseManagement = warehouseManagement;
    }

    @Override
    public void onCRUDevent(HazardEReadEvent event) throws ClassNotFoundException, IOException {
        Set<Hazard> includedHazards = warehouseManagement.getExcludedHazards();
        System.out.println("here are following hazards included: " + includedHazards);
    }
}
