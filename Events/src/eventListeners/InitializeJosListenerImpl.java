package eventListeners;

import customer.CustomerStorage;
import eventSystem.InitializeJBPEvent;
import eventSystem.InitializeJosEvent;
import eventSystem.Listener;
import jos.CustomJOS;
import jos.WarehouseJOS;
import warehouse.Warehouse;
import warehouse.WarehouseManagement;

import java.io.IOException;

public class InitializeJosListenerImpl implements Listener<InitializeJosEvent> {
    private WarehouseManagement warehouseManagement;

    public InitializeJosListenerImpl(WarehouseManagement warehouseManagement) {
        this.warehouseManagement = warehouseManagement;
    }

    @Override
    public void onCRUDevent(InitializeJosEvent event) throws ClassNotFoundException, IOException {
        CustomerStorage customerStorage =   CustomJOS.initializeJOS("persisteddata/customerjosdata.txt");
        warehouseManagement.setCustomerStorage(customerStorage);
        Warehouse warehouse = WarehouseJOS.initializeWithJOS("persisteddata/warehousejosdata.txt");

        warehouseManagement.setWarehouse(warehouse);
    }
}
