package pl.chiqvito.sowieso.bus.events;

public class ExpenseInfoEvent extends Event {

    private Status status;

    public ExpenseInfoEvent(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "ExpenseInfoEvent{status=" + status + '}';
    }
}
