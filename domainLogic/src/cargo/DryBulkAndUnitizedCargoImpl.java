package cargo;

import administration.Customer;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

public class DryBulkAndUnitizedCargoImpl extends CargoImpl implements DryBulkAndUnitisedCargo {

    private boolean fragile;


    @Override
    public String toString() {
        return "Cargo type : DryBulkAndUnitizedCargo, " + "customer : " + getOwner().getName() + ", value: " + getValue() + ", storageLocation : " + getStorageLocation() + ", hazards : " + getHazards() + ", grainSize: " + getGrainSize() + ", fragile :" + isFragile();
    }

    private int grainSize;

    public DryBulkAndUnitizedCargoImpl() {

    }

    public DryBulkAndUnitizedCargoImpl(Customer owner, Date storageDate, Date lastInspectionDate, BigDecimal value, Collection<Hazard> hazards, boolean fragile, int grainSize) {
        super(owner, storageDate, lastInspectionDate, value, hazards);
        this.fragile = fragile;
        this.grainSize = grainSize;
    }

    public DryBulkAndUnitizedCargoImpl(Customer owner, Date storageDate, Date lastInspectionDate, int storageLocation, BigDecimal value, Collection<Hazard> hazards, boolean fragile, int grainSize) {
        super(owner, storageDate, lastInspectionDate, storageLocation, value, hazards);
        this.fragile = fragile;
        this.grainSize = grainSize;
    }

    @Override
    public boolean isFragile() {
        return fragile;
    }

    @Override
    public int getGrainSize() {
        return grainSize;
    }


    public void setFragile(boolean fragile) {
        this.fragile = fragile;
    }

    public void setGrainSize(int grainSize) {
        this.grainSize = grainSize;
    }

}
