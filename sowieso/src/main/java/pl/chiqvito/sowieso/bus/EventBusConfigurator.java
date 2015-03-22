package pl.chiqvito.sowieso.bus;

import de.greenrobot.event.EventBus;
import pl.chiqvito.sowieso.BuildConfig;
import pl.chiqvito.sowieso.bus.subscribers.ExpenseSubscriber;

public class EventBusConfigurator {

    ExpenseSubscriber expenseSubscriber;

    public EventBusConfigurator() {
    }

    private void build() {
        expenseSubscriber = new ExpenseSubscriber();
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
