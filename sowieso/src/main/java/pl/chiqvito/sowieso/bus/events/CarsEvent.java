package pl.chiqvito.sowieso.bus.events;

import java.util.List;

import pl.chiqvito.sowieso.db.model.CarEntity;

public class CarsEvent {

    private final List<CarEntity> cars;

    public CarsEvent(List<CarEntity> cars) {
        this.cars = cars;
    }

    public List<CarEntity> getCars() {
        return cars;
    }

    @Override
    public String toString() {
        return "CarsEvent{cars=" + cars + '}';
    }

}
