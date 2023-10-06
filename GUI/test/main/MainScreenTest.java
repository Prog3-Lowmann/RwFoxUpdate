package main;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import Interfaces.WarehouseInterface;
import customer.CustomerStorage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import warehouse.Warehouse;
import warehouse.WarehouseManagement;
import observers.Interfaces.Observer;

class MainScreenTest {

    @Test
    void testConstructor() {
        WarehouseManagement warehouseManagement = (new MainScreen()).warehouseManagement;
        assertTrue(warehouseManagement.getCustomersWithTotalCargo().isEmpty());
        assertTrue(warehouseManagement.getIncludedHazards().isEmpty());
        WarehouseInterface warehouse = warehouseManagement.getWarehouse();
        assertTrue(warehouse.getObservers().isEmpty());
        assertEquals(1, ((Warehouse) warehouse).getIndex());
        assertEquals(10, warehouse.getCapacity());
        assertTrue(((CustomerStorage) warehouseManagement.getCustomerStorage()).getStorage().isEmpty());
        assertTrue(warehouse.getStorage().isEmpty());
    }



}

