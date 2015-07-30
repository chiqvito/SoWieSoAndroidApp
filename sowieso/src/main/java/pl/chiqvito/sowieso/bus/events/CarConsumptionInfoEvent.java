package pl.chiqvito.sowieso.bus.events;

public class CarConsumptionInfoEvent extends Event {

    private Status status;

    public CarConsumptionInfoEvent(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "CarConsumptionInfoEvent{status=" + status + '}';
    }
}
