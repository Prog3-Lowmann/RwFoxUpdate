package Interfaces;

import administration.Customer;
import cargo.Cargo;
import observers.Interfaces.Observer;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface WarehouseInterface {


    boolean addCargo(Cargo storable);

    void updateCargo(int storageLocation);

    void deleteCargo(int storageLocation);

    int getCapacity();

    void setCapacity(int capacity);

    Cargo getCargo(int position);

    Map<Integer, Cargo> readAll();

    Collection<Cargo> getAllCustomerCargo(Customer customer);

    List<Observer> getObservers();

    void setObservers(List<Observer> observers);

    Map<Integer, Cargo> getStorage();

    public void updateCargoStorageLocation();
}
