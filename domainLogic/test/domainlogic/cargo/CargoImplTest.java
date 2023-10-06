package domainlogic.cargo;

import cargo.CargoImpl;
import cargo.Hazard;
import administration.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class CargoImplTest {

    private CargoImpl cargo;

    @BeforeEach
    void setUp() {
        Customer owner = mock(Customer.class);
        Date storageDate = new Date();
        Date lastInspectionDate = new Date();
        BigDecimal value = new BigDecimal("100.00");
        Collection<Hazard> hazards = Arrays.asList(Hazard.explosive, Hazard.flammable);

        cargo = new CargoImpl(owner, storageDate, lastInspectionDate, value, hazards);
    }

    @Test
    void getValue() {
        assertEquals(new BigDecimal("100.00"), cargo.getValue());
    }

    @Test
    void getHazards() {
        assertEquals(2, cargo.getHazards().size());
    }

    @Test
    void setValue() {
        BigDecimal newValue = new BigDecimal("200.00");
        cargo.setValue(newValue);
        assertEquals(newValue, cargo.getValue());
    }

    @Test
    void setHazards() {
        Collection<Hazard> newHazards = Arrays.asList(Hazard.explosive);
        cargo.setHazards(newHazards);
        assertEquals(newHazards, cargo.getHazards());
    }


}
