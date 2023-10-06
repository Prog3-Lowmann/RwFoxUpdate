package observers;


import observers.Interfaces.Observer;
import warehouse.WarehouseManagement;

import java.io.Serializable;


public class CapacityObserver implements Observer, Serializable {
    private WarehouseManagement warehouseManagement;
    private int lastCapacity;

    public static String warehouseCapacity;

    public CapacityObserver(WarehouseManagement warehouseManagement) {
        this.warehouseManagement = warehouseManagement;
        this.lastCapacity = warehouseManagement.getWarehouse().getCapacity();
    }

    @Override
    public void update() {
        if (warehouseCapacity != null && !warehouseCapacity.isEmpty()) {
            System.out.println(warehouseCapacity);
        }

        checkCapacity(warehouseManagement);
    }

    public void checkCapacity(WarehouseManagement warehouseManagement) {
        int capacity = warehouseManagement.getWarehouse().getCapacity(); // capacity
        int currentSize = warehouseManagement.getWarehouse().getStorage().size(); // current size

        double utilizationPercentage = (double) currentSize / capacity * 100;

        if (utilizationPercentage >= 90) {
            warehouseCapacity = "Warning: Capacity exceeded 90%!";
        }
    }

}



