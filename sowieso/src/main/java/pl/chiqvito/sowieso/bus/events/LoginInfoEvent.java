package pl.chiqvito.sowieso.bus.events;

public class LoginInfoEvent extends Event {

    private Status status;

    public LoginInfoEvent(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "LoginInfoEvent{status=" + status + '}';
    }
}
