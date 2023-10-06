package cargo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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

class UnitisedCargoImplTest {
    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link UnitisedCargoImpl#UnitisedCargoImpl()}
     *   <li>{@link UnitisedCargoImpl#isFragile()}
     * </ul>
     */
    @Test
    void testConstructor() {
        assertFalse((new UnitisedCargoImpl()).isFragile());
    }

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link UnitisedCargoImpl#UnitisedCargoImpl(Customer, Date, Date, int, BigDecimal, Collection, boolean)}
     *   <li>{@link UnitisedCargoImpl#toString()}
     *   <li>{@link UnitisedCargoImpl#isFragile()}
     * </ul>
     */
    @Test
    void testConstructor2() {
        Customer owner = mock(Customer.class);
        Date storageDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        Date lastInspectionDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        BigDecimal value = BigDecimal.valueOf(1L);
        UnitisedCargoImpl actualUnitisedCargoImpl = new UnitisedCargoImpl(owner, storageDate, lastInspectionDate, 1,
                value, new ArrayList<>(), true);
        String actualToStringResult = actualUnitisedCargoImpl.toString();
        assertTrue(actualUnitisedCargoImpl.isFragile());
        assertEquals("CargoType :  UnitisedCargo, customer:  null, value :1, storageLocation : 1, Hazards : [] fragile : true",
                actualToStringResult);
    }

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link UnitisedCargoImpl#UnitisedCargoImpl(Customer, Date, Date, BigDecimal, Collection, boolean)}
     *   <li>{@link UnitisedCargoImpl#toString()}
     *   <li>{@link UnitisedCargoImpl#isFragile()}
     * </ul>
     */
    @Test
    void testConstructor3() {
        Customer owner = mock(Customer.class);
        Date storageDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        Date lastInspectionDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        BigDecimal value = BigDecimal.valueOf(1L);
        UnitisedCargoImpl actualUnitisedCargoImpl = new UnitisedCargoImpl(owner, storageDate, lastInspectionDate, value,
                new ArrayList<>(), true);
        String actualToStringResult = actualUnitisedCargoImpl.toString();
        assertTrue(actualUnitisedCargoImpl.isFragile());
        assertEquals("CargoType :  UnitisedCargo, customer:  null, value :1, storageLocation : -1, Hazards : [] fragile : true",
                actualToStringResult);
    }
}

