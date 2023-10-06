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

class ConsoleTest {

    @Test
    void testAddHandler() {
        Console console = new Console();
        console.addHandler(new Handler());
    }


    @Test
    void testAddHandler2() {
        Console console = new Console();

        Handler handler = new Handler();
        handler.add(mock(Listener.class));
        console.addHandler(handler);
    }


    @Test
    void testConstructor() {
        Console actualConsole = new Console();
        actualConsole.setCargoDBPath("Cargo DBPath");
        actualConsole.setCurrentCase(Operator.INSERT);
        actualConsole.setCustomerDBPath("Customer DBPath");
        Handler handler = new Handler();
        actualConsole.setInsertEventHandler(handler);
        Handler handler1 = new Handler();
        actualConsole.setReadEventHandler(handler1);
        Handler handler2 = new Handler();
        actualConsole.setRemoveEventHandler(handler2);
        Handler handler3 = new Handler();
        actualConsole.setUpdateEventHandler(handler3);
        WarehouseManagement warehouseManagement = new WarehouseManagement();
        actualConsole.setWarehouseManagement(warehouseManagement);
        assertEquals("Cargo DBPath", actualConsole.getCargoDBPath());
        assertEquals(Operator.INSERT, actualConsole.getCurrentCase());
        assertEquals("Customer DBPath", actualConsole.getCustomerDBPath());
        assertSame(handler, actualConsole.getInsertEventHandler());
        assertSame(handler1, actualConsole.getReadEventHandler());
        assertSame(handler2, actualConsole.getRemoveEventHandler());
        assertSame(handler3, actualConsole.getUpdateEventHandler());
        assertSame(warehouseManagement, actualConsole.getWarehouseManagement());
    }


    @Test
    void testConstructor2() {
        Console actualConsole = new Console();
        assertNull(actualConsole.getUpdateEventHandler());
        assertNull(actualConsole.getInsertEventHandler());
        assertNull(actualConsole.getReadEventHandler());
        assertNull(actualConsole.getRemoveEventHandler());
        WarehouseManagement warehouseManagement = actualConsole.getWarehouseManagement();
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
    void testSwitchCommand() {
        (new Console()).switchCommand("Input");
    }

}
