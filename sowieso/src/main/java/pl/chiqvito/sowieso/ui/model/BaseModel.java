package pl.chiqvito.sowieso.ui.model;

import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseModel {

    public BaseModel() {
    }

    public BaseModel(SparseArray<BaseModel> viewModels) {
        viewModels.put(getViewType(), this);
    }

    protected static View inflate(ViewGroup container, int layoutId) {
        return LayoutInflater.from(container.getContext()).inflate(layoutId, container, false);
    }

    public abstract RecyclerView.ViewHolder onCreateViewHolder(ViewGroup container);

    public void onBindViewHolder(RecyclerView.ViewHolder itemHolder, int position) {
    }

    public abstract int getViewType();

    public abstract Parcelable getParcelable();

    @Override
    public String toString() {
        if (getParcelable() == null)
            return super.toString();
        return getParcelable().toString();
    }

}
