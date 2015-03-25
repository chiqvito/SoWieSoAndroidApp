package pl.chiqvito.sowieso.rest.client;

import android.content.Context;

import java.util.List;

import pl.chiqvito.sowieso.rest.ApiService;
import pl.chiqvito.sowieso.rest.dto.ExpenseReportYearMonthCategoryDTO;

public class ExpenseReportYearMonthCategoryClient extends BaseApiClient<List<ExpenseReportYearMonthCategoryDTO>> {

    private final Integer year;
    private final Integer month;

    public ExpenseReportYearMonthCategoryClient(Context context, String sessionID, Integer year, Integer month) {
        super(context, sessionID);
        this.year = year;
        this.month = month;
    }

    @Override
    protected void executeService(ApiService service) {
        service.reportYearMonthCategory(year, month, this);
    }
}
