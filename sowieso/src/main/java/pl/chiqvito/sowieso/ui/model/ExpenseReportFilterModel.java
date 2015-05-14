package pl.chiqvito.sowieso.ui.model;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Calendar;

import pl.chiqvito.sowieso.R;
import pl.chiqvito.sowieso.ui.fragment.FragmentBuilder;


public class ExpenseReportFilterModel extends BaseModel {

    private FragmentBuilder.FragmentName fn;
    private FilterCallback callback;

    public ExpenseReportFilterModel(FragmentBuilder.FragmentName fn, FilterCallback callback) {
        this.fn = fn;
        this.callback = callback;
    }

    public ExpenseReportFilterModel(SparseArray<BaseModel> viewModels) {
        super(viewModels);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup container) {
        return new ViewHolder(inflate(container, R.layout.row_expense_report_filter));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder itemHolder, int position) {
        ((ViewHolder) itemHolder).bindView(fn, callback);
    }

    @Override
    public int getViewType() {
        return ModelType.EXPENSE_REPORT_FILTER.getValue();
    }

    public int getYear() {
        return ViewHolder.year;
    }

    public int getMonth() {
        return ViewHolder.month;
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {

        private FilterCallback callback;
        private static int year = Calendar.getInstance().get(Calendar.YEAR);
        private static int month = Calendar.getInstance().get(Calendar.MONTH);

        private Button date;

        private ViewHolder(final View itemView) {
            super(itemView);
            Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);

            date = (Button) itemView.findViewById(R.id.edit_txt_date);
            date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatePickerDialog d = customDatePicker(itemView.getContext());
                    d.show();
                }
            });
        }

        public void bindView(FragmentBuilder.FragmentName fn, FilterCallback callback) {
            this.callback = callback;
            if (FragmentBuilder.FragmentName.EXPENSE_REPORT_YEAR_MONTH.equals(fn)) {
                date.setText(String.valueOf(year));
            } else {
                date.setText(formatDate(year, month));
            }
        }

        private String formatDate(int year, int month) {
            return year + "-" + ++month;
        }

        private DatePickerDialog customDatePicker(Context context) {
            DatePickerDialog dpd = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                // when dialog box is closed, below method will be called.
                public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                    year = selectedYear;
                    month = selectedMonth;
                    // set selected date into textview
                    date.setText(formatDate(selectedYear, selectedMonth));
                    date.setError(null);
                    callback.filter(year, month);
                }
            }, year, month, 1);
            return dpd;
        }
    }

    public static interface FilterCallback {
        void filter(int year, int month);
    }

}
