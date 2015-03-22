package pl.chiqvito.sowieso.bus.events;

public class ExpenseInfoEvent {

    public final static int SAVE = 0;
    public final static int FAIL = 1;

    private int status;

    public ExpenseInfoEvent(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "ExpenseInfoEvent{status=" + status + '}';
    }
}
