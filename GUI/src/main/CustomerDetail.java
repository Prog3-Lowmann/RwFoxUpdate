package main;

public class CustomerDetail {
    String name;
    int cargoSize;

    public CustomerDetail(String name, int cargoSize) {
        this.name = name;
        this.cargoSize = cargoSize;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCargoSize() {
        return cargoSize;
    }

    public void setCargoSize(int cargoSize) {
        this.cargoSize = cargoSize;
    }
}
