package eventSystem;

import java.util.EventObject;

public class CustomerDeleteEvent extends EventObject {
    private String customerDetails;

    public CustomerDeleteEvent(Object source, String customerDetails) {
        super(source);
        this.customerDetails = customerDetails;
    }

    public String getCustomerDetails(){
        return customerDetails;
    }

    public String toString(){
        return "customer delete event is used";
    }
}
