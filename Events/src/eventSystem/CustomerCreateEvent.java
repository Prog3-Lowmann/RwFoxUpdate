package eventSystem;

import java.util.EventObject;

public class CustomerCreateEvent extends EventObject {
    private String customerDetails;

    public CustomerCreateEvent(Object source, String customerDetails) {
        super(source);
        this.customerDetails = customerDetails;
    }

    public String getCustomerDetails(){
        return customerDetails;
    }

    public String toString(){
        return "customer create event is used";
    }
}
