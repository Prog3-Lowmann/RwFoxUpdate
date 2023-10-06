package objservers;

import cargo.Cargo;
import observers.CapacityObserver;
import warehouse.Warehouse;
import warehouse.WarehouseManagement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class CapacityObserverTest {

    private CapacityObserver capacityObserver;
    private WarehouseManagement mockWarehouseManagement;

    @BeforeEach
    void setUp() {

        Warehouse mockWarehouse = Mockito.mock(Warehouse.class);
        mockWarehouseManagement = Mockito.mock(WarehouseManagement.class);

        // Set up a mock storage with a specific size
        Map<Integer, Cargo> mockStorage = new HashMap<>();
        when(mockWarehouse.getStorage()).thenReturn(mockStorage);

        // Set a capacity for the mock warehouse
        when(mockWarehouse.getCapacity()).thenReturn(100);

        when(mockWarehouseManagement.getWarehouse()).thenReturn(mockWarehouse);

        capacityObserver = spy(new CapacityObserver(mockWarehouseManagement));
    }








    @Test
    void checkCapacityNoWarningGenerated() {
        CapacityObserver.warehouseCapacity = null;
        //when(mockWarehouseManagement.getWarehouse().getStorage().size()).thenReturn(80); // Set a size for the mock storage

        // Call the method to be tested
        capacityObserver.checkCapacity(mockWarehouseManagement);

        // Check the result
        assertNull(CapacityObserver.warehouseCapacity);
    }

}

