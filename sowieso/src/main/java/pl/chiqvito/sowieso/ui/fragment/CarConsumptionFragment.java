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
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import pl.chiqvito.sowieso.Constants;
import pl.chiqvito.sowieso.R;
import pl.chiqvito.sowieso.rest.dto.InventoryCarConsumptionDTO;
import pl.chiqvito.sowieso.rest.dto.enums.PetrolStationEnum;
import pl.chiqvito.sowieso.ui.adapter.OptionSpinnerAdapter;
import pl.chiqvito.sowieso.ui.validator.InputValidator;
import pl.chiqvito.sowieso.ui.validator.SpinnerValidator;
import pl.chiqvito.sowieso.ui.validator.TextValidator;
import pl.chiqvito.sowieso.utils.Boast;
import pl.chiqvito.sowieso.utils.DateUtil;

public class CarConsumptionFragment extends BaseFragment {

    private static final String TAG = CarConsumptionFragment.class.getName();

    private Holder holder;
    private DatePickerDialog datePicker;
    private List<InputValidator> validators;
    private int year;
    private int month;
    private int day;

    public static CarConsumptionFragment newInstance(FragmentBuilder.FragmentName fn) {
        return newInstance(fn, null);
    }

    public static CarConsumptionFragment newInstance(FragmentBuilder.FragmentName fn, InventoryCarConsumptionDTO dto) {
        CarConsumptionFragment fragment = new CarConsumptionFragment();
        Bundle args = new Bundle();
        if (dto != null)
            args.putParcelable("dto", dto);
        args.putString(Constants.FRAGMENT_NAME, fn.name());
        fragment.setArguments(args);
        return fragment;
    }

    public CarConsumptionFragment() {
        this.validators = new ArrayList<InputValidator>();
    }

    private InventoryCarConsumptionDTO dto() {
        return getArguments().getParcelable("dto");
    }

    @Override
    public void onStart() {
        super.onStart();
//        EventBus.getDefault().post(new CategoryOperationEvent(Event.Operation.GET_ALL, null)); TODO
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_car_consumption, container, false);

        holder = new Holder();
        holder.txtRefuelDate = (EditText) rootView.findViewById(R.id.cc_refuel_date);
        holder.txtRefuelAmount = (EditText) rootView.findViewById(R.id.cc_refuel_amount);
        holder.txtDistance = (EditText) rootView.findViewById(R.id.cc_distance);
        holder.txtPrice = (EditText) rootView.findViewById(R.id.cc_price);
        holder.spinnerCar = (Spinner) rootView.findViewById(R.id.cc_car);
        holder.spinnerPetrolStation = (Spinner) rootView.findViewById(R.id.cc_petrol_station);

        setCurrentDate();
        datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                year = selectedYear;
                month = selectedMonth;
                day = selectedDay;
                // set selected date into textview
                holder.txtRefuelDate.setText(DateUtil.dateFormat(year, month, day));
                holder.txtRefuelDate.setError(null);
            }
        }, year, month, day);

        OptionSpinnerAdapter<PetrolStationEnum> dataAdapter = new OptionSpinnerAdapter<PetrolStationEnum>(getActivity());
        dataAdapter.loadData(Arrays.asList(PetrolStationEnum.values()));
        holder.spinnerPetrolStation.setAdapter(dataAdapter);

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
//            ExpenseEntity exp = collectData(); TODO
//            Log.v(TAG, "save: " + exp);
//            EventBus.getDefault().post(new ExpenseOperationEvent(Event.Operation.SAVE, exp)); ++ TODO
        } else {
            enable();
            Boast.showText(getActivity(), getString(R.string.msg_fill_correct_form), Toast.LENGTH_SHORT);
        }
    }

    private void clear() {
        holder.txtRefuelDate.getText().clear();
        holder.txtRefuelDate.setError(null);
        holder.txtRefuelAmount.getText().clear();
        holder.txtRefuelAmount.setError(null);
        holder.txtDistance.getText().clear();
        holder.txtDistance.setError(null);
        holder.txtPrice.getText().clear();
        holder.txtPrice.setError(null);
    }

    private void enable() {
        holder.txtRefuelDate.setEnabled(true);
        holder.txtRefuelAmount.setEnabled(true);
        holder.txtDistance.setEnabled(true);
        holder.txtPrice.setEnabled(true);
        holder.spinnerCar.setEnabled(true);
        holder.spinnerPetrolStation.setEnabled(true);
    }

    private void disable() {
        holder.txtRefuelDate.setEnabled(false);
        holder.txtRefuelAmount.setEnabled(false);
        holder.txtDistance.setEnabled(false);
        holder.txtPrice.setEnabled(false);
        holder.spinnerCar.setEnabled(false);
        holder.spinnerPetrolStation.setEnabled(false);
    }

    private boolean validate() {
        boolean stat = true;
        for (InputValidator w : validators) {
            stat = stat && w.validate();
        }
        return stat;
    }

    private void addValidators() {
        InputValidator distanceVali = new TextValidator(holder.txtDistance) {
            @Override
            public void validate(TextView textView, String text) {
                if (text.length() == 0)
                    textView.setError(getString(R.string.msg_required_distance));
            }
        };
        validators.add(distanceVali);
        holder.txtDistance.addTextChangedListener((TextWatcher) distanceVali);

        InputValidator priceVali = new TextValidator(holder.txtPrice) {
            @Override
            public void validate(TextView textView, String text) {
                if (text.length() == 0)
                    textView.setError(getString(R.string.msg_required_price));
            }
        };
        validators.add(priceVali);
        holder.txtPrice.addTextChangedListener((TextWatcher) priceVali);

        InputValidator refuelAmountVali = new TextValidator(holder.txtRefuelAmount) {
            @Override
            public void validate(TextView textView, String text) {
                if (text.length() == 0)
                    textView.setError(getString(R.string.msg_required_refuel_amount));
            }
        };
        validators.add(refuelAmountVali);
        holder.txtRefuelAmount.addTextChangedListener((TextWatcher) refuelAmountVali);

        InputValidator dateVali = new TextValidator(holder.txtRefuelDate) {
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
        holder.txtRefuelDate.addTextChangedListener((TextWatcher) dateVali);

        SpinnerValidator spinnerCarVali = new SpinnerValidator(holder.spinnerCar) {
            @Override
            public boolean validate(Spinner spinner) {
                Object obj = spinner.getSelectedItem();
                if (obj == null) {
                    Boast.showText(getActivity(), getString(R.string.msg_car_not_selected), Toast.LENGTH_SHORT);
                    return false;
                }
                return true;
            }
        };
        validators.add(spinnerCarVali);

        SpinnerValidator spinnerPetrolStationVali = new SpinnerValidator(holder.spinnerPetrolStation) {
            @Override
            public boolean validate(Spinner spinner) {
                Object obj = spinner.getSelectedItem();
                if (obj == null) {
                    Boast.showText(getActivity(), getString(R.string.msg_petrol_station_not_selected), Toast.LENGTH_SHORT);
                    return false;
                }
                return true;
            }
        };
        validators.add(spinnerPetrolStationVali);
    }

    private void addListeners() {
        holder.txtRefuelDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "click on date input text");
                showDatePicekr();
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

    private static class Holder {
        private Spinner spinnerCar;
        private Spinner spinnerPetrolStation;
        private EditText txtRefuelDate;
        private EditText txtRefuelAmount;
        private EditText txtDistance;
        private EditText txtPrice;
    }

}
