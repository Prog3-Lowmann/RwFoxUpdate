package warehouse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import administration.Customer;
import cargo.Cargo;
import cargo.CargoImpl;

import java.util.ArrayList;
import java.util.Date;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import observers.Interfaces.Observer;
import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;

class WarehouseTest {

    @Test
    void testConstructor() {
        Warehouse actualWarehouse = new Warehouse();
        actualWarehouse.setCapacity(3);
        actualWarehouse.setIndex(1);
        actualWarehouse.setStorage(new HashMap<>());
        String actualToStringResult = actualWarehouse.toString();
        assertEquals(3, actualWarehouse.getCapacity());
        assertEquals(1, actualWarehouse.getIndex());
        List<Observer> expectedObservers = actualWarehouse.observers;
        assertSame(expectedObservers, actualWarehouse.getObservers());
        Map<Integer, Cargo> expectedStorage = actualWarehouse.readAll();
        assertSame(expectedStorage, actualWarehouse.getStorage());
        assertEquals("Warehouse{capacity=3, storage={}, index=1}", actualToStringResult);
    }


    @Test
    void testConstructor2() {
        Warehouse actualWarehouse = new Warehouse(3);
        actualWarehouse.setCapacity(3);
        actualWarehouse.setIndex(1);
        actualWarehouse.setStorage(new HashMap<>());
        String actualToStringResult = actualWarehouse.toString();
        assertEquals(3, actualWarehouse.getCapacity());
        assertEquals(1, actualWarehouse.getIndex());
        List<Observer> expectedObservers = actualWarehouse.observers;
        assertSame(expectedObservers, actualWarehouse.getObservers());
        Map<Integer, Cargo> expectedStorage = actualWarehouse.readAll();
        assertSame(expectedStorage, actualWarehouse.getStorage());
        assertEquals("Warehouse{capacity=3, storage={}, index=1}", actualToStringResult);
    }


    @Test
    void testSetObservers() {
        Warehouse warehouse = new Warehouse(3);
        warehouse.setObservers(new ArrayList<>());
        assertTrue(warehouse.getObservers().isEmpty());
    }


    @Test
    void testSetObservers2() {
        Cargo cargo = mock(Cargo.class);
        doNothing().when(cargo).setStorageLocation(anyInt());
        when(cargo.getOwner()).thenReturn(mock(Customer.class));

        Warehouse warehouse = new Warehouse(3);
        warehouse.addCargo(cargo);
        warehouse.setObservers(new ArrayList<>());
        verify(cargo).getOwner();
        verify(cargo).setStorageLocation(anyInt());
        assertTrue(warehouse.getObservers().isEmpty());
    }


    @Test
    void testAddCargo() {
        ArrayList<Object> objectList = new ArrayList<>();
        objectList.add(mock(Observer.class));
        Warehouse warehouse = new Warehouse(3);


        CargoImpl cargoImpl = new CargoImpl();
        cargoImpl.setOwner(null);
        assertFalse(warehouse.addCargo(cargoImpl));
    }


    @Test
    void testAddCargo2() {
        Warehouse warehouse = new Warehouse(3);
        Cargo cargo = mock(Cargo.class);
        doNothing().when(cargo).setStorageLocation(anyInt());
        when(cargo.getOwner()).thenReturn(mock(Customer.class));
        assertTrue(warehouse.addCargo(cargo));
        verify(cargo).getOwner();
        verify(cargo).setStorageLocation(anyInt());
        assertEquals(1, warehouse.readAll().size());
        assertEquals(2, warehouse.getIndex());
    }


    @Test
    void testAddCargo3() {
        Warehouse warehouse = new Warehouse(0);
        Cargo cargo = mock(Cargo.class);
        doNothing().when(cargo).setStorageLocation(anyInt());
        when(cargo.getOwner()).thenReturn(mock(Customer.class));
        assertFalse(warehouse.addCargo(cargo));
    }


    @Test
    void testAddCargo4() {
        Observer observer = mock(Observer.class);
        doNothing().when(observer).update();

        ArrayList<Object> objectList = new ArrayList<>();
        objectList.add(observer);
        Warehouse warehouse = new Warehouse(3);


        CargoImpl cargoImpl = new CargoImpl();
        cargoImpl.setOwner(mock(Customer.class));
        assertTrue(warehouse.addCargo(cargoImpl));

        assertEquals(1, cargoImpl.getStorageLocation());
        assertEquals(1, warehouse.readAll().size());
        assertEquals(2, warehouse.getIndex());
    }


    @Test
    void testDeleteCargo() {
        Warehouse warehouse = new Warehouse(3);
        warehouse.deleteCargo(1);
        assertTrue(warehouse.readAll().isEmpty());
    }








    @Test
    void testUpdateCargo3() {
        Cargo cargo = mock(Cargo.class);
        doNothing().when(cargo).setLastInspectionDate((Date) any());
        doNothing().when(cargo).setStorageLocation(anyInt());
        when(cargo.getOwner()).thenReturn(mock(Customer.class));

        Warehouse warehouse = new Warehouse(3);
        warehouse.addCargo(cargo);
        warehouse.updateCargo(1);
        verify(cargo).getOwner();
        verify(cargo).setLastInspectionDate((Date) any());
        verify(cargo).setStorageLocation(anyInt());
        assertEquals(1, warehouse.readAll().size());
    }

    void testGetCargo() {
        assertNull((new Warehouse(3)).getCargo(1));
    }


    @Test
    void testGetCargo2() {
        Cargo cargo = mock(Cargo.class);
        doNothing().when(cargo).setStorageLocation(anyInt());
        when(cargo.getOwner()).thenReturn(mock(Customer.class));

        Warehouse warehouse = new Warehouse(3);
        warehouse.addCargo(cargo);
        warehouse.getCargo(1);
        verify(cargo).getOwner();
        verify(cargo).setStorageLocation(anyInt());
    }


    @Test
    void testReadAll() {
        assertTrue((new Warehouse(3)).readAll().isEmpty());
    }


    @Test
    void testReadAll2() {
        Cargo cargo = mock(Cargo.class);
        doNothing().when(cargo).setStorageLocation(anyInt());
        when(cargo.getOwner()).thenReturn(mock(Customer.class));

        Warehouse warehouse = new Warehouse(3);
        warehouse.addCargo(cargo);
        assertEquals(1, warehouse.readAll().size());
        verify(cargo).getOwner();
        verify(cargo).setStorageLocation(anyInt());
    }


    @Test
    void testGetAllCustomerCargo() {
        assertTrue((new Warehouse(3)).getAllCustomerCargo(mock(Customer.class)).isEmpty());
    }


    @Test
    void testGetAllCustomerCargo2() {
        Customer customer = mock(Customer.class);
        when(customer.getName()).thenReturn("Name");
        Cargo cargo = mock(Cargo.class);
        doNothing().when(cargo).setStorageLocation(anyInt());
        when(cargo.getOwner()).thenReturn(customer);

        Warehouse warehouse = new Warehouse(3);
        warehouse.addCargo(cargo);
        Customer customer1 = mock(Customer.class);
        when(customer1.getName()).thenReturn("Name");
        assertEquals(1, warehouse.getAllCustomerCargo(customer1).size());
        verify(cargo, atLeast(1)).getOwner();
        verify(cargo).setStorageLocation(anyInt());
        verify(customer).getName();
        verify(customer1).getName();
    }


    @Test
    void testGetAllCustomerCargo3() {
        Customer customer = mock(Customer.class);
        when(customer.getName()).thenReturn("Name");
        Cargo cargo = mock(Cargo.class);
        doNothing().when(cargo).setStorageLocation(anyInt());
        when(cargo.getOwner()).thenReturn(customer);

        Warehouse warehouse = new Warehouse();
        warehouse.addCargo(cargo);
        Customer customer1 = mock(Customer.class);
        when(customer1.getName()).thenReturn("foo");
        assertTrue(warehouse.getAllCustomerCargo(customer1).isEmpty());
        verify(cargo, atLeast(1)).getOwner();
        verify(cargo).setStorageLocation(anyInt());
        verify(customer).getName();
        verify(customer1).getName();
    }


    @Test
    void testGetAllCustomerCargo4() {
        Customer customer = mock(Customer.class);
        when(customer.getName()).thenReturn("Name");
        Cargo cargo = mock(Cargo.class);
        doNothing().when(cargo).setStorageLocation(anyInt());
        when(cargo.getOwner()).thenReturn(customer);
        Customer customer1 = mock(Customer.class);
        when(customer1.getName()).thenReturn("Name");
        Cargo cargo1 = mock(Cargo.class);
        doNothing().when(cargo1).setStorageLocation(anyInt());
        when(cargo1.getOwner()).thenReturn(customer1);

        Warehouse warehouse = new Warehouse(10);
        warehouse.addCargo(cargo1);
        warehouse.addCargo(cargo);
        Customer customer2 = mock(Customer.class);
        when(customer2.getName()).thenReturn("");
        assertTrue(warehouse.getAllCustomerCargo(customer2).isEmpty());
        verify(cargo1, atLeast(1)).getOwner();
        verify(cargo1).setStorageLocation(anyInt());
        verify(customer1).getName();
        verify(cargo, atLeast(1)).getOwner();
        verify(cargo).setStorageLocation(anyInt());
        verify(customer).getName();
        verify(customer2, atLeast(1)).getName();
    }


    @Test
    void testAttach() {
        Warehouse warehouse = new Warehouse(3);
        warehouse.attach(mock(Observer.class));
        assertEquals(1, warehouse.getObservers().size());
    }


    @Test
    void testDetach() {
        Warehouse warehouse = new Warehouse(3);
        warehouse.detach(mock(Observer.class));
        assertTrue(warehouse.getObservers().isEmpty());
    }




    @Test
    void testNotifyObservers() {
        Warehouse warehouse = new Warehouse(3);
        warehouse.notifyObservers();
        assertEquals(3, warehouse.getCapacity());
        assertTrue(warehouse.readAll().isEmpty());
        assertTrue(warehouse.getObservers().isEmpty());
        assertEquals(1, warehouse.getIndex());
    }


}

