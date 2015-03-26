package pl.chiqvito.sowieso.ui.model;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pl.chiqvito.sowieso.R;
import pl.chiqvito.sowieso.rest.dto.ExpenseReportDTO;

public class ExpenseReportDateAmountModel extends BaseModel {

    private ExpenseReportDTO report;

    public ExpenseReportDateAmountModel(ExpenseReportDTO report) {
        this.report = report;
    }

    public ExpenseReportDateAmountModel(SparseArray<BaseModel> viewModels) {
        super(viewModels);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup container) {
        return new ViewHolder(inflate(container, R.layout.row_expense_report_date_amount));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder itemHolder, int position) {
        ((ViewHolder) itemHolder).bindView(report);
    }

    @Override
    public int getViewType() {
        return ModelType.EXPENSE_REPORT_DATE_AMOUNT;
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView date;
        private TextView amount;

        private ViewHolder(View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.txt_exp_report_date);
            amount = (TextView) itemView.findViewById(R.id.txt_exp_report_amount);
        }

        public void bindView(ExpenseReportDTO expense) {
            date.setText(expense.getDate());
            amount.setText(expense.getAmount() + " " + expense.getCurrency());
        }
    }
}
