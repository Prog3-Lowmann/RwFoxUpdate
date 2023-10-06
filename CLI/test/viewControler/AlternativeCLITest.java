package viewControler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import Interfaces.WarehouseInterface;
import customer.CustomerStorage;
import eventSystem.Handler;
import eventSystem.Listener;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import warehouse.Warehouse;
import warehouse.WarehouseManagement;

class AlternativeCLITest {

    @Test
    void testConstructor() {
        AlternativeCLI actualAlternativeCLI = new AlternativeCLI();
        actualAlternativeCLI.setCargoDBPath("Cargo DBPath");
        actualAlternativeCLI.setCurrentCase(Operator.INSERT);
        actualAlternativeCLI.setCustomerDBPath("Customer DBPath");
        Handler handler = new Handler();
        actualAlternativeCLI.setInsertEventHandler(handler);
        Handler handler1 = new Handler();
        actualAlternativeCLI.setReadEventHandler(handler1);
        Handler handler2 = new Handler();
        actualAlternativeCLI.setRemoveEventHandler(handler2);
        Handler handler3 = new Handler();
        actualAlternativeCLI.setUpdateEventHandler(handler3);
        WarehouseManagement warehouseManagement = new WarehouseManagement();
        actualAlternativeCLI.setWarehouseManagement(warehouseManagement);
        assertEquals("Cargo DBPath", actualAlternativeCLI.getCargoDBPath());
        assertEquals(Operator.INSERT, actualAlternativeCLI.getCurrentCase());
        assertEquals("Customer DBPath", actualAlternativeCLI.getCustomerDBPath());
        assertSame(handler, actualAlternativeCLI.getInsertEventHandler());
        assertSame(handler1, actualAlternativeCLI.getReadEventHandler());
        assertSame(handler2, actualAlternativeCLI.getRemoveEventHandler());
        assertSame(handler3, actualAlternativeCLI.getUpdateEventHandler());
        assertSame(warehouseManagement, actualAlternativeCLI.getWarehouseManagement());
    }


    @Test
    void testConstructor2() {
        AlternativeCLI actualAlternativeCLI = new AlternativeCLI();
        assertNull(actualAlternativeCLI.getUpdateEventHandler());
        assertNull(actualAlternativeCLI.getInsertEventHandler());
        assertNull(actualAlternativeCLI.getReadEventHandler());
        assertNull(actualAlternativeCLI.getRemoveEventHandler());
        WarehouseManagement warehouseManagement = actualAlternativeCLI.getWarehouseManagement();
        assertTrue(warehouseManagement.getCustomersWithTotalCargo().isEmpty());
        assertTrue(warehouseManagement.getIncludedHazards().isEmpty());
        assertTrue(((CustomerStorage) warehouseManagement.getCustomerStorage()).getStorage().isEmpty());
        WarehouseInterface warehouse = warehouseManagement.getWarehouse();
        assertTrue(warehouse.getStorage().isEmpty());
        assertTrue(warehouse.getObservers().isEmpty());
        assertEquals(1, ((Warehouse) warehouse).getIndex());
        assertEquals(10, warehouse.getCapacity());
    }








    @Test
    void testInitializeDB() {
        AlternativeCLI alternativeCLI = new AlternativeCLI();

        WarehouseManagement warehouseManagement = alternativeCLI.getWarehouseManagement();
        assertTrue(warehouseManagement.getCustomersWithTotalCargo().isEmpty());
        assertTrue(warehouseManagement.getIncludedHazards().isEmpty());
        assertTrue(((CustomerStorage) warehouseManagement.getCustomerStorage()).getStorage().isEmpty());
        WarehouseInterface warehouse = warehouseManagement.getWarehouse();
        assertTrue(warehouse.getStorage().isEmpty());
        assertTrue(warehouse.getObservers().isEmpty());
        assertEquals(1, ((Warehouse) warehouse).getIndex());
        assertEquals(10, warehouse.getCapacity());
    }


    @Test
    void testInitializeDB2() {
        Handler handler = new Handler();
        handler.add(mock(Listener.class));

        AlternativeCLI alternativeCLI = new AlternativeCLI();
        alternativeCLI.setInsertEventHandler(handler);

        WarehouseManagement warehouseManagement = alternativeCLI.getWarehouseManagement();
        assertTrue(warehouseManagement.getCustomersWithTotalCargo().isEmpty());
        assertTrue(warehouseManagement.getIncludedHazards().isEmpty());
        assertTrue(((CustomerStorage) warehouseManagement.getCustomerStorage()).getStorage().isEmpty());
        WarehouseInterface warehouse = warehouseManagement.getWarehouse();
        assertTrue(warehouse.getStorage().isEmpty());
        assertTrue(warehouse.getObservers().isEmpty());
        assertEquals(1, ((Warehouse) warehouse).getIndex());
        assertEquals(10, warehouse.getCapacity());
    }


    @Test
    void testInitializeDB3() {
        AlternativeCLI alternativeCLI = new AlternativeCLI();

        WarehouseManagement warehouseManagement = alternativeCLI.getWarehouseManagement();
        assertTrue(warehouseManagement.getCustomersWithTotalCargo().isEmpty());
        assertTrue(warehouseManagement.getIncludedHazards().isEmpty());
        assertTrue(((CustomerStorage) warehouseManagement.getCustomerStorage()).getStorage().isEmpty());
        WarehouseInterface warehouse = warehouseManagement.getWarehouse();
        assertTrue(warehouse.getStorage().isEmpty());
        assertTrue(warehouse.getObservers().isEmpty());
        assertEquals(1, ((Warehouse) warehouse).getIndex());
        assertEquals(10, warehouse.getCapacity());
    }


    @Test
    void testInitializeDB4() {
        Handler handler = new Handler();
        handler.add(mock(Listener.class));

        AlternativeCLI alternativeCLI = new AlternativeCLI();
        alternativeCLI.setInsertEventHandler(handler);

        WarehouseManagement warehouseManagement = alternativeCLI.getWarehouseManagement();
        assertTrue(warehouseManagement.getCustomersWithTotalCargo().isEmpty());
        assertTrue(warehouseManagement.getIncludedHazards().isEmpty());
        assertTrue(((CustomerStorage) warehouseManagement.getCustomerStorage()).getStorage().isEmpty());
        WarehouseInterface warehouse = warehouseManagement.getWarehouse();
        assertTrue(warehouse.getStorage().isEmpty());
        assertTrue(warehouse.getObservers().isEmpty());
        assertEquals(1, ((Warehouse) warehouse).getIndex());
        assertEquals(10, warehouse.getCapacity());
    }





}

