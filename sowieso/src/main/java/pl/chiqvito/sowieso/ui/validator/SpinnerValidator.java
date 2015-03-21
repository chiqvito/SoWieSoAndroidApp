package pl.chiqvito.sowieso.ui.validator;

import android.widget.Spinner;

public abstract class SpinnerValidator implements InputValidator {

    private Spinner spinner;

    public SpinnerValidator(Spinner spinner) {
        this.spinner = spinner;
    }

    public abstract boolean validate(Spinner spinner);

    @Override
    public boolean validate() {
        spinner.requestFocus();
        return validate(spinner);
    }

    public Spinner getSpinner() {
        return spinner;
    }
    
}
