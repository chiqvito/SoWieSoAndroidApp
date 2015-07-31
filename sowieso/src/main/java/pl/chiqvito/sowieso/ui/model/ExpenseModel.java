package pl.chiqvito.sowieso.ui.model;

import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pl.chiqvito.sowieso.R;
import pl.chiqvito.sowieso.db.model.ExpenseEntity;
import pl.chiqvito.sowieso.ui.dialog.ManageExpenseDialog;

public class ExpenseModel extends BaseModel {

    private ExpenseEntity expense;
    private FragmentManager fm;

    public ExpenseModel(ExpenseEntity expense) {
        this.expense = expense;
    }

    public ExpenseModel(SparseArray<BaseModel> viewModels, FragmentManager fm) {
        super(viewModels);
        this.fm = fm;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup container) {
        return new ViewHolder(inflate(container, R.layout.row_expense), fm);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder itemHolder, int position) {
        ((ViewHolder) itemHolder).bindView(expense);
    }

    @Override
    public int getViewType() {
        return ModelType.EXPENSE.getValue();
    }

    private static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final FragmentManager fm;
        private ExpenseEntity expense;

        private TextView txtName;
        private TextView txtAmount;
        private TextView txtDate;
        private TextView txtCategory;
        private TextView txtInfo;

        private ViewHolder(View itemView, FragmentManager fm) {
            super(itemView);
            this.fm = fm;
            itemView.setOnClickListener(this);
            itemView.setClickable(true);
            txtName = (TextView) itemView.findViewById(R.id.txtNameItemExp);
            txtAmount = (TextView) itemView.findViewById(R.id.txtAmountItemExp);
            txtDate = (TextView) itemView.findViewById(R.id.txtDateItemExp);
            txtCategory = (TextView) itemView.findViewById(R.id.txtCategoryItemExp);
            txtInfo = (TextView) itemView.findViewById(R.id.txtInfoItemExp);
        }

        public void bindView(ExpenseEntity expense) {
            this.expense = expense;
            txtName.setText(expense.getName());
            txtAmount.setText(expense.getAmount());
            txtDate.setText(expense.getOperationDate());
            txtCategory.setText(expense.getCategory() != null ? expense.getCategory().getName() : "category id: " + expense.getCategoryId());
            txtInfo.setText(expense.getInfo());
        }

        @Override
        public void onClick(View v) {
            ManageExpenseDialog dialog = ManageExpenseDialog.newInstance(expense);
            dialog.show(fm, null);
        }
    }

}
