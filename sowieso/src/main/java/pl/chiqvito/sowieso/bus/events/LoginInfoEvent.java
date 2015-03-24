package pl.chiqvito.sowieso.bus.events;

public class LoginInfoEvent {

    public final static int LOGIN = 0;
    public final static int FAIL = 1;

    private int status;

    public LoginInfoEvent(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "LoginInfoEvent{status=" + status + '}';
    }
}
