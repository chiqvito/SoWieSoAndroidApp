package pl.chiqvito.sowieso.ui.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import pl.chiqvito.sowieso.Constants;
import pl.chiqvito.sowieso.R;
import pl.chiqvito.sowieso.ui.validator.InputValidator;
import pl.chiqvito.sowieso.ui.validator.SpinnerValidator;
import pl.chiqvito.sowieso.ui.validator.TextValidator;
import pl.chiqvito.sowieso.utils.Boast;

public class ExpenseFragment extends BaseFragment {

    private static final String TAG = ExpenseFragment.class.getName();

    private Holder holder;
    private DatePickerDialog datePicker;

    private List<InputValidator> validators;
    private int year;
    private int month;
    private int day;


    public static ExpenseFragment newInstance(FragmentBuilder.FragmentName fn) {
        ExpenseFragment fragment = new ExpenseFragment();
        Bundle args = new Bundle();
        args.putString(Constants.FRAGMENT_NAME, fn.name());
        fragment.setArguments(args);
        return fragment;
    }

    public ExpenseFragment() {
        validators = new ArrayList<InputValidator>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_expense, container, false);

        holder = new Holder();
        holder.txtDate = (EditText) rootView.findViewById(R.id.exp_date);
        holder.txtName = (EditText) rootView.findViewById(R.id.exp_name);
        holder.txtAmount = (EditText) rootView.findViewById(R.id.exp_amount);
        holder.txtDesc = (EditText) rootView.findViewById(R.id.exp_desc);
        holder.spinnerCategory = (Spinner) rootView.findViewById(R.id.exp_category);

        setCurrentDate();
        datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                year = selectedYear;
                month = selectedMonth;
                day = selectedDay;
                // set selected date into textview
                holder.txtDate.setText(dateFormat(year, month, day));
                holder.txtDate.setError(null);
            }
        }, year, month, day);

        addValidators();
        addListeners();

        return rootView;
    }

    @Override
    public void save() {
        super.save();
        Log.d(TAG, "save");
        if (validate()) {
//TODO
        } else {
            Boast.showText(getActivity(), getString(R.string.msg_fill_correct_form), Toast.LENGTH_SHORT);
        }
    }

    private boolean validate() {
        boolean stat = true;
        for (InputValidator w : validators) {
            stat = stat && w.validate();
        }
        return stat;
    }

    private void addValidators() {
        InputValidator nameVali = new TextValidator(holder.txtName) {
            @Override
            public void validate(TextView textView, String text) {
                if (text.length() == 0)
                    textView.setError(getString(R.string.msg_required_name));
            }
        };
        validators.add(nameVali);
        holder.txtName.addTextChangedListener((TextWatcher) nameVali);

        InputValidator amountVali = new TextValidator(holder.txtAmount) {
            @Override
            public void validate(TextView textView, String text) {
                if (text.length() == 0)
                    textView.setError(getString(R.string.msg_required_amount));
            }
        };
        validators.add(amountVali);
        holder.txtAmount.addTextChangedListener((TextWatcher) amountVali);

        InputValidator dateVali = new TextValidator(holder.txtDate) {
            @Override
            public void validate(TextView textView, String text) {
                if (text.length() == 0)
                    textView.setError(getString(R.string.msg_required_date));
                else {
                    if (!text.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$")) {
                        textView.setError(getString(R.string.msg_wrong_date_format));
                    }
                }
            }
        };
        validators.add(dateVali);
        holder.txtDate.addTextChangedListener((TextWatcher) dateVali);

        SpinnerValidator spinnerVali = new SpinnerValidator(holder.spinnerCategory) {

            @Override
            public boolean validate(Spinner spinner) {
                Object obj = spinner.getSelectedItem();
                if (obj == null) {
                    Toast.makeText(getActivity(), getString(R.string.msg_category_not_selected), Toast.LENGTH_SHORT).show();
                    return false;
                }
                return true;
            }
        };
        validators.add(spinnerVali);
    }

    private void addListeners() {
        holder.txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "click on date input text");
                showDatePicekr();
            }
        });
        holder.txtDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showDatePicekr();
                }
            }
        });
    }

    private void showDatePicekr() {
        datePicker.show();
    }

    private void setCurrentDate() {
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
    }

    private String dateFormat(int year, int month, int day) {
        StringBuilder sb = new StringBuilder();
        sb.append(year);
        sb.append("-");
        sb.append(month + 1);
        sb.append("-");
        sb.append(day);
        return sb.toString();
    }

    private static class Holder {
        private EditText txtDate;
        private EditText txtName;
        private EditText txtAmount;
        private EditText txtDesc;
        private Spinner spinnerCategory;
    }

}
