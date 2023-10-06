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

class LiquidBulkAndUnitizedCargoImplTest {
    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link LiquidBulkAndUnitizedCargoImpl#LiquidBulkAndUnitizedCargoImpl()}
     *   <li>{@link LiquidBulkAndUnitizedCargoImpl#isFragile()}
     *   <li>{@link LiquidBulkAndUnitizedCargoImpl#isPressurized()}
     * </ul>
     */
    @Test
    void testConstructor() {
        LiquidBulkAndUnitizedCargoImpl actualLiquidBulkAndUnitizedCargoImpl = new LiquidBulkAndUnitizedCargoImpl();
        assertFalse(actualLiquidBulkAndUnitizedCargoImpl.isFragile());
        assertFalse(actualLiquidBulkAndUnitizedCargoImpl.isPressurized());
    }

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link LiquidBulkAndUnitizedCargoImpl#LiquidBulkAndUnitizedCargoImpl(Customer, Date, Date, int, BigDecimal, Collection, boolean, boolean)}
     *   <li>{@link LiquidBulkAndUnitizedCargoImpl#toString()}
     *   <li>{@link LiquidBulkAndUnitizedCargoImpl#isFragile()}
     *   <li>{@link LiquidBulkAndUnitizedCargoImpl#isPressurized()}
     * </ul>
     */
    @Test
    void testConstructor2() {
        Customer owner = mock(Customer.class);
        Date storageDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        Date lastInspectionDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        BigDecimal value = BigDecimal.valueOf(1L);
        LiquidBulkAndUnitizedCargoImpl actualLiquidBulkAndUnitizedCargoImpl = new LiquidBulkAndUnitizedCargoImpl(owner,
                storageDate, lastInspectionDate, 1, value, new ArrayList<>(), true, true);
        String actualToStringResult = actualLiquidBulkAndUnitizedCargoImpl.toString();
        assertTrue(actualLiquidBulkAndUnitizedCargoImpl.isFragile());
        assertTrue(actualLiquidBulkAndUnitizedCargoImpl.isPressurized());
        assertEquals("cargoType : LiquidBulkAndUnitizedCargo , customer : null, value : 1, storageLocation : 1, hazards : [], isPressurized : true, isFragile : true", actualToStringResult);
    }

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link LiquidBulkAndUnitizedCargoImpl#LiquidBulkAndUnitizedCargoImpl(Customer, Date, Date, BigDecimal, Collection, boolean, boolean)}
     *   <li>{@link LiquidBulkAndUnitizedCargoImpl#toString()}
     *   <li>{@link LiquidBulkAndUnitizedCargoImpl#isFragile()}
     *   <li>{@link LiquidBulkAndUnitizedCargoImpl#isPressurized()}
     * </ul>
     */
    @Test
    void testConstructor3() {
        Customer owner = mock(Customer.class);
        Date storageDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        Date lastInspectionDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        BigDecimal value = BigDecimal.valueOf(1L);
        LiquidBulkAndUnitizedCargoImpl actualLiquidBulkAndUnitizedCargoImpl = new LiquidBulkAndUnitizedCargoImpl(owner,
                storageDate, lastInspectionDate, value, new ArrayList<>(), true, true);
        String actualToStringResult = actualLiquidBulkAndUnitizedCargoImpl.toString();
        assertTrue(actualLiquidBulkAndUnitizedCargoImpl.isFragile());
        assertTrue(actualLiquidBulkAndUnitizedCargoImpl.isPressurized());
        assertEquals("cargoType : LiquidBulkAndUnitizedCargo , customer : null, value : 1, storageLocation : -1, hazards : [], isPressurized : true, isFragile : true", actualToStringResult);
    }
}

