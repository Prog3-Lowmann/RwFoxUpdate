package cargo;

import administration.Customer;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

public class UnitisedCargoImpl extends CargoImpl implements UnitisedCargo {

    public boolean fragile;


    public UnitisedCargoImpl() {

    }

    public UnitisedCargoImpl(Customer owner, Date storageDate, Date lastInspectionDate, BigDecimal value, Collection<Hazard> hazards, boolean fragile) {
        super(owner, storageDate, lastInspectionDate, value, hazards);
        this.fragile = fragile;
    }

    public UnitisedCargoImpl(Customer owner, Date storageDate, Date lastInspectionDate, int storageLocation, BigDecimal value, Collection<Hazard> hazards, boolean fragile) {
        super(owner, storageDate, lastInspectionDate, storageLocation, value, hazards);
        this.fragile = fragile;
    }

    @Override
    public boolean isFragile() {
        return fragile;
    }

    @Override
    public String toString() {
        return "Cargo Type :  UnitisedCargo" + ", customer:  " + getOwner().getName() + ", value :" + getValue() + ", storageLocation : " + getStorageLocation() + ", Hazards : " + getHazards() + " fragile : " + isFragile();
    }
}
