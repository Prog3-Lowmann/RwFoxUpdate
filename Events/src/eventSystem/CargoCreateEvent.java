package eventSystem;

import java.util.EventObject;

public class CargoCreateEvent extends EventObject {
    private String cargoDetails;
    public CargoCreateEvent(Object source, String cargoDetails) {
        super(source);
        this.cargoDetails = cargoDetails;
    }

    public String getCargoDetails(){
        return cargoDetails;
    }

    public String toString(){
        return "Cargo create method is used.";
    }

}
