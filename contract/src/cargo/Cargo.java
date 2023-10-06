package cargo;

import administration.Storable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;

public interface Cargo extends Storable, Serializable {
    BigDecimal getValue();
    Collection<Hazard> getHazards();
}
