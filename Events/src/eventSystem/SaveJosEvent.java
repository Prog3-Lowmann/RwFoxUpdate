package eventSystem;

import java.util.EventObject;

public class SaveJosEvent extends EventObject {
    private String customerDetails;


    public SaveJosEvent(Object source, String customerDetails) {
        super(source);
        this.customerDetails = customerDetails;
    }

    public String getCargoDetails(){
        return customerDetails;
    }

    public String toString(){
        return "customer read event is used";
    }
}
