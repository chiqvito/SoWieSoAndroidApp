package pl.chiqvito.sowieso.ui.adapter;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import pl.chiqvito.sowieso.ui.model.OptionItem;

public class OptionSpinnerAdapter<T> extends BaseArrayAdapter<OptionItem<T>> {

    public OptionSpinnerAdapter(Context context) {
        this(context, new ArrayList<OptionItem<T>>());
    }

    protected OptionSpinnerAdapter(Context context, List<OptionItem<T>> items) {
        super(context, android.R.layout.simple_spinner_item, items);
        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    public void loadData(List<T> data) {
        List<OptionItem<T>> items = new ArrayList<OptionItem<T>>();
        for (T t : data)
            items.add(new OptionItem<T>(t));
        super.load(items);
    }
}
