package cargo;

import administration.Customer;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

public class LiquidAndDryBulkCargoImpl extends CargoImpl implements LiquidAndDryBulkCargo {

    private int grainSize;
    private boolean pressurized;



    public LiquidAndDryBulkCargoImpl() {

    }

    public LiquidAndDryBulkCargoImpl(Customer owner, Date storageDate, Date lastInspectionDate, BigDecimal value, Collection<Hazard> hazards, int grainSize, boolean pressurized) {
        super(owner, storageDate, lastInspectionDate, value, hazards);
        this.grainSize = grainSize;
        this.pressurized = pressurized;
    }

    public LiquidAndDryBulkCargoImpl(Customer owner, Date storageDate, Date lastInspectionDate, int storageLocation, BigDecimal value, Collection<Hazard> hazards, int grainSize, boolean pressurized) {
        super(owner, storageDate, lastInspectionDate, storageLocation, value, hazards);
        this.grainSize = grainSize;
        this.pressurized = pressurized;
    }

    public void setGrainSize(int grainSize) {
        this.grainSize = grainSize;
    }

    public void setPressurized(boolean pressurized) {
        this.pressurized = pressurized;
    }

    @Override
    public int getGrainSize() {
        return grainSize;
    }

    @Override
    public boolean isPressurized() {
        return pressurized;
    }

    @Override
    public String toString() {
        return "Cargo Type: LiquidAndDryBulkCargo " +", customer : "+getOwner().getName()+ " value :" + getValue() +", storageLocation : " + getStorageLocation() +  ", hazards : " + getHazards() + ", grainSize : " + getGrainSize()+ ", isPressurized:  " + isPressurized();
    }
}
