package pl.chiqvito.sowieso.bus.subscribers;

import android.util.Log;

import java.util.List;

import de.greenrobot.event.EventBus;
import pl.chiqvito.sowieso.bus.events.ExpenseInfoEvent;
import pl.chiqvito.sowieso.bus.events.ExpenseOperationEvent;
import pl.chiqvito.sowieso.bus.events.ExpensesEvent;
import pl.chiqvito.sowieso.db.model.ExpenseEntity;
import pl.chiqvito.sowieso.db.service.ExpensesService;

public class ExpenseSubscriber {

    private static final String TAG = ExpenseSubscriber.class.getName();

    private final ExpensesService expensesService;

    public ExpenseSubscriber(ExpensesService expensesService) {
        this.expensesService = expensesService;
    }

    public void onEventBackgroundThread(ExpenseOperationEvent event) {
        Log.v(TAG, "event:" + event);
        switch (event.getOperation()) {
            case ExpenseOperationEvent.SAVE: {
                if (expensesService.save(event.getExpense()))
                    EventBus.getDefault().post(new ExpenseInfoEvent(ExpenseInfoEvent.SAVE));
                else
                    EventBus.getDefault().post(new ExpenseInfoEvent(ExpenseInfoEvent.FAIL));
                break;
            }
            case ExpenseOperationEvent.GET_ALL_WITH_CATEGORY: {
                List<ExpenseEntity> expenseEntities = expensesService.getAllWithCategories();
                EventBus.getDefault().post(new ExpensesEvent(expenseEntities));
                break;
            }
        }
    }

}
