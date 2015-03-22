package pl.chiqvito.sowieso;

import android.app.Application;

import pl.chiqvito.sowieso.bus.EventBusConfigurator;

public class SoWieSoApplication extends Application {

    EventBusConfigurator eventBusConfigurator;

    @Override
    public void onCreate() {
        super.onCreate();
        EventBusConfigurator.configDefaultEventBus();
        eventBusConfigurator = new EventBusConfigurator();
        eventBusConfigurator.register();
    }

    @Override
    public void onTerminate() {
        eventBusConfigurator.unregister();
        // not guaranteed to be called
        super.onTerminate();
    }

}
