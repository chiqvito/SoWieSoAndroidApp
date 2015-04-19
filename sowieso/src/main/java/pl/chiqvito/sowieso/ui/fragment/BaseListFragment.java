package pl.chiqvito.sowieso.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.List;

import de.greenrobot.event.EventBus;
import pl.chiqvito.sowieso.R;
import pl.chiqvito.sowieso.bus.events.Event;
import pl.chiqvito.sowieso.ui.adapter.BaseRecyclerViewAdapter;
import pl.chiqvito.sowieso.ui.model.BaseModel;

public abstract class BaseListFragment extends BaseFragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressBar mProgressBar;
    private BaseRecyclerViewAdapter mAdapter;

    protected abstract BaseRecyclerViewAdapter adapter();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_recycler, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mProgressBar = (ProgressBar) rootView.findViewById(R.id.progress_bar);
        mRecyclerView.setHasFixedSize(false);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = adapter();

        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    protected void fetchData(final Event event) {
        showProgress();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                EventBus.getDefault().post(event);
            }
        }, 500);
    }

    protected void load(List<BaseModel> data) {
        hideProgress();
        mAdapter.clear();
        mAdapter.load(data);
    }

    private void showProgress() {
        if (mProgressBar != null)
            mProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        if (mProgressBar != null)
            mProgressBar.setVisibility(View.GONE);
    }

}
