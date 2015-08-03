package pl.chiqvito.sowieso.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import pl.chiqvito.sowieso.R;

public class YesNoDialog extends DialogFragment {

    private final String TAG = getClass().getName();

    private YesNoCallback callback;

    public interface YesNoCallback {
        void action();

        void cancel();
    }

    public static YesNoDialog newInstance(String title, String msg, YesNoCallback callback) {
        YesNoDialog dialog = new YesNoDialog();
        dialog.setCallback(callback);
        Bundle args = new Bundle();
        args.putString("msg", msg);
        args.putString("title", title);
        dialog.setArguments(args);
        return dialog;
    }

    private void setCallback(YesNoCallback callback) {
        this.callback = callback;
    }

    private String actionNo() {
        return getString(R.string.action_no);
    }

    private String actionYes() {
        return getString(R.string.action_yes);
    }

    private String msg() {
        return getArguments().getString("msg");
    }

    private String title() {
        return getArguments().getString("title");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = title();
        String msg = msg();
        String actionNo = actionNo();
        String actionYes = actionYes();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);

        if (title != null)
            builder.setTitle(title);
        if (msg != null)
            builder.setMessage(msg);

        builder.setNegativeButton(actionNo, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                callback.cancel();
            }
        });

        builder.setPositiveButton(actionYes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                callback.action();
            }
        });
        Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }

}
