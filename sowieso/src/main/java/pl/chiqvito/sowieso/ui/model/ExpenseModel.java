package pl.chiqvito.sowieso.ui.model;

import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pl.chiqvito.sowieso.R;
import pl.chiqvito.sowieso.db.model.ExpenseEntity;

public class ExpenseModel extends BaseModel {

    private ExpenseEntity expense;

    public ExpenseModel(ExpenseEntity expense) {
        this.expense = expense;
    }

    public ExpenseModel(SparseArray<BaseModel> viewModels) {
        super(viewModels);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup container) {
        return new ViewHolder(inflate(container, R.layout.row_expense));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder itemHolder, int position) {
        ((ViewHolder) itemHolder).bindView(expense);
    }

    @Override
    public int getViewType() {
        return ModelType.EXPENSE;
    }

    @Override
    public Parcelable getParcelable() {
        return null;
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtName;
        private TextView txtAmount;
        private TextView txtDate;
        private TextView txtCategory;
        private TextView txtInfo;

        private ViewHolder(View itemView) {
            super(itemView);
            txtName = (TextView) itemView.findViewById(R.id.txtNameItemExp);
            txtAmount = (TextView) itemView.findViewById(R.id.txtAmountItemExp);
            txtDate = (TextView) itemView.findViewById(R.id.txtDateItemExp);
            txtCategory = (TextView) itemView.findViewById(R.id.txtCategoryItemExp);
            txtInfo = (TextView) itemView.findViewById(R.id.txtInfoItemExp);
        }

        public void bindView(ExpenseEntity expense) {
            txtName.setText(expense.getName());
            txtAmount.setText(expense.getAmount());
            txtDate.setText(expense.getOperationDate());
            txtCategory.setText(expense.getCategory() != null ? expense.getCategory().getName() : "category id: " + expense.getCategoryId());
            txtInfo.setText(expense.getInfo());
        }
    }

}
