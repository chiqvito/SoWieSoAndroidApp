package pl.chiqvito.sowieso.bus.subscribers;

import android.util.Log;

import de.greenrobot.event.EventBus;
import pl.chiqvito.sowieso.bus.events.ExpenseEvent;
import pl.chiqvito.sowieso.bus.events.ExpenseInfoEvent;
import pl.chiqvito.sowieso.db.service.ExpensesService;

public class ExpenseSubscriber {

    private static final String TAG = ExpenseSubscriber.class.getName();

    private final ExpensesService expensesService;

    public ExpenseSubscriber(ExpensesService expensesService) {
        this.expensesService = expensesService;
    }

    public void onEventBackgroundThread(ExpenseEvent event) {
        Log.v(TAG, "event:" + event);
        if (expensesService.save(event.getExpense()))
            EventBus.getDefault().post(new ExpenseInfoEvent(ExpenseInfoEvent.SAVE));
        else
            EventBus.getDefault().post(new ExpenseInfoEvent(ExpenseInfoEvent.FAIL));
    }

}
