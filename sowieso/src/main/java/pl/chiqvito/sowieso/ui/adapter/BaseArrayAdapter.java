package pl.chiqvito.sowieso.ui.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseArrayAdapter<T> extends ArrayAdapter<T> {

    private List<T> itemList;

    public BaseArrayAdapter(Context context, int resource) {
        this(context, resource, new ArrayList<T>());
    }

    protected BaseArrayAdapter(Context context, int resource, List<T> itemList) {
        super(context, resource);
        this.itemList = itemList;
    }

    @Override
    public void clear() {
        super.clear();
        this.itemList.clear();
        notifyDataSetChanged();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    public List<T> getItemList() {
        return itemList;
    }

    public void load(List<T> data) {
        this.itemList.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public T getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return itemList.get(position).hashCode();
    }

    @Override
    public int getPosition(T item) {
        return itemList.indexOf(item);
    }

    @Override
    public void remove(T comment) {
        itemList.remove(comment);
        notifyDataSetChanged();
    }

    @Override
    public void add(T comment) {
        itemList.add(comment);
        notifyDataSetChanged();
    }

}
