package domainlogic.warehouse;

import Interfaces.CustomerStorageInterface;
import Interfaces.WarehouseInterface;
import administration.Customer;
import cargo.Cargo;
import cargo.CargoImpl;
import cargo.Hazard;
import customer.CustomerStorage;
import javafx.util.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import warehouse.Warehouse;
import warehouse.WarehouseCustomer;
import warehouse.WarehouseManagement;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class WarehouseManagementTest {



    @Mock
    private WarehouseManagement warehouseManagement;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        warehouseManagement = new WarehouseManagement();
    }


    @Test
    void shouldReturnAllCargoTest() {
        WarehouseInterface warehouse = Mockito.mock(WarehouseInterface.class);
        Customer customer = new WarehouseCustomer("Alice");
        Collection<Hazard> hazards = new ArrayList<>();
        hazards.add(Hazard.flammable);
        Cargo cargo = new CargoImpl(customer,new Date(),new Date(), new BigDecimal("10.10"),hazards);
        Cargo cargo2 = new CargoImpl(customer,new Date(), new Date(), new BigDecimal("10.10"),hazards);
        warehouse.addCargo(cargo);
        warehouse.addCargo(cargo2);
        Map<Integer,Cargo> expectedCargo = new  HashMap<>();
        List<Cargo> expectedCargo2 = new ArrayList<>();
        expectedCargo2.add(cargo);
        expectedCargo2.add(cargo2);

        expectedCargo.put(1,cargo);
        expectedCargo.put(2,cargo2);
        Mockito.when(warehouse.readAll()).thenReturn(new HashMap<Integer,Cargo>(expectedCargo));

        WarehouseManagement warehouseManagement = new WarehouseManagement(warehouse);
        List<Cargo> actualCargo = warehouseManagement.readAllCargo();

        Assertions.assertEquals(expectedCargo2, actualCargo);
    }

    @Test
    public void insertCargoTest() throws Exception {
        WarehouseManagement warehouseManagement = new WarehouseManagement();
        warehouseManagement.getCustomerStorage().addCustomer(new WarehouseCustomer("Alice"));
        warehouseManagement.insertCargo("LiquidAndDryBulkCargo Alice 4004,50 flammable,toxic true 10");

        Assertions.assertEquals(1, warehouseManagement.getWarehouse().readAll().size());
    }

    @Test
    void testGetIncludedHazardsAndGetExcludedHazards() {

        Customer customer = warehouseManagement.insertCustomer("Alice");

        Assertions.assertEquals(1,warehouseManagement.getCustomerStorage().getAllCustomers().size());

        Collection<Hazard> hazards = new ArrayList<>();
        hazards.add(Hazard.flammable);
        Collection<Hazard> hazards1 =  new ArrayList<>();
        hazards1.add(Hazard.toxic);
        Cargo cargo = new CargoImpl(customer,new Date(),new Date(), new BigDecimal("10.10"),hazards);
        Cargo cargo2 = new CargoImpl(customer,new Date(), new Date(), new BigDecimal("10.10"),hazards1);

        warehouseManagement.getWarehouse().addCargo(cargo);
        warehouseManagement.getWarehouse().addCargo(cargo2);

        Assertions.assertEquals(2,warehouseManagement.getWarehouse().readAll().size());

        Set<Hazard> includedHazard = warehouseManagement.getIncludedHazards();
        Set<Hazard> excludedHazard = warehouseManagement.getExcludedHazards();

        Assertions.assertEquals(2,includedHazard.size());
        Assertions.assertEquals(2,excludedHazard.size());


    }

    @Test
    public void testRemoveCustomerAndInsertCustomer() {


        warehouseManagement.insertCustomer("Alice1");
        warehouseManagement.insertCustomer("Alice2");
        warehouseManagement.insertCustomer("Alice3");

        Assertions.assertEquals(3,warehouseManagement.getCustomerStorage().getAllCustomers().size());

        warehouseManagement.removeCustomer("Alice2");

        Assertions.assertEquals(2,warehouseManagement.getCustomerStorage().getAllCustomers().size());
    }


    @Test
    public void testCargoTyType() throws Exception {
        warehouseManagement.insertCustomer("Alice");
        warehouseManagement.insertCustomer("Bob");
        warehouseManagement.insertCargo("LiquidAndDryBulkCargo Alice 4004,50 flammable,toxic true 10");
        warehouseManagement.insertCargo("UnitisedCargo Bob 10000 , false");


        Assertions.assertEquals(2,warehouseManagement.getCustomerStorage().getAllCustomers().size());
        Assertions.assertEquals(2,warehouseManagement.getWarehouse().readAll().size());

        List<Cargo> cargos = warehouseManagement.getCargoOfTye("unitisedcargo");
        Assertions.assertEquals(1,cargos.size());




    }

    @Test
    void getCustomersWithTotalCargoTest() {

        WarehouseManagement warehouseManagement1 = new WarehouseManagement();


        warehouseManagement1.insertCustomer("Alice");
        System.out.println(warehouseManagement1.getCustomerStorage().getAllCustomers());
        warehouseManagement1.inspectCargo("LiquidAndDryBulkCargo Alice 4004,50 flammable,toxic true 10");

        assertEquals(warehouseManagement1.getCustomersWithTotalCargo().size(),1);

    }

    @Test
    public void insertCustomerTest() {
        WarehouseManagement warehouseManagement1 = new WarehouseManagement();
        warehouseManagement1.insertCustomer("Alice");
        assertEquals(warehouseManagement1.getCustomerStorage().getAllCustomers().size(),1);
    }

    @Test
    public void removeCustomerTest() {
        WarehouseManagement warehouseManagement1 = new WarehouseManagement();
        warehouseManagement1.insertCustomer("Alice");
        warehouseManagement1.insertCustomer("Bob");
        warehouseManagement1.removeCustomer("Alice");
        assertEquals(warehouseManagement1.getCustomerStorage().getAllCustomers().size(),1);
    }
}
