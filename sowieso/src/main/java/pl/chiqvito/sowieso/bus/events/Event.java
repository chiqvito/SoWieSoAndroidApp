package pl.chiqvito.sowieso.bus.events;

public abstract class Event {

    public enum Operation {
        GET_ALL, GET_ALL_WITH_CATEGORY,
        CREATE, UPDATE, REMOVE, EDIT, SELECT, DOWNLOAD,
        SAVE, SAVE_ALL_ON_SERVER,
    }

    public enum Status {
        LOGIN, FAIL, SAVE
    }
}
