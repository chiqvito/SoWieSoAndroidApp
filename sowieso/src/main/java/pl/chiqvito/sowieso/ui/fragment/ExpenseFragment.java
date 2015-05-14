package pl.chiqvito.sowieso.ui.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.greenrobot.event.EventBus;
import pl.chiqvito.sowieso.Constants;
import pl.chiqvito.sowieso.R;
import pl.chiqvito.sowieso.bus.events.CategoriesEvent;
import pl.chiqvito.sowieso.bus.events.CategoryOperationEvent;
import pl.chiqvito.sowieso.bus.events.Event;
import pl.chiqvito.sowieso.bus.events.ExpenseInfoEvent;
import pl.chiqvito.sowieso.bus.events.ExpenseOperationEvent;
import pl.chiqvito.sowieso.bus.events.SwitchFragmentEvent;
import pl.chiqvito.sowieso.db.model.CategoryEntity;
import pl.chiqvito.sowieso.db.model.ExpenseEntity;
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
        return newInstance(fn, null);
    }

    public static ExpenseFragment newInstance(FragmentBuilder.FragmentName fn, ExpenseEntity expense) {
        ExpenseFragment fragment = new ExpenseFragment();
        Bundle args = new Bundle();
        if (expense != null)
            args.putParcelable("expense", expense);
        args.putString(Constants.FRAGMENT_NAME, fn.name());
        fragment.setArguments(args);
        return fragment;
    }

    private ExpenseEntity expense() {
        return getArguments().getParcelable("expense");
    }

    public ExpenseFragment() {
        validators = new ArrayList<InputValidator>();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().post(new CategoryOperationEvent(Event.Operation.GET_ALL, null));
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
        disable();
        if (validate()) {
            ExpenseEntity exp = collectData();
            Log.v(TAG, "save: " + exp);
            EventBus.getDefault().post(new ExpenseOperationEvent(Event.Operation.SAVE, exp));
        } else {
            enable();
            Boast.showText(getActivity(), getString(R.string.msg_fill_correct_form), Toast.LENGTH_SHORT);
        }
    }

    public void onEventMainThread(ExpenseInfoEvent event) {
        Log.v(TAG, "event:" + event);
        enable();
        switch (event.getStatus()) {
            case FAIL: {
                Boast.showText(getActivity(), getString(R.string.msg_data_not_saved), Toast.LENGTH_SHORT);
                break;
            }
            case SAVE: {
                clear();
                EventBus.getDefault().post(new SwitchFragmentEvent(FragmentBuilder.FragmentName.EXPENSE_LIST));
                Toast.makeText(getActivity(), getString(R.string.msg_data_saved), Toast.LENGTH_SHORT);
                break;
            }
        }
    }

    public void onEventMainThread(CategoriesEvent event) {
        Log.v(TAG, "event:" + event);
        ArrayAdapter<CategoryEntity> dataAdapter = new ArrayAdapter<CategoryEntity>(getActivity(), android.R.layout.simple_spinner_item, event.getCategories());
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        int position = 0;
        for (int i = 0; i < dataAdapter.getCount(); i++) {
            CategoryEntity cat = dataAdapter.getItem(i);
            if (cat != null && cat.getIsSelected()) {
                position = i;
                break;
            }
        }
        holder.spinnerCategory.setAdapter(dataAdapter);
        holder.spinnerCategory.setSelection(position);

        ExpenseEntity expense = expense();
        if (expense != null) {
            position = dataAdapter.getPosition(expense.getCategory());
            holder.spinnerCategory.setSelection(position);
            holder.txtAmount.setText(expense.getAmount());
            holder.txtDate.setText(expense.getOperationDate());
            holder.txtDesc.setText(expense.getInfo());
            holder.txtName.setText(expense.getName());
        }
    }

    private void clear() {
        holder.txtAmount.getText().clear();
        holder.txtAmount.setError(null);
        holder.txtDate.getText().clear();
        holder.txtDate.setError(null);
        holder.txtDesc.getText().clear();
        holder.txtDesc.setError(null);
        holder.txtName.getText().clear();
        holder.txtName.setError(null);
    }

    private void enable() {
        holder.txtAmount.setEnabled(true);
        holder.txtDate.setEnabled(true);
        holder.txtDesc.setEnabled(true);
        holder.txtName.setEnabled(true);
        holder.spinnerCategory.setEnabled(true);
    }

    private void disable() {
        holder.txtAmount.setEnabled(false);
        holder.txtDate.setEnabled(false);
        holder.txtDesc.setEnabled(false);
        holder.txtName.setEnabled(false);
        holder.spinnerCategory.setEnabled(false);
    }

    private ExpenseEntity collectData() {
        ExpenseEntity exp = new ExpenseEntity();
        ExpenseEntity expense = expense();
        if (expense != null) {
            exp.setId(expense.getId());
        }
        CategoryEntity cat = (CategoryEntity) holder.spinnerCategory.getSelectedItem();
        if (cat != null) {
            Long catId = cat.getId();
            exp.setCategoryId(catId);
        } else {
            exp.setCategoryId(-1L);
        }
        exp.setAmount(holder.txtAmount.getText().toString());
        exp.setInfo(holder.txtDesc.getText().toString());
        exp.setName(holder.txtName.getText().toString());
        exp.setOperationDate(holder.txtDate.getText().toString());
        return exp;
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
//        holder.txtDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
//                    showDatePicekr();
//                }
//            }
//        });
        holder.spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

                @SuppressWarnings("unchecked")
                ArrayAdapter<CategoryEntity> myAdap = (ArrayAdapter<CategoryEntity>) holder.spinnerCategory.getAdapter();
                if (myAdap != null) {
                    CategoryEntity cat = myAdap.getItem(position);
                    if (cat != null) {
                        EventBus.getDefault().post(new CategoryOperationEvent(Event.Operation.SELECT, cat));
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
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
