package eventListeners;

import customer.CustomerStorage;
import eventSystem.Listener;
import eventSystem.SaveJosEvent;
import jos.CustomJOS;
import jos.WarehouseJOS;
import warehouse.WarehouseManagement;

import java.io.IOException;

public class SaveJosListenerImpl implements Listener<SaveJosEvent> {
    private WarehouseManagement warehouseManagement;

    public SaveJosListenerImpl(WarehouseManagement warehouseManagement) {
        this.warehouseManagement = warehouseManagement;
    }

    @Override
    public void onCRUDevent(SaveJosEvent event) throws ClassNotFoundException, IOException {
        CustomJOS.persistJOS("persisteddata/customerjosdata.txt", (CustomerStorage) this.warehouseManagement.getCustomerStorage());
        WarehouseJOS.persistWithJOS("persisteddata/warehousejosdata.txt", this.warehouseManagement.getWarehouse());
        // System.out.println("Data persisted through JOS");
    }
}
