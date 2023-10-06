package cargo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import administration.Customer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;

class LiquidBulkCargoImplTest {

    @Test
    void testConstructor() {
        LiquidBulkCargoImpl actualLiquidBulkCargoImpl = new LiquidBulkCargoImpl();
        actualLiquidBulkCargoImpl.setPressurized(true);
        assertNull(actualLiquidBulkCargoImpl.getHazards());
        assertNull(actualLiquidBulkCargoImpl.getValue());
        assertEquals(0, actualLiquidBulkCargoImpl.getStorageLocation());
        assertNull(actualLiquidBulkCargoImpl.getStorageDate());
        assertNull(actualLiquidBulkCargoImpl.getOwner());
        assertNull(actualLiquidBulkCargoImpl.getLastInspectionDate());
    }


    @Test
    void testConstructor2() {
        Customer owner = mock(Customer.class);
        Date fromResult = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        Date fromResult1 = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        BigDecimal valueOfResult = BigDecimal.valueOf(1L);
        ArrayList<Hazard> hazardList = new ArrayList<>();
        LiquidBulkCargoImpl actualLiquidBulkCargoImpl = new LiquidBulkCargoImpl(owner, fromResult, fromResult1, 1,
                valueOfResult, hazardList, true);
        actualLiquidBulkCargoImpl.setPressurized(true);
        BigDecimal expectedValue = valueOfResult.ONE;
        BigDecimal value = actualLiquidBulkCargoImpl.getValue();
        assertSame(expectedValue, value);
        Collection<Hazard> hazards = actualLiquidBulkCargoImpl.getHazards();
        assertSame(hazardList, hazards);
        assertTrue(hazards.isEmpty());
        assertSame(fromResult, actualLiquidBulkCargoImpl.getStorageDate());
        assertSame(fromResult1, actualLiquidBulkCargoImpl.getLastInspectionDate());
        assertEquals(1, actualLiquidBulkCargoImpl.getStorageLocation());
        assertEquals(2, actualLiquidBulkCargoImpl.getDurationOfStorage().getUnits().size());
        assertEquals(1, value.signum());
        assertEquals(0, value.scale());
        assertEquals("1", value.toString());
    }


    @Test
    void testConstructor3() {
        Customer owner = mock(Customer.class);
        Date fromResult = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        Date fromResult1 = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        BigDecimal valueOfResult = BigDecimal.valueOf(1L);
        ArrayList<Hazard> hazardList = new ArrayList<>();
        LiquidBulkCargoImpl actualLiquidBulkCargoImpl = new LiquidBulkCargoImpl(owner, fromResult, fromResult1,
                valueOfResult, hazardList, true);
        actualLiquidBulkCargoImpl.setPressurized(true);
        BigDecimal expectedValue = valueOfResult.ONE;
        BigDecimal value = actualLiquidBulkCargoImpl.getValue();
        assertSame(expectedValue, value);
        Collection<Hazard> hazards = actualLiquidBulkCargoImpl.getHazards();
        assertSame(hazardList, hazards);
        assertTrue(hazards.isEmpty());
        assertSame(fromResult, actualLiquidBulkCargoImpl.getStorageDate());
        assertSame(fromResult1, actualLiquidBulkCargoImpl.getLastInspectionDate());
        assertEquals(-1, actualLiquidBulkCargoImpl.getStorageLocation());
        assertEquals(2, actualLiquidBulkCargoImpl.getDurationOfStorage().getUnits().size());
        assertEquals(1, value.signum());
        assertEquals(0, value.scale());
        assertEquals("1", value.toString());
    }



}

