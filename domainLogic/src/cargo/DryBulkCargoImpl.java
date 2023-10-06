package cargo;

import administration.Customer;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

public class DryBulkCargoImpl extends CargoImpl implements DryBulkCargo {

    private int grainSize;


    @Override
    public String toString() {
        return "Cargo type: DryBulkCargo " + ", customer:  " + getOwner().getName() + ", value: " + getValue() + ", storageLocation : " + getStorageLocation() + ", hazards:  " + getHazards() + ", grainSize :  " + getGrainSize();
    }

    public DryBulkCargoImpl() {

    }

    public DryBulkCargoImpl(Customer owner, Date storageDate, Date lastInspectionDate, BigDecimal value, Collection<Hazard> hazards, int grainSize) {
        super(owner, storageDate, lastInspectionDate, value, hazards);
        this.grainSize = grainSize;
    }


    public DryBulkCargoImpl(Customer owner, Date storageDate, Date lastInspectionDate, int storageLocation, BigDecimal value, Collection<Hazard> hazards, int grainSize) {
        super(owner, storageDate, lastInspectionDate, storageLocation, value, hazards);
        this.grainSize = grainSize;
    }

    @Override
    public int getGrainSize() {
        return grainSize;
    }
}
