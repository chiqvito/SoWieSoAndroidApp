package pl.chiqvito.sowieso.ui.adapter;

import android.support.v4.app.FragmentManager;

import pl.chiqvito.sowieso.ui.model.ExpenseModel;
import pl.chiqvito.sowieso.ui.model.TitleModel;

public class ExpenseAdapter extends BaseRecyclerViewAdapter {

    public ExpenseAdapter(FragmentManager fm) {
        super(ExpenseAdapter.class.getName());
        new TitleModel(mViewModels);
        new ExpenseModel(mViewModels, fm);
    }
}
