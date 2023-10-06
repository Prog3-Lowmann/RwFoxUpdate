package objservers;

import observers.AvailableHazardObserver;
import warehouse.WarehouseManagement;
import cargo.Hazard;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AvailableHazardObserverTest {

    @Test
    void updateCallsCheckAvailableHazard() {
        WarehouseManagement mockWarehouseManagement = Mockito.mock(WarehouseManagement.class);
        AvailableHazardObserver observer = new AvailableHazardObserver(mockWarehouseManagement);

        observer.update();

        Mockito.verify(mockWarehouseManagement, Mockito.times(1)).getExcludedHazards();
    }

    @Test
    void checkAvailableHazard() {
        WarehouseManagement mockWarehouseManagement = Mockito.mock(WarehouseManagement.class);
        AvailableHazardObserver observer = new AvailableHazardObserver(mockWarehouseManagement);

        // Set up a mock for the excluded hazards
        Set<Hazard> excludedHazards = new HashSet<>();
        excludedHazards.add(Hazard.toxic);
        excludedHazards.add(Hazard.explosive);

        // Define the behavior of the mock
        Mockito.when(mockWarehouseManagement.getExcludedHazards()).thenReturn(excludedHazards);

        // Call the method to be tested
        observer.checkAvaibleHazard(mockWarehouseManagement);

        // Check the result
        assertEquals(excludedHazards, observer.availableHazards);
    }
}
