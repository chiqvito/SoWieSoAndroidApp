package pl.chiqvito.sowieso.bus.events;

import pl.chiqvito.sowieso.Constants;
import pl.chiqvito.sowieso.db.model.CarEntity;

public class CarOperationEvent extends Event {

    private final Operation operation;
    private int page;
    private CarEntity car;

    public CarOperationEvent(Operation operation, int page, CarEntity car) {
        this.operation = operation;
        this.page = page;
        this.car = car;
    }

    public CarOperationEvent(Operation operation, int page) {
        this(operation, page, null);
    }

    public CarOperationEvent(Operation operation, CarEntity car) {
        this(operation, Constants.ERROR_PAGE, car);
    }

    public Operation getOperation() {
        return operation;
    }

    public int getPage() {
        return page;
    }

    public CarEntity getCar() {
        return car;
    }

    @Override
    public String toString() {
        return "CarOperationEvent{car=" + car + ", page=" + page + ", operation=" + operation + '}';
    }

}
