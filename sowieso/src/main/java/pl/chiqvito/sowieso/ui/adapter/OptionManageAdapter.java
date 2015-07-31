package pl.chiqvito.sowieso.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pl.chiqvito.sowieso.R;
import pl.chiqvito.sowieso.ui.model.OptionItem;

public abstract class OptionManageAdapter<T> extends BaseArrayAdapter<OptionItem<T>> {

    public OptionManageAdapter(Context context) {
        this(context, new ArrayList<OptionItem<T>>());
    }

    protected OptionManageAdapter(Context context, List<OptionItem<T>> items) {
        super(context, R.layout.row_layout_manage, items);
    }

    protected abstract String getTitle(Context context, OptionItem<T> item);

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View result = convertView;
        ViewHolder holder = null;

        if (result == null) {
            result = super.getView(position, convertView, parent);
            holder = new ViewHolder();

            holder.text = (TextView) result.findViewById(R.id.text);

            result.setTag(holder);
        } else {
            holder = (ViewHolder) result.getTag();
        }

        holder.text.setText(getTitle(getContext(), getItem(position)));

        return result;

    }

    public void loadData(List<T> data) {
        List<OptionItem<T>> items = new ArrayList<OptionItem<T>>();
        for (T t : data)
            items.add(new OptionItem<T>(t));
        super.load(items);
    }

    private static class ViewHolder {
        TextView text;
    }

}
