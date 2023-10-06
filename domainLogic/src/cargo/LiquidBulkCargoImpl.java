package cargo;

import administration.Customer;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

public class LiquidBulkCargoImpl extends CargoImpl implements LiquidBulkCargo {

    private boolean pressurized;


    public LiquidBulkCargoImpl() {

    }

    public LiquidBulkCargoImpl(Customer owner, Date storageDate, Date lastInspectionDate, BigDecimal value, Collection<Hazard> hazards, boolean pressurized) {
        super(owner, storageDate, lastInspectionDate, value, hazards);
        this.pressurized = pressurized;
    }

    public LiquidBulkCargoImpl(Customer owner, Date storageDate, Date lastInspectionDate, int storageLocation, BigDecimal value, Collection<Hazard> hazards, boolean pressurized) {
        super(owner, storageDate, lastInspectionDate, storageLocation, value, hazards);
        this.pressurized = pressurized;
    }

    public void setPressurized(boolean pressurized) {
        this.pressurized = pressurized;
    }

    @Override
    public String toString() {
        return "Cargo Type: LiquidBulkCargo " + ", customer : " + getOwner().getName() + ", value : " + getValue() + ", storageLocation : " + getStorageLocation() + ", hazards : " + getHazards() + ", isPressurized :  " + isPressurized();
    }

    @Override
    public boolean isPressurized() {
        return isPressurized();
    }
}
