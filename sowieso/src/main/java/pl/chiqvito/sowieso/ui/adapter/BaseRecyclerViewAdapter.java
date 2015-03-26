package pl.chiqvito.sowieso.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.util.SparseArray;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import pl.chiqvito.sowieso.ui.model.BaseModel;

public abstract class BaseRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected final String TAG;

    protected SparseArray<BaseModel> mViewModels;
    private List<BaseModel> mItems;

    protected BaseRecyclerViewAdapter(String TAG) {
        this.TAG = TAG;
        this.mItems = new ArrayList<BaseModel>();
        this.mViewModels = new SparseArray<BaseModel>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i(TAG, "view type: " + viewType + " in " + mViewModels);
        return mViewModels.get(viewType).onCreateViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d(TAG, "Element " + position + " set.");
        mItems.get(position).onBindViewHolder(holder, position);
    }

    @Override
    public int getItemViewType(int position) {
        return mItems.get(position).getViewType();
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void load(List<BaseModel> data) {
        if (mItems.isEmpty()) {
            initEmptyList();
        }
        this.mItems.addAll(data);
        notifyDataSetChanged();
    }

    protected void initEmptyList() {
    }

    public List<BaseModel> getItemsModel() {
        return mItems;
    }

    public void clear() {
        getItemsModel().clear();
        notifyDataSetChanged();
    }

}
