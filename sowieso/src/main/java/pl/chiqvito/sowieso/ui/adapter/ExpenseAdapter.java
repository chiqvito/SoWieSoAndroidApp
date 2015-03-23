package pl.chiqvito.sowieso.ui.adapter;

import pl.chiqvito.sowieso.ui.model.ExpenseModel;
import pl.chiqvito.sowieso.ui.model.TitleModel;

public class ExpenseAdapter extends BaseRecyclerViewAdapter {

    public ExpenseAdapter() {
        super(ExpenseAdapter.class.getName());
        new TitleModel(mViewModels);
        new ExpenseModel(mViewModels);
    }
}
