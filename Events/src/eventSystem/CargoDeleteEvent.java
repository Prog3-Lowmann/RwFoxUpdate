package eventSystem;

import java.util.EventObject;

public class CargoDeleteEvent extends EventObject {
    private String location;

    public CargoDeleteEvent(Object source, String location) {
        super(source);
        this.location = location;
    }

    public String getCargoDetails() { return this.location; }

    public String toString(){
        return "Cargo delete method is used.";
    }

}
