package eventListeners;

import eventSystem.CustomerDeleteEvent;
import eventSystem.Listener;
import eventSystem.SaveJBPEvent;
import jbs.CustomJBP;
import jbs.WarehouseJBP;
import warehouse.WarehouseManagement;

import java.io.IOException;

public class SaveJBPListenerImpl implements Listener<SaveJBPEvent> {
    private WarehouseManagement warehouseManagement;

    public SaveJBPListenerImpl(WarehouseManagement warehouseManagement) {
        this.warehouseManagement = warehouseManagement;
    }

    @Override
    public void onCRUDevent(SaveJBPEvent event) throws ClassNotFoundException, IOException {
        CustomJBP.persistJBP("persisteddata/customerjbpdata.txt",this.warehouseManagement.getCustomerStorage());
        WarehouseJBP.persistJBP("persisteddata/warehousejbpdata.txt",this.warehouseManagement.getWarehouse());

    }
}
