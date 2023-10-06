package main;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class CustomerDetailTest {

    @Test
    void testConstructor() {
        CustomerDetail actualCustomerDetail = new CustomerDetail("Name", 3);
        actualCustomerDetail.setCargoSize(3);
        actualCustomerDetail.setName("Name");
        assertEquals(3, actualCustomerDetail.getCargoSize());
        assertEquals("Name", actualCustomerDetail.getName());
    }
}

