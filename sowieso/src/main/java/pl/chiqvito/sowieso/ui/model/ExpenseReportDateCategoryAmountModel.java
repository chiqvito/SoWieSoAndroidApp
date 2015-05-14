package pl.chiqvito.sowieso.ui.model;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pl.chiqvito.sowieso.R;
import pl.chiqvito.sowieso.rest.dto.ExpenseReportYearMonthCategoryDTO;

public class ExpenseReportDateCategoryAmountModel extends BaseModel {

    private ExpenseReportYearMonthCategoryDTO report;

    public ExpenseReportDateCategoryAmountModel(ExpenseReportYearMonthCategoryDTO report) {
        this.report = report;
    }

    public ExpenseReportDateCategoryAmountModel(SparseArray<BaseModel> viewModels) {
        super(viewModels);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup container) {
        return new ViewHolder(inflate(container, R.layout.row_expense_report_date_category_amount));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder itemHolder, int position) {
        ((ViewHolder) itemHolder).bindView(report);
    }

    @Override
    public int getViewType() {
        return ModelType.EXPENSE_REPORT_DATE_CATEGORY_AMOUNT.getValue();
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView date;
        private TextView category;
        private TextView amount;

        private ViewHolder(View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.txt_exp_report_date);
            category = (TextView) itemView.findViewById(R.id.txt_exp_report_category);
            amount = (TextView) itemView.findViewById(R.id.txt_exp_report_amount);
        }

        public void bindView(ExpenseReportYearMonthCategoryDTO expense) {
            date.setText(expense.getDate());
            category.setText(expense.getName());
            amount.setText(expense.getAmount() + " " + expense.getCurrency());
        }
    }
}
