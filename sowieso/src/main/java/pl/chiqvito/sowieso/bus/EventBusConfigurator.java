package pl.chiqvito.sowieso.bus;

import de.greenrobot.event.EventBus;
import pl.chiqvito.sowieso.BuildConfig;
import pl.chiqvito.sowieso.bus.subscribers.ExpenseSubscriber;
import pl.chiqvito.sowieso.db.DbServices;

public class EventBusConfigurator {

    private DbServices dbServices;

    private ExpenseSubscriber expenseSubscriber;

    public EventBusConfigurator dbServices(DbServices dbServices) {
        this.dbServices = dbServices;
        return this;
    }

    private void build() {
        expenseSubscriber = new ExpenseSubscriber(dbServices.expensesService());
    }

    public void register() {
        build();
        EventBus.getDefault().register(expenseSubscriber);
    }

    public void unregister() {
        EventBus.getDefault().unregister(expenseSubscriber);
    }

    public static void configDefaultEventBus() {
        EventBus.builder()
                .logNoSubscriberMessages(false)
                .sendNoSubscriberEvent(false)
                .throwSubscriberException(BuildConfig.DEBUG)
                .installDefaultEventBus();
    }

}
