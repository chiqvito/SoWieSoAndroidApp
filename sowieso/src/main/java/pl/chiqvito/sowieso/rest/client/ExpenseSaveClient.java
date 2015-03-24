package pl.chiqvito.sowieso.rest.client;

import android.content.Context;

import java.util.List;

import pl.chiqvito.sowieso.rest.ApiService;
import pl.chiqvito.sowieso.rest.dto.ExpenseDTO;

public class ExpenseSaveClient extends BaseApiClient<Boolean> {

    private final List<ExpenseDTO> expenses;

    public ExpenseSaveClient(Context context, String sessionID, List<ExpenseDTO> expenses) {
        super(context, sessionID);
        this.expenses = expenses;
    }

    @Override
    protected void executeService(ApiService service) {
        service.saveExpenses(expenses, this);
    }

}