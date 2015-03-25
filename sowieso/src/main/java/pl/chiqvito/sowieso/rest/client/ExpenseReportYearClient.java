package pl.chiqvito.sowieso.rest.client;

import android.content.Context;

import java.util.List;

import pl.chiqvito.sowieso.rest.ApiService;
import pl.chiqvito.sowieso.rest.dto.ExpenseReportYearDTO;

public class ExpenseReportYearClient extends BaseApiClient<List<ExpenseReportYearDTO>> {
    public ExpenseReportYearClient(Context context, String sessionID) {
        super(context, sessionID);
    }

    @Override
    protected void executeService(ApiService service) {
        service.reportYear(this);
    }
}
