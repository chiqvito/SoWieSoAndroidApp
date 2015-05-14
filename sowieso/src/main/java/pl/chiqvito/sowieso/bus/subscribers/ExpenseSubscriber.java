package pl.chiqvito.sowieso.bus.subscribers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import de.greenrobot.event.EventBus;
import pl.chiqvito.sowieso.R;
import pl.chiqvito.sowieso.bus.events.Event;
import pl.chiqvito.sowieso.bus.events.ExpenseInfoEvent;
import pl.chiqvito.sowieso.bus.events.ExpenseOperationEvent;
import pl.chiqvito.sowieso.bus.events.ExpensesEvent;
import pl.chiqvito.sowieso.bus.events.ExpensesReportEvent;
import pl.chiqvito.sowieso.bus.events.ExpensesReportOperationEvent;
import pl.chiqvito.sowieso.bus.events.SwitchFragmentEvent;
import pl.chiqvito.sowieso.db.model.ExpenseEntity;
import pl.chiqvito.sowieso.db.service.ExpensesService;
import pl.chiqvito.sowieso.db.service.PropertiesService;
import pl.chiqvito.sowieso.rest.client.BasicOnResultCallback;
import pl.chiqvito.sowieso.rest.client.ExpenseReportYearClient;
import pl.chiqvito.sowieso.rest.client.ExpenseReportYearMonthCategoryClient;
import pl.chiqvito.sowieso.rest.client.ExpenseReportYearMonthClient;
import pl.chiqvito.sowieso.rest.client.ExpenseSaveClient;
import pl.chiqvito.sowieso.rest.dto.ExpenseDTO;
import pl.chiqvito.sowieso.rest.dto.ExpenseReportDTO;
import pl.chiqvito.sowieso.rest.dto.ExpenseReportYearDTO;
import pl.chiqvito.sowieso.rest.dto.ExpenseReportYearMonthCategoryDTO;
import pl.chiqvito.sowieso.rest.dto.ExpenseReportYearMonthDTO;
import pl.chiqvito.sowieso.ui.fragment.FragmentBuilder;
import pl.chiqvito.sowieso.utils.Boast;
import retrofit.RetrofitError;
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
            case SAVE: {
                if (expensesService.save(event.getExpense()))
                    EventBus.getDefault().post(new ExpenseInfoEvent(Event.Status.SAVE));
                else
                    EventBus.getDefault().post(new ExpenseInfoEvent(Event.Status.FAIL));
                break;
            }
            case GET_ALL_WITH_CATEGORY: {
                List<ExpenseEntity> expenseEntities = expensesService.getAllWithCategories();
                EventBus.getDefault().post(new ExpensesEvent(expenseEntities));
                break;
            }
            case EDIT: {
                EventBus.getDefault().post(new SwitchFragmentEvent(FragmentBuilder.FragmentName.EXPENSE_EDIT, event.getExpense()));
                break;
            }
            case REMOVE: {
                expensesService.delete(event.getExpense());
                List<ExpenseEntity> expenseEntities = expensesService.getAllWithCategories();
                EventBus.getDefault().post(new ExpensesEvent(expenseEntities));
                break;
            }
        }
    }

    public void onEventAsync(ExpenseOperationEvent event) {
        Log.v(TAG, "event:" + event);
        switch (event.getOperation()) {
            case SAVE_ALL_ON_SERVER: {
                List<ExpenseDTO> expenses = expensesService.getAllExpenseDTOs();
                ExpenseSaveClient client = new ExpenseSaveClient(context, propertiesService.getSessionId(), expenses);
                client.setOnResultCallback(new BasicOnResultCallback<Boolean>() {
                    @Override
                    public void onResponseOk(Boolean status, Response r) {
                        if (status) {
                            expensesService.deleteAll();
                            EventBus.getDefault().post(new SwitchFragmentEvent(FragmentBuilder.FragmentName.EXPENSE_LIST));
                            Boast.showText(context, context.getString(R.string.msg_data_saved), Toast.LENGTH_SHORT);
                        } else {
                            Boast.showText(context, context.getString(R.string.msg_data_not_saved), Toast.LENGTH_SHORT);
                        }
                    }
                });
                client.execute();
                break;
            }
        }
    }

    public void onEventAsync(ExpensesReportOperationEvent event) {
        Log.v(TAG, "event:" + event);
        switch (event.getFragmentName()) {
            case EXPENSE_REPORT_YEAR_MONTH_CATEGORY: {
                ExpenseReportYearMonthCategoryClient client = new ExpenseReportYearMonthCategoryClient(context, propertiesService.getSessionId(), event.getYear(), event.getMonth());
                client.setOnResultCallback(new BasicOnResultCallback<List<ExpenseReportYearMonthCategoryDTO>>() {
                    @Override
                    public void onResponseOk(List<ExpenseReportYearMonthCategoryDTO> expenses, Response r) {
                        EventBus.getDefault().post(new ExpensesReportEvent(expenses));
                    }

                    @Override
                    public void onFail(RetrofitError error) {
                        super.onFail(error);
                        EventBus.getDefault().post(new ExpensesReportEvent(Collections.<ExpenseReportDTO>emptyList()));
                    }
                });
                client.execute();
                break;
            }
            case EXPENSE_REPORT_YEAR_MONTH: {
                ExpenseReportYearMonthClient client = new ExpenseReportYearMonthClient(context, propertiesService.getSessionId(), event.getYear());
                client.setOnResultCallback(new BasicOnResultCallback<List<ExpenseReportYearMonthDTO>>() {
                    @Override
                    public void onResponseOk(List<ExpenseReportYearMonthDTO> expenses, Response r) {
                        EventBus.getDefault().post(new ExpensesReportEvent(expenses));
                    }

                    @Override
                    public void onFail(RetrofitError error) {
                        super.onFail(error);
                        EventBus.getDefault().post(new ExpensesReportEvent(Collections.<ExpenseReportDTO>emptyList()));
                    }
                });
                client.execute();
                break;
            }
            case EXPENSE_REPORT_YEAR: {
                ExpenseReportYearClient client = new ExpenseReportYearClient(context, propertiesService.getSessionId());
                client.setOnResultCallback(new BasicOnResultCallback<List<ExpenseReportYearDTO>>() {
                    @Override
                    public void onResponseOk(List<ExpenseReportYearDTO> expenses, Response r) {
                        EventBus.getDefault().post(new ExpensesReportEvent(expenses));
                    }

                    @Override
                    public void onFail(RetrofitError error) {
                        super.onFail(error);
                        EventBus.getDefault().post(new ExpensesReportEvent(Collections.<ExpenseReportDTO>emptyList()));
                    }
                });
                client.execute();
                break;
            }
        }
    }

}
