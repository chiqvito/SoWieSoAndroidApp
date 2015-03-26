package pl.chiqvito.sowieso.ui.fragment;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.app.Fragment;

import pl.chiqvito.sowieso.R;
import pl.chiqvito.sowieso.db.model.ExpenseEntity;

public class FragmentBuilder {

    private FragmentName fn;
    private Parcelable parcelable;

    public enum FragmentName {
        EXPENSE_LIST,
        EXPENSE_ADD,
        EXPENSE_EDIT,
        EXPENSE_REPORT,
        EXPENSE_REPORT_YEAR,
        EXPENSE_REPORT_YEAR_MONTH,
        EXPENSE_REPORT_YEAR_MONTH_CATEGORY,
    }

    public FragmentBuilder(FragmentName fn) {
        this.fn = fn;
    }

    public FragmentBuilder parcelable(Parcelable parcelable) {
        this.parcelable = parcelable;
        return this;
    }

    public static String title(Context ctx, FragmentName fn) {
        switch (fn) {
            case EXPENSE_ADD:
                return ctx.getString(R.string.title_expense_add);
            case EXPENSE_EDIT:
                return ctx.getString(R.string.title_expense_edit);
            case EXPENSE_LIST:
                return ctx.getString(R.string.title_expense_list);
            case EXPENSE_REPORT:
                return ctx.getString(R.string.title_expense_report);
            case EXPENSE_REPORT_YEAR:
                return ctx.getString(R.string.title_expense_report_year);
            case EXPENSE_REPORT_YEAR_MONTH:
                return ctx.getString(R.string.title_expense_report_yearmonth);
            case EXPENSE_REPORT_YEAR_MONTH_CATEGORY:
                return ctx.getString(R.string.title_expense_report_categoryyearmonth);
        }
        throw new IllegalArgumentException("Unknown title for " + fn);
    }

    public Fragment build() {
        switch (fn) {
            case EXPENSE_ADD:
                return ExpenseFragment.newInstance(fn);
            case EXPENSE_EDIT:
                return ExpenseFragment.newInstance(fn, (ExpenseEntity) parcelable);
            case EXPENSE_LIST:
                return ExpensesFragment.newInstance(fn);
            case EXPENSE_REPORT_YEAR:
            case EXPENSE_REPORT_YEAR_MONTH:
            case EXPENSE_REPORT_YEAR_MONTH_CATEGORY:
                return ExpensesReportFragment.newInstance(fn);
        }
        throw new IllegalArgumentException("Unknown fragment for " + fn);
    }

}
