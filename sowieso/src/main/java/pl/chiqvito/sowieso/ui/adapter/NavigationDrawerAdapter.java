package pl.chiqvito.sowieso.ui.adapter;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import pl.chiqvito.sowieso.R;
import pl.chiqvito.sowieso.ui.fragment.FragmentBuilder;
import pl.chiqvito.sowieso.ui.model.NavigationItem;

public class NavigationDrawerAdapter extends BaseExpandableListAdapter {

    private SparseArray<NavigationItem> mainItems;
    private SparseArray<ArrayList<NavigationItem>> subItems;

    public NavigationDrawerAdapter() {
        init();
    }

    private void init() {
        this.mainItems = new SparseArray<NavigationItem>();
        this.subItems = new SparseArray<ArrayList<NavigationItem>>();

        this.mainItems.put(0, new NavigationItem(FragmentBuilder.FragmentName.EXPENSE_LIST));
        ArrayList<NavigationItem> items = new ArrayList<NavigationItem>();
        items.add(new NavigationItem(FragmentBuilder.FragmentName.EXPENSE_ADD));
        items.add(new NavigationItem(FragmentBuilder.FragmentName.EXPENSE_LIST));
        this.subItems.put(0, items);

        this.mainItems.put(1, new NavigationItem(FragmentBuilder.FragmentName.EXPENSE_REPORT));
        items = new ArrayList<NavigationItem>();
        items.add(new NavigationItem(FragmentBuilder.FragmentName.EXPENSE_REPORT_YEAR));
        items.add(new NavigationItem(FragmentBuilder.FragmentName.EXPENSE_REPORT_YEAR_MONTH));
        items.add(new NavigationItem(FragmentBuilder.FragmentName.EXPENSE_REPORT_YEAR_MONTH_CATEGORY));
        this.subItems.put(1, items);
    }

    @Override
    public int getGroupCount() {
        return mainItems.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return subItems.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mainItems.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return subItems.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return mainItems.get(groupPosition).hashCode();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return subItems.get(groupPosition).get(childPosition).hashCode();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        MainHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_navigation_main, parent, false);
            holder = new MainHolder();
            holder.text = (TextView) convertView.findViewById(R.id.nav_main_text);
            convertView.setTag(holder);
        } else {
            holder = (MainHolder) convertView.getTag();
        }

        NavigationItem item = (NavigationItem) getGroup(groupPosition);
        holder.text.setText(FragmentBuilder.title(parent.getContext(), item.getFragmentName()));

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        SubHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_navigation_sub, parent, false);
            holder = new SubHolder();
            holder.text = (TextView) convertView.findViewById(R.id.nav_sub_text);
            convertView.setTag(holder);
        } else {
            holder = (SubHolder) convertView.getTag();
        }

        NavigationItem item = (NavigationItem) getChild(groupPosition, childPosition);
        holder.text.setText(FragmentBuilder.title(parent.getContext(), item.getFragmentName()));

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private static class MainHolder {
        TextView text;
    }

    private static class SubHolder {
        TextView text;
    }

}
