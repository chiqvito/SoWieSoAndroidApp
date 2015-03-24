package pl.chiqvito.sowieso.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pl.chiqvito.sowieso.R;

public abstract class CommonOptionAdapter<T> extends ArrayAdapter<T> {

    private int layoutId;
    private List<T> itemList;

    public CommonOptionAdapter(Context context, int layoutId) {
        super(context, layoutId);
        this.layoutId = layoutId;
        this.itemList = new ArrayList<T>();
    }

    protected abstract String getTitle(Context context, T item);

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View result = convertView;
        ViewHolder holder = null;

        if (result == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            result = inflater.inflate(layoutId, parent, false);
            holder = new ViewHolder();

            holder.text = (TextView) result.findViewById(R.id.text);

            result.setTag(holder);
        } else {
            holder = (ViewHolder) result.getTag();
        }

        holder.text.setText(getTitle(getContext(), getItem(position)));

        return result;

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

    private static class ViewHolder {
        TextView text;
    }

}
