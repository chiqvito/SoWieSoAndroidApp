package pl.chiqvito.sowieso.rest.client;

import android.content.Context;

import java.util.List;

import pl.chiqvito.sowieso.rest.ApiService;
import pl.chiqvito.sowieso.rest.dto.ExpenseReportYearMonthDTO;

public class ExpenseReportYearMonthClient extends BaseApiClient<List<ExpenseReportYearMonthDTO>> {

    private final Integer year;

    public ExpenseReportYearMonthClient(Context context, String sessionID, Integer year) {
        super(context, sessionID);
        this.year = year;
    }

    @Override
    protected void executeService(ApiService service) {
        service.reportYearMonth(year, this);
    }
}
