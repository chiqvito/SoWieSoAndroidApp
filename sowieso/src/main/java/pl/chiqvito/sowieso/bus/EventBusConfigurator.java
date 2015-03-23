package pl.chiqvito.sowieso.bus;

import de.greenrobot.event.EventBus;
import pl.chiqvito.sowieso.BuildConfig;
import pl.chiqvito.sowieso.bus.subscribers.CategorySubscriber;
import pl.chiqvito.sowieso.bus.subscribers.ExpenseSubscriber;
import pl.chiqvito.sowieso.db.DbServices;

public class EventBusConfigurator {

    private DbServices dbServices;

    private ExpenseSubscriber expenseSubscriber;
    private CategorySubscriber categorySubscriber;

    public EventBusConfigurator dbServices(DbServices dbServices) {
        this.dbServices = dbServices;
        return this;
    }

    private void build() {
        expenseSubscriber = new ExpenseSubscriber(dbServices.expensesService());
        categorySubscriber = new CategorySubscriber(dbServices.categoriesService());
    }

    public void register() {
        build();
        EventBus.getDefault().register(expenseSubscriber);
        EventBus.getDefault().register(categorySubscriber);
    }

    public void unregister() {
        EventBus.getDefault().unregister(expenseSubscriber);
        EventBus.getDefault().unregister(categorySubscriber);
    }

    public static void configDefaultEventBus() {
        EventBus.builder()
                .logNoSubscriberMessages(false)
                .sendNoSubscriberEvent(false)
                .throwSubscriberException(BuildConfig.DEBUG)
                .installDefaultEventBus();
    }

}
