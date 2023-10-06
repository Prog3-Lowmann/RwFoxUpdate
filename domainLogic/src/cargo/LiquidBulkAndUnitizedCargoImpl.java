package cargo;

import administration.Customer;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

public class LiquidBulkAndUnitizedCargoImpl extends CargoImpl implements LiquidBulkAndUnitisedCargo {

    private boolean pressurized;

    public boolean fragile;

    public LiquidBulkAndUnitizedCargoImpl() {

    }

    public LiquidBulkAndUnitizedCargoImpl(Customer owner, Date storageDate, Date lastInspectionDate, BigDecimal value, Collection<Hazard> hazards, boolean pressurized, boolean fragile) {
        super(owner, storageDate, lastInspectionDate, value, hazards);
        this.pressurized = pressurized;
        this.fragile = fragile;
    }

    public LiquidBulkAndUnitizedCargoImpl(Customer owner, Date storageDate, Date lastInspectionDate, int storageLocation, BigDecimal value, Collection<Hazard> hazards, boolean pressurized, boolean fragile) {
        super(owner, storageDate, lastInspectionDate, storageLocation, value, hazards);
        this.pressurized = pressurized;
        this.fragile = fragile;
    }

    @Override
    public boolean isPressurized() {
        return pressurized;
    }

    @Override
    public String toString() {
        return "Cargo Type : LiquidBulkAndUnitizedCargo " + ", customer : " + getOwner().getName() + ", value : " + getValue() + ", storageLocation : " + getStorageLocation() + ", hazards : " + getHazards() + ", isPressurized : " + isPressurized() + ", isFragile : " + isFragile();
    }

    @Override
    public boolean isFragile() {
        return fragile;
    }
}
