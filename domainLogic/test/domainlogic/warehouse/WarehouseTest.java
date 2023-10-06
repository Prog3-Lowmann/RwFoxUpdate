package domainlogic.warehouse;



import administration.Customer;
import cargo.Cargo;
import cargo.DryBulkCargoImpl;
import cargo.Hazard;
import observers.Interfaces.Observer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import warehouse.Warehouse;
import warehouse.WarehouseCustomer;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;


import static org.junit.jupiter.api.Assertions.*;


public class WarehouseTest {

    @Mock
    private Cargo mockCargo;

    @Mock
    private Observer mockObserver;

    @Mock
    private Customer customer;

    @Mock
    private DryBulkCargoImpl dryBulkCargo;



    private Warehouse warehouse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        warehouse = new Warehouse(10);
        customer = new WarehouseCustomer("Mariami");

        mockCargo  = new DryBulkCargoImpl(customer,new Date(),new Date(),new BigDecimal("1.1"),Collections.singletonList(Hazard.radioactive),10);
       //678 dryBulkCargo  = new DryBulkCargoImpl(customer,new Date(),new Date(),new BigDecimal("1.1"),Collections.singletonList(Hazard.radioactive),10);

        warehouse.attach(mockObserver);
    }

    @Test
    void testAddCargo() {

        Assertions.assertTrue(warehouse.addCargo(mockCargo));
    }

    @Test
    void testDeleteCargo1() {
        Assertions.assertFalse(warehouse.deleteCargo(mockCargo));
    }

    @Test
    void testDeleteCargo2() {
        warehouse.addCargo(mockCargo);
        Assertions.assertTrue(warehouse.deleteCargo(mockCargo));
    }

    @Test
    void addCargo_shouldAddCargoWhenCapacityNotExceeded() {
        Assertions.assertTrue(warehouse.addCargo(mockCargo));
        Assertions.assertEquals(2, warehouse.getIndex());
        Assertions.assertEquals(mockCargo, warehouse.getStorage().get(1));
    }

    @Test
    void testUpdateCargo() {
        System.out.println(warehouse.getStorage());
        warehouse.addCargo(mockCargo);
        System.out.println(warehouse.getStorage());
        warehouse.updateCargo(1);
        System.out.println(warehouse.getStorage().get(1).getStorageLocation());
    }




}
