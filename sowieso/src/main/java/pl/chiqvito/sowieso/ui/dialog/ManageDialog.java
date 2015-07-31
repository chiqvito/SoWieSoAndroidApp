package pl.chiqvito.sowieso.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;

import java.util.Arrays;

import pl.chiqvito.sowieso.ui.adapter.OptionManageAdapter;
import pl.chiqvito.sowieso.ui.model.OptionItem;
import pl.chiqvito.sowieso.ui.model.enums.ManageType;

public abstract class ManageDialog extends DialogFragment {

    private final String TAG = getClass().getName();

    protected abstract String title();

    protected abstract void edit();

    protected abstract void remove();

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final OptionManageAdapter<ManageType> adapter = new OptionManageAdapter<ManageType>(getActivity()) {
            @Override
            protected String getTitle(Context context, OptionItem<ManageType> item) {
                return context.getString(item.getType().resIdString());
            }
        };
        adapter.loadData(Arrays.asList(ManageType.values()));
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        builder.setTitle(title());
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int position) {
                Log.v(TAG, "position:" + position);

                Log.d(TAG, "=============== selected position: " + position);
                OptionItem<ManageType> item = adapter.getItem(position);
                Log.d(TAG, "=============== selected position: " + position + ", " + item.getType());

                switch (item.getType()) {
                    case EDIT: {
                        dialog.dismiss();
                        edit();
                        break;
                    }
                    case REMOVE: {
                        dialog.dismiss();
                        remove();
                        break;
                    }
                }
            }
        });
        Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }

}
