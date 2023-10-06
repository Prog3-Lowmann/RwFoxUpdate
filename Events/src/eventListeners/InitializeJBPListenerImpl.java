package eventListeners;

import Interfaces.WarehouseInterface;
import customer.CustomerStorage;
import eventSystem.InitializeJBPEvent;
import eventSystem.Listener;
import eventSystem.SaveJBPEvent;
import jbs.CustomJBP;
import jbs.WarehouseJBP;
import warehouse.WarehouseManagement;

import java.io.IOException;

public class InitializeJBPListenerImpl implements Listener<InitializeJBPEvent> {
    private WarehouseManagement warehouseManagement;

    public InitializeJBPListenerImpl(WarehouseManagement warehouseManagement) {
        this.warehouseManagement = warehouseManagement;
    }

    @Override
    public void onCRUDevent(InitializeJBPEvent event) throws ClassNotFoundException, IOException {
        CustomerStorage customerStorage =   CustomJBP.initializeJBP("persisteddata/customerjbpdata.txt");
        warehouseManagement.setCustomerStorage(customerStorage);
        WarehouseInterface warehouse = WarehouseJBP.initializeJBP("persisteddata/warehousejbpdata.txt");

        //warehouseManagement.setCustomerStorage(customerStorage);
        warehouseManagement.setWarehouse(warehouse);
    }
}
