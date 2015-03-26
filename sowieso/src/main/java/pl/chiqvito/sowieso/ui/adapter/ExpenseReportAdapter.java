package pl.chiqvito.sowieso.ui.adapter;

import pl.chiqvito.sowieso.ui.model.ExpenseReportDateAmountModel;
import pl.chiqvito.sowieso.ui.model.ExpenseReportDateCategoryAmountModel;
import pl.chiqvito.sowieso.ui.model.ExpenseReportFilterModel;

public class ExpenseReportAdapter extends BaseRecyclerViewAdapter {

    public ExpenseReportAdapter() {
        super(ExpenseReportAdapter.class.getName());
        new ExpenseReportFilterModel(mViewModels);
        new ExpenseReportDateAmountModel(mViewModels);
        new ExpenseReportDateCategoryAmountModel(mViewModels);
    }
}
