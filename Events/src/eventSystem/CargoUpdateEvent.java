package eventSystem;

import java.util.EventObject;

public class CargoUpdateEvent extends EventObject {

    private String location;

    public CargoUpdateEvent(Object source, String location) {
        super(source);
        this.location = location;
    }

    public String getCargoDetails() {
        return this.location;
    }

    public String toString(){
        return "Cargo update method is used.";
    }
}
