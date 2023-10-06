package cargo;

import administration.Customer;
import administration.Storable;

import java.time.Duration;
import java.util.Date;

public class StorableImpl implements Storable {

    private Customer owner;

    private Date storageDate;

    private Date lastInspectionDate;

    private int storageLocation;

    public StorableImpl() {

    }

    public StorableImpl(Customer owner, Date storageDate, Date lastInspectionDate, int storageLocation) {
        this.owner = owner;
        this.storageDate = storageDate;
        this.lastInspectionDate = lastInspectionDate;
        this.storageLocation = storageLocation;
    }

    public StorableImpl(Customer owner, Date storageDate, Date lastInspectionDate) {
        this.owner = owner;
        this.storageDate = storageDate;
        this.lastInspectionDate = lastInspectionDate;
        this.storageLocation = -1;
    }

    @Override
    public Customer getOwner() {
        return owner;
    }

    public void setOwner(Customer owner) {
        this.owner = owner;
    }

    public Date getStorageDate() {
        return storageDate;
    }

    public void setStorageDate(Date storageDate) {
        this.storageDate = storageDate;
    }

    @Override
    public Duration getDurationOfStorage() {
        return Duration.between((new Date()).toInstant(), storageDate.toInstant());
    }

    @Override
    public Date getLastInspectionDate() {
        return lastInspectionDate;
    }

    @Override
    public void setLastInspectionDate(Date date) {
        this.lastInspectionDate = date;
    }

    @Override
    public int getStorageLocation() {
        return storageLocation;
    }

    @Override
    public void setStorageLocation(int location) {
        this.storageLocation = location;
    }

    @Override
    public String toString() {
        return "StorableImpl{" +
                "owner= " + owner +
                ", storageDate= " + storageDate +
                ", lastInspectionDate= " + lastInspectionDate +
                ", storageLocation= " + storageLocation +
                '}';
    }
}
