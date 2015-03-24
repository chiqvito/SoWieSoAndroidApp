package pl.chiqvito.sowieso.bus.subscribers;

import android.content.Context;
import android.util.Log;

import java.util.List;

import de.greenrobot.event.EventBus;
import pl.chiqvito.sowieso.bus.events.ExpenseInfoEvent;
import pl.chiqvito.sowieso.bus.events.ExpenseOperationEvent;
import pl.chiqvito.sowieso.bus.events.ExpensesEvent;
import pl.chiqvito.sowieso.db.model.ExpenseEntity;
import pl.chiqvito.sowieso.db.service.ExpensesService;
import pl.chiqvito.sowieso.db.service.PropertiesService;
import pl.chiqvito.sowieso.rest.client.BasicOnResultCallback;
import pl.chiqvito.sowieso.rest.client.ExpenseSaveClient;
import pl.chiqvito.sowieso.rest.dto.ExpenseDTO;
import retrofit.client.Response;

public class ExpenseSubscriber {

    private static final String TAG = ExpenseSubscriber.class.getName();

    private final Context context;
    private final ExpensesService expensesService;
    private final PropertiesService propertiesService;

    public ExpenseSubscriber(Context context, ExpensesService expensesService, PropertiesService propertiesService) {
        this.context = context;
        this.expensesService = expensesService;
        this.propertiesService = propertiesService;
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

    public void onEventAsync(ExpenseOperationEvent event) {
        Log.v(TAG, "event:" + event);
        switch (event.getOperation()) {
            case ExpenseOperationEvent.SAVE_ALL_ON_SERVER: {
                List<ExpenseDTO> expenses = expensesService.getAllExpenseDTOs();
                ExpenseSaveClient client = new ExpenseSaveClient(context, propertiesService.getSessionId(), expenses);
                client.setOnResultCallback(new BasicOnResultCallback<Boolean>() {
                    @Override
                    public void onResponseOk(Boolean status, Response r) {
//TODO
                    }
                });
                client.execute();
                break;
            }
        }
    }

}
