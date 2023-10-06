package administration;

import java.io.Serializable;
import java.time.Duration;
import java.util.Date;

public interface Storable extends Serializable {
    Customer getOwner();
    /**
     * liefert die vergangene Zeit seit dem Einfügen
     * @return vergangene Zeit oder null wenn kein Einfügedatum gesetzt
     */
    Duration getDurationOfStorage();
    Date getLastInspectionDate();
    void setLastInspectionDate(Date date);
    int getStorageLocation();
    void setStorageLocation(int location);
}
