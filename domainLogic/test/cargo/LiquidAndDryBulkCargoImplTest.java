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

class LiquidAndDryBulkCargoImplTest {
    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link LiquidAndDryBulkCargoImpl#LiquidAndDryBulkCargoImpl()}
     *   <li>{@link LiquidAndDryBulkCargoImpl#setGrainSize(int)}
     *   <li>{@link LiquidAndDryBulkCargoImpl#setPressurized(boolean)}
     *   <li>{@link LiquidAndDryBulkCargoImpl#getGrainSize()}
     *   <li>{@link LiquidAndDryBulkCargoImpl#isPressurized()}
     * </ul>
     */
    @Test
    void testConstructor() {
        LiquidAndDryBulkCargoImpl actualLiquidAndDryBulkCargoImpl = new LiquidAndDryBulkCargoImpl();
        actualLiquidAndDryBulkCargoImpl.setGrainSize(3);
        actualLiquidAndDryBulkCargoImpl.setPressurized(true);
        assertEquals(3, actualLiquidAndDryBulkCargoImpl.getGrainSize());
        assertTrue(actualLiquidAndDryBulkCargoImpl.isPressurized());
    }

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link LiquidAndDryBulkCargoImpl#LiquidAndDryBulkCargoImpl(Customer, Date, Date, int, BigDecimal, Collection, int, boolean)}
     *   <li>{@link LiquidAndDryBulkCargoImpl#setGrainSize(int)}
     *   <li>{@link LiquidAndDryBulkCargoImpl#setPressurized(boolean)}
     *   <li>{@link LiquidAndDryBulkCargoImpl#toString()}
     *   <li>{@link LiquidAndDryBulkCargoImpl#getGrainSize()}
     *   <li>{@link LiquidAndDryBulkCargoImpl#isPressurized()}
     * </ul>
     */
    @Test
    void testConstructor2() {
        Customer owner = mock(Customer.class);
        Date storageDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        Date lastInspectionDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        BigDecimal value = BigDecimal.valueOf(1L);
        LiquidAndDryBulkCargoImpl actualLiquidAndDryBulkCargoImpl = new LiquidAndDryBulkCargoImpl(owner, storageDate,
                lastInspectionDate, 1, value, new ArrayList<>(), 3, true);
        actualLiquidAndDryBulkCargoImpl.setGrainSize(3);
        actualLiquidAndDryBulkCargoImpl.setPressurized(true);
        String actualToStringResult = actualLiquidAndDryBulkCargoImpl.toString();
        assertEquals(3, actualLiquidAndDryBulkCargoImpl.getGrainSize());
        assertTrue(actualLiquidAndDryBulkCargoImpl.isPressurized());
        assertEquals("cargotype: LiquidAndDryBulkCargo , customer : null value :1, storageLocation : 1, hazards : [], grainSize : 3, isPressurized:  true",
                actualToStringResult);
    }

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link LiquidAndDryBulkCargoImpl#LiquidAndDryBulkCargoImpl(Customer, Date, Date, BigDecimal, Collection, int, boolean)}
     *   <li>{@link LiquidAndDryBulkCargoImpl#setGrainSize(int)}
     *   <li>{@link LiquidAndDryBulkCargoImpl#setPressurized(boolean)}
     *   <li>{@link LiquidAndDryBulkCargoImpl#toString()}
     *   <li>{@link LiquidAndDryBulkCargoImpl#getGrainSize()}
     *   <li>{@link LiquidAndDryBulkCargoImpl#isPressurized()}
     * </ul>
     */
    @Test
    void testConstructor3() {
        Customer owner = mock(Customer.class);
        Date storageDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        Date lastInspectionDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        BigDecimal value = BigDecimal.valueOf(1L);
        LiquidAndDryBulkCargoImpl actualLiquidAndDryBulkCargoImpl = new LiquidAndDryBulkCargoImpl(owner, storageDate,
                lastInspectionDate, value, new ArrayList<>(), 3, true);
        actualLiquidAndDryBulkCargoImpl.setGrainSize(3);
        actualLiquidAndDryBulkCargoImpl.setPressurized(true);
        String actualToStringResult = actualLiquidAndDryBulkCargoImpl.toString();
        assertEquals(3, actualLiquidAndDryBulkCargoImpl.getGrainSize());
        assertTrue(actualLiquidAndDryBulkCargoImpl.isPressurized());
        assertEquals("cargotype: LiquidAndDryBulkCargo , customer : null value :1, storageLocation : -1, hazards : [], grainSize : 3, isPressurized:  true",
                actualToStringResult);
    }
}

