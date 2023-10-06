package main;

import administration.Customer;
import cargo.Hazard;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

public class CargoDetail {

    String name;
    String value;
    String owner;
    int storageLocation;
    Date lastInspectionDate;
    Duration storageDuration;

    Collection<Hazard> hazardSet;

    public Collection<Hazard> getHazardSet() {
        return hazardSet;
    }

    public void setHazardSet(Collection<Hazard> hazardSet) {
        this.hazardSet = hazardSet;
    }

    public CargoDetail(String name, String value, String owner, int storageLocation, Date lastInspectionDate, Duration storageDuration, Collection<Hazard> hazardSet) {
        this.name = name;
        this.value = value;
        this.owner = owner;
        this.storageLocation = storageLocation;
        this.lastInspectionDate = lastInspectionDate;
        this.storageDuration = storageDuration;
        this.hazardSet = hazardSet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getStorageLocation() {
        return storageLocation;
    }

    public void setStorageLocation(int storageLocation) {
        this.storageLocation = storageLocation;
    }

    public Date getLastInspectionDate() {
        return lastInspectionDate;
    }

    public void setLastInspectionDate(Date lastInspectionDate) {
        this.lastInspectionDate = lastInspectionDate;
    }

    public Duration getStorageDuration() {
        return storageDuration;
    }

    public void setStorageDuration(Duration storageDuration) {
        this.storageDuration = storageDuration;
    }
}
