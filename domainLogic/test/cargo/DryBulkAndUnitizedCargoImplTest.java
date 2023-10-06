package cargo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import administration.Customer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.junit.jupiter.api.Test;

class DryBulkAndUnitizedCargoImplTest {
    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link DryBulkAndUnitizedCargoImpl#DryBulkAndUnitizedCargoImpl()}
     *   <li>{@link DryBulkAndUnitizedCargoImpl#setFragile(boolean)}
     *   <li>{@link DryBulkAndUnitizedCargoImpl#setGrainSize(int)}
     *   <li>{@link DryBulkAndUnitizedCargoImpl#getGrainSize()}
     *   <li>{@link DryBulkAndUnitizedCargoImpl#isFragile()}
     * </ul>
     */
    @Test
    void testConstructor() {
        DryBulkAndUnitizedCargoImpl actualDryBulkAndUnitizedCargoImpl = new DryBulkAndUnitizedCargoImpl();
        actualDryBulkAndUnitizedCargoImpl.setFragile(true);
        actualDryBulkAndUnitizedCargoImpl.setGrainSize(3);
        assertEquals(3, actualDryBulkAndUnitizedCargoImpl.getGrainSize());
        assertTrue(actualDryBulkAndUnitizedCargoImpl.isFragile());
    }

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link DryBulkAndUnitizedCargoImpl#DryBulkAndUnitizedCargoImpl(Customer, Date, Date, int, BigDecimal, Collection, boolean, int)}
     *   <li>{@link DryBulkAndUnitizedCargoImpl#setFragile(boolean)}
     *   <li>{@link DryBulkAndUnitizedCargoImpl#setGrainSize(int)}
     *   <li>{@link DryBulkAndUnitizedCargoImpl#toString()}
     *   <li>{@link DryBulkAndUnitizedCargoImpl#getGrainSize()}
     *   <li>{@link DryBulkAndUnitizedCargoImpl#isFragile()}
     * </ul>
     */
    @Test
    void testConstructor2() {
        Customer owner = mock(Customer.class);
        Date storageDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        Date lastInspectionDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        BigDecimal value = BigDecimal.valueOf(1L);
        DryBulkAndUnitizedCargoImpl actualDryBulkAndUnitizedCargoImpl = new DryBulkAndUnitizedCargoImpl(owner,
                storageDate, lastInspectionDate, 1, value, new ArrayList<>(), true, 3);
        actualDryBulkAndUnitizedCargoImpl.setFragile(true);
        actualDryBulkAndUnitizedCargoImpl.setGrainSize(3);
        String actualToStringResult = actualDryBulkAndUnitizedCargoImpl.toString();
        assertEquals(3, actualDryBulkAndUnitizedCargoImpl.getGrainSize());
        assertTrue(actualDryBulkAndUnitizedCargoImpl.isFragile());
        assertEquals(
                "Cargo type : DryBulkAndUnitizedCargo, customer : null, value: 1, storageLocation : 1, hazards : [], grainSize: 3, fragile :true",
                actualToStringResult);
    }

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link DryBulkAndUnitizedCargoImpl#DryBulkAndUnitizedCargoImpl(Customer, Date, Date, BigDecimal, Collection, boolean, int)}
     *   <li>{@link DryBulkAndUnitizedCargoImpl#setFragile(boolean)}
     *   <li>{@link DryBulkAndUnitizedCargoImpl#setGrainSize(int)}
     *   <li>{@link DryBulkAndUnitizedCargoImpl#toString()}
     *   <li>{@link DryBulkAndUnitizedCargoImpl#getGrainSize()}
     *   <li>{@link DryBulkAndUnitizedCargoImpl#isFragile()}
     * </ul>
     */
    @Test
    void testConstructor3() {
        Customer owner = mock(Customer.class);
        Date storageDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        Date lastInspectionDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        BigDecimal value = BigDecimal.valueOf(1L);
        DryBulkAndUnitizedCargoImpl actualDryBulkAndUnitizedCargoImpl = new DryBulkAndUnitizedCargoImpl(owner,
                storageDate, lastInspectionDate, value, new ArrayList<>(), true, 3);
        actualDryBulkAndUnitizedCargoImpl.setFragile(true);
        actualDryBulkAndUnitizedCargoImpl.setGrainSize(3);
        String actualToStringResult = actualDryBulkAndUnitizedCargoImpl.toString();
        assertEquals(3, actualDryBulkAndUnitizedCargoImpl.getGrainSize());
        assertTrue(actualDryBulkAndUnitizedCargoImpl.isFragile());
        assertEquals(
                "Cargo type : DryBulkAndUnitizedCargo, customer : null, value: 1, storageLocation : -1, hazards : [], grainSize: 3, fragile :true",
                actualToStringResult);
    }
}

