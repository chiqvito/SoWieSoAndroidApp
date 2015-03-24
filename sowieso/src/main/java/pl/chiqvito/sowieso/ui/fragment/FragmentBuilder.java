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
        }
        throw new IllegalArgumentException("Unknown fragment for " + fn);
    }

}
