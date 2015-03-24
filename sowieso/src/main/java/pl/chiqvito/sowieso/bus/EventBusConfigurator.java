package pl.chiqvito.sowieso.bus;

import android.content.Context;

import de.greenrobot.event.EventBus;
import pl.chiqvito.sowieso.BuildConfig;
import pl.chiqvito.sowieso.bus.subscribers.CategorySubscriber;
import pl.chiqvito.sowieso.bus.subscribers.ExpenseSubscriber;
import pl.chiqvito.sowieso.bus.subscribers.UserSubscriber;
import pl.chiqvito.sowieso.db.DbServices;

public class EventBusConfigurator {

    private Context context;
    private DbServices dbServices;

    private UserSubscriber userSubscriber;
    private ExpenseSubscriber expenseSubscriber;
    private CategorySubscriber categorySubscriber;

    public EventBusConfigurator(Context context) {
        this.context = context;
    }

    public EventBusConfigurator dbServices(DbServices dbServices) {
        this.dbServices = dbServices;
        return this;
    }

    private void build() {
        userSubscriber = new UserSubscriber(context, dbServices.propertiesService());
        expenseSubscriber = new ExpenseSubscriber(context, dbServices.expensesService(), dbServices.propertiesService());
        categorySubscriber = new CategorySubscriber(context, dbServices.categoriesService());
    }

    public void register() {
        build();
        EventBus.getDefault().register(userSubscriber);
        EventBus.getDefault().register(expenseSubscriber);
        EventBus.getDefault().register(categorySubscriber);
    }

    public void unregister() {
        EventBus.getDefault().unregister(userSubscriber);
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
