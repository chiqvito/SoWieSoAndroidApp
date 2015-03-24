package pl.chiqvito.sowieso;

import android.app.Application;

import pl.chiqvito.sowieso.bus.EventBusConfigurator;
import pl.chiqvito.sowieso.db.DataManagerImpl;
import pl.chiqvito.sowieso.db.DbManager;
import pl.chiqvito.sowieso.db.DbServices;

public class SoWieSoApplication extends Application {

    private DbServices dbServices;
    private EventBusConfigurator eventBusConfigurator;

    @Override
    public void onCreate() {
        super.onCreate();
        dbServices = new DataManagerImpl(this);
        EventBusConfigurator.configDefaultEventBus();
        eventBusConfigurator = new EventBusConfigurator(this)
                .dbServices(dbServices);
        eventBusConfigurator.register();
    }

    @Override
    public void onTerminate() {
        eventBusConfigurator.unregister();
        ((DbManager) dbServices).closeDb();
        // not guaranteed to be called
        super.onTerminate();
    }

}
