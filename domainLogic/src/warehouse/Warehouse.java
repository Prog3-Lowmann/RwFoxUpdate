package warehouse;

import Interfaces.WarehouseInterface;
import administration.Customer;
import cargo.Cargo;
import observers.Interfaces.Observer;
import observers.Interfaces.Subject;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;


public class Warehouse implements WarehouseInterface, Serializable, Subject {

    private static final long serialVersionUID = 22222L;

    private int capacity;
    private Map<Integer, Cargo> storage;
    public List<Observer> observers = new ArrayList<>();
    private int index;

    public Warehouse() {
        this.capacity = 10;
        storage = new HashMap<>();
        this.index = 1;
        observers = new ArrayList<>();
    }

    public Warehouse(int capacity) {
        this.capacity = capacity;
        storage = new HashMap<>();
        this.index = 1;
        observers = new ArrayList<>();
    }


    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public Map<Integer, Cargo> getStorage() {
        return storage;
    }

    public void setStorage(Map<Integer, Cargo> storage) {
        this.storage = storage;
    }

    @Override
    public List<Observer> getObservers() {
        return observers;
    }

    @Override
    public void setObservers(List<Observer> observers) {
        this.observers.addAll(observers);
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }


    @Override
    public boolean addCargo(Cargo cargo) {

        // checking capacity is not exceeded and cargo belongs to some customer
        if (storage.size() < capacity && cargo.getOwner() != null) {
            cargo.setStorageLocation(index);
            storage.put(index, cargo);
            index++;
            return true;
        }
        return false;
    }


    public Boolean deleteCargo(Cargo cargo) {

        if (storage.size() == 0 || !storage.containsKey(cargo.getStorageLocation())) {
            return false;
        } else {
            storage.remove(cargo.getStorageLocation());
            notifyObservers();
            return true;
        }
    }

    @Override
    public void updateCargoStorageLocation() {
        Collection<Cargo> cargos = storage.values();
        Map<Integer, Cargo> newStorage = new HashMap<Integer, Cargo>();

        int i = 1;
        for (Cargo c : cargos) {
            c.setStorageLocation(i);
            newStorage.put(i, c);
            i++;
        }
        storage.clear();
        storage.putAll(newStorage);
        index = i;
    }

    @Override
    public void updateCargo(int storageLocation) {
        Cargo cargo = storage.get(storageLocation);
        if (cargo == null) {

            System.out.println("could not find item at location " + storageLocation);
            return;
        }
        cargo.setLastInspectionDate(new Date());
        storage.put(storageLocation, cargo);
        notifyObservers();
    }

    @Override
    public void deleteCargo(int storageLocation) {
        storage.remove(storageLocation);
        notifyObservers();
    }

    @Override
    public Cargo getCargo(int position) {
        return storage.get(position);
    }

    @Override
    public Map<Integer, Cargo> readAll() {
        return storage;
    }

    @Override
    public Collection<Cargo> getAllCustomerCargo(Customer customer) {
        return storage.values().stream().filter(storable -> {
            if (storable.getOwner().getName().equals(customer.getName()))
                return true;
            else
                return false;
        }).collect(Collectors.toList());
    }


    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    @Override
    public String toString() {
        return "Warehouse{" +
                "capacity=" + capacity +
                ", storage=" + storage +
                ", index=" + index +
                '}';
    }

}
