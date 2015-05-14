package pl.chiqvito.sowieso.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.List;

import de.greenrobot.event.EventBus;
import pl.chiqvito.sowieso.Constants;
import pl.chiqvito.sowieso.R;
import pl.chiqvito.sowieso.bus.events.Event;
import pl.chiqvito.sowieso.ui.adapter.BaseRecyclerViewAdapter;
import pl.chiqvito.sowieso.ui.model.BaseModel;

public abstract class BaseListFragment extends BaseFragment {

    public static final String TAG = BaseListFragment.class.getName();

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressBar mProgressBar;
    private BaseRecyclerViewAdapter mAdapter;
    private int page = Constants.FIRST_PAGE;

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
        mRecyclerView.setOnScrollListener(scrollListener());

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        page = Constants.FIRST_PAGE;
    }

    @Override
    public void refresh() {
        super.refresh();
        page = Constants.FIRST_PAGE;
    }

    protected void onScrollFetchData() {
        if (canLoadMorePages())
            page++;
        Log.i(TAG, "onScrollFetchData page=" + page);
    }

    protected int getPage() {
        return page;
    }

    protected boolean canLoadMorePages() {
        if (page < Constants.FIRST_PAGE) {
            Log.i(TAG, "no more results page:" + page);
            return false;
        }
        return true;
    }

    private RecyclerView.OnScrollListener scrollListener() {
        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int visibleItemCount = mLayoutManager.getChildCount();
                int totalItemCount = mLayoutManager.getItemCount();
                int firstVisibleItem = firstVisibleItem();
                Log.d(TAG, "fi:" + firstVisibleItem + ", ic:" + visibleItemCount + ", tic: " + totalItemCount);
                int loadedItems = firstVisibleItem + visibleItemCount;
                if (loadedItems >= totalItemCount) {
                    onScrollFetchData();
                }
            }
        };
    }

    private int firstVisibleItem() {
        int firstVisibleItem = -1;

        if (mLayoutManager instanceof GridLayoutManager)
            firstVisibleItem = ((GridLayoutManager) mLayoutManager).findFirstVisibleItemPosition();
        if (mLayoutManager instanceof LinearLayoutManager)
            firstVisibleItem = ((LinearLayoutManager) mLayoutManager).findFirstVisibleItemPosition();

        return firstVisibleItem;
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
        load(data, Constants.FIRST_PAGE);
    }

    protected void load(List<BaseModel> data, int page) {
        this.page = page;
        hideProgress();
        if (page == Constants.FIRST_PAGE)
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
