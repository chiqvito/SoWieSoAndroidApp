package pl.chiqvito.sowieso.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import pl.chiqvito.sowieso.Constants;
import pl.chiqvito.sowieso.bus.events.ExpensesReportEvent;
import pl.chiqvito.sowieso.bus.events.ExpensesReportOperationEvent;
import pl.chiqvito.sowieso.rest.dto.ExpenseReportDTO;
import pl.chiqvito.sowieso.rest.dto.ExpenseReportYearDTO;
import pl.chiqvito.sowieso.rest.dto.ExpenseReportYearMonthCategoryDTO;
import pl.chiqvito.sowieso.rest.dto.ExpenseReportYearMonthDTO;
import pl.chiqvito.sowieso.ui.adapter.BaseRecyclerViewAdapter;
import pl.chiqvito.sowieso.ui.adapter.ExpenseReportAdapter;
import pl.chiqvito.sowieso.ui.model.BaseModel;
import pl.chiqvito.sowieso.ui.model.ExpenseReportDateAmountModel;
import pl.chiqvito.sowieso.ui.model.ExpenseReportDateCategoryAmountModel;
import pl.chiqvito.sowieso.ui.model.ExpenseReportFilterModel;

public class ExpensesReportFragment extends BaseListFragment implements ExpenseReportFilterModel.FilterCallback {

    private static final String TAG = ExpensesReportFragment.class.getName();

    private ExpenseReportFilterModel filter;

    public static ExpensesReportFragment newInstance(FragmentBuilder.FragmentName fn) {
        ExpensesReportFragment fragment = new ExpensesReportFragment();
        Bundle args = new Bundle();
        args.putString(Constants.FRAGMENT_NAME, fn.name());
        fragment.setArguments(args);
        return fragment;
    }

    public ExpensesReportFragment() {
    }

    @Override
    public void onStart() {
        super.onStart();
        load(filter.getYear(), filter.getMonth());
    }

    @Override
    public void filter(int year, int month) {
        load(year, month);
    }

    @Override
    public void refresh() {
        super.refresh();
        load(filter.getYear(), filter.getMonth());
    }

    public void load(int year, int month) {
        ++month;
        fetchData(new ExpensesReportOperationEvent(fragmentName(), year, month));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        filter = new ExpenseReportFilterModel(fragmentName(), this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected BaseRecyclerViewAdapter adapter() {
        return new ExpenseReportAdapter() {
            @Override
            protected void initEmptyList() {
                super.initEmptyList();
                if (FragmentBuilder.FragmentName.EXPENSE_REPORT_YEAR.equals(fragmentName()))
                    return;
                getItemsModel().add(filter);
            }
        };
    }

    public void onEventMainThread(ExpensesReportEvent event) {
        Log.v(TAG, "event:" + event);
        List<BaseModel> ms = new ArrayList<BaseModel>();
        List<? extends ExpenseReportDTO> expenses = event.getReports();
        for (ExpenseReportDTO er : expenses) {
            if (er instanceof ExpenseReportYearMonthCategoryDTO) {
                ms.add(new ExpenseReportDateCategoryAmountModel((ExpenseReportYearMonthCategoryDTO) er));
                continue;
            }
            if (er instanceof ExpenseReportYearMonthDTO) {
                ms.add(new ExpenseReportDateAmountModel(er));
                continue;
            }
            if (er instanceof ExpenseReportYearDTO) {
                ms.add(new ExpenseReportDateAmountModel(er));
                continue;
            }
        }
        load(ms);
    }

}
