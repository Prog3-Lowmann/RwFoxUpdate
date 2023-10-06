package cargo;

import administration.Customer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

public class CargoImpl extends StorableImpl implements Cargo {

    private transient BigDecimal value; // to avoid serialization of bigDecimal

    private Collection<Hazard> hazards;

    public CargoImpl() {

    }

    public CargoImpl(Customer owner, Date storageDate, Date lastInspectionDate, BigDecimal value, Collection<Hazard> hazards) {
        super(owner, storageDate, lastInspectionDate);
        this.value = value;
        this.hazards = hazards;
    }

    public CargoImpl(Customer owner, Date storageDate, Date lastInspectionDate, int storageLocation, BigDecimal value, Collection<Hazard> hazards) {
        super(owner, storageDate, lastInspectionDate, storageLocation);
        this.value = value;
        this.hazards = hazards;
    }


    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public void setHazards(Collection<Hazard> hazards) {
        this.hazards = hazards;
    }

    @Override
    public BigDecimal getValue() {
        return this.value;
    }

    @Override
    public Collection<Hazard> getHazards() {
        return hazards;
    }

    @Override
    public String toString() {
        return "CargoImpl{" +
                "value=" + value +
                ", hazards=" + hazards +
                '}';
    }


    // serialization method
    private void writeObject(ObjectOutputStream out) throws IOException {

        out.defaultWriteObject();
        out.writeUTF(value.toString());
    }

    // Custom deserialization method
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.value = new BigDecimal(in.readUTF());
    }


}
