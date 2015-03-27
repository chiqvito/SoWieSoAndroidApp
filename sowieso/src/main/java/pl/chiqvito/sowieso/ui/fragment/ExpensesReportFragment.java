package pl.chiqvito.sowieso.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import pl.chiqvito.sowieso.Constants;
import pl.chiqvito.sowieso.R;
import pl.chiqvito.sowieso.bus.events.ExpensesReportEvent;
import pl.chiqvito.sowieso.bus.events.ExpensesReportOperationEvent;
import pl.chiqvito.sowieso.rest.dto.ExpenseReportDTO;
import pl.chiqvito.sowieso.rest.dto.ExpenseReportYearDTO;
import pl.chiqvito.sowieso.rest.dto.ExpenseReportYearMonthCategoryDTO;
import pl.chiqvito.sowieso.rest.dto.ExpenseReportYearMonthDTO;
import pl.chiqvito.sowieso.ui.adapter.ExpenseReportAdapter;
import pl.chiqvito.sowieso.ui.model.BaseModel;
import pl.chiqvito.sowieso.ui.model.ExpenseReportDateAmountModel;
import pl.chiqvito.sowieso.ui.model.ExpenseReportDateCategoryAmountModel;
import pl.chiqvito.sowieso.ui.model.ExpenseReportFilterModel;

public class ExpensesReportFragment extends BaseFragment implements ExpenseReportFilterModel.FilterCallback {

    private static final String TAG = ExpensesReportFragment.class.getName();

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ExpenseReportAdapter mAdapter;

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
        showProgress();
        EventBus.getDefault().post(new ExpensesReportOperationEvent(fragmentName(), year, month));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_expenses, container, false);

        progressBar = (ProgressBar) rootView.findViewById(R.id.progress_bar);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(false);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        filter = new ExpenseReportFilterModel(fragmentName(), this);

        mAdapter = new ExpenseReportAdapter() {
            @Override
            protected void initEmptyList() {
                super.initEmptyList();
                if (FragmentBuilder.FragmentName.EXPENSE_REPORT_YEAR.equals(fragmentName()))
                    return;
                getItemsModel().add(filter);
            }
        };

        mRecyclerView.setAdapter(mAdapter);

        return rootView;
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
        hideProgress();
        mAdapter.clear();
        mAdapter.load(ms);
    }

}
