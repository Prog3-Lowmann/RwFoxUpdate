package eventSystem;

import java.util.EventObject;

public class SaveJBPEvent extends EventObject {
    private String customerDetails;


    public SaveJBPEvent(Object source, String customerDetails) {
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
