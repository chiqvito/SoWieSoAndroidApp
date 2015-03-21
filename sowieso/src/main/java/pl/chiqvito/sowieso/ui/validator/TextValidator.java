package pl.chiqvito.sowieso.ui.validator;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

public abstract class TextValidator implements TextWatcher, InputValidator {

    private TextView textView;

    public TextValidator(TextView textView) {
        this.textView = textView;
    }

    public abstract void validate(TextView textView, String text);

    public TextView getTextView() {
        return textView;
    }

    @Override
    public void afterTextChanged(Editable s) {
        String text = textView.getText().toString();
        textView.requestFocus();
        validate(textView, text);
    }

    @Override
    public boolean validate() {
        afterTextChanged(null);
        return getTextView().getError() == null || getTextView().getError().toString().isEmpty();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

}
