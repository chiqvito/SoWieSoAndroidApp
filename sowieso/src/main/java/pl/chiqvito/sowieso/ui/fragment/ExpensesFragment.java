package pl.chiqvito.sowieso.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import pl.chiqvito.sowieso.Constants;
import pl.chiqvito.sowieso.R;
import pl.chiqvito.sowieso.bus.events.ExpenseOperationEvent;
import pl.chiqvito.sowieso.bus.events.ExpensesEvent;
import pl.chiqvito.sowieso.db.model.ExpenseEntity;
import pl.chiqvito.sowieso.ui.adapter.ExpenseAdapter;
import pl.chiqvito.sowieso.ui.model.BaseModel;
import pl.chiqvito.sowieso.ui.model.ExpenseModel;
import pl.chiqvito.sowieso.ui.model.TitleModel;

public class ExpensesFragment extends BaseFragment {

    public static final String TAG = ExpenseFragment.class.getName();

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ExpenseAdapter mAdapter;

    public static ExpensesFragment newInstance(FragmentBuilder.FragmentName fn) {
        ExpensesFragment fragment = new ExpensesFragment();
        Bundle args = new Bundle();
        args.putString(Constants.FRAGMENT_NAME, fn.name());
        fragment.setArguments(args);
        return fragment;
    }

    public ExpensesFragment() {
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().post(new ExpenseOperationEvent(ExpenseOperationEvent.GET_ALL_WITH_CATEGORY, null));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_expenses, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(false);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new ExpenseAdapter() {
            @Override
            protected void initEmptyList() {
                super.initEmptyList();
                getItemsModel().add(new TitleModel(getString(R.string.title_expense_list)));
            }
        };

        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    public void onEventMainThread(ExpensesEvent event) {
        Log.v(TAG, "event:" + event);
        List<BaseModel> ms = new ArrayList<BaseModel>();
        List<ExpenseEntity> expenseEntities = event.getExpenses();
        for (ExpenseEntity ee : expenseEntities) {
            ms.add(new ExpenseModel(ee));
        }
        mAdapter.clear();
        mAdapter.load(ms);
    }

    @Override
    public void refresh() {
        super.refresh();
        EventBus.getDefault().post(new ExpenseOperationEvent(ExpenseOperationEvent.GET_ALL_WITH_CATEGORY, null));
    }
}
