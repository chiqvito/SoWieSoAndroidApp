package pl.chiqvito.sowieso.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Calendar;
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

public class ExpensesReportFragment extends BaseFragment {

    private static final String TAG = ExpensesReportFragment.class.getName();

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ExpenseReportAdapter mAdapter;

    private int year;
    private int month;

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
        EventBus.getDefault().post(new ExpensesReportOperationEvent(fragmentName(), year, month));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_expenses, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(false);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new ExpenseReportAdapter();

        mRecyclerView.setAdapter(mAdapter);

        setCurrentDateOnView();

        return rootView;
    }

    private void setCurrentDateOnView() {
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
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
        mAdapter.clear();
        mAdapter.load(ms);
    }

}
