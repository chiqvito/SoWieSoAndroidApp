package pl.chiqvito.sowieso.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import pl.chiqvito.sowieso.R;
import pl.chiqvito.sowieso.bus.events.ExpenseOperationEvent;
import pl.chiqvito.sowieso.db.model.ExpenseEntity;
import pl.chiqvito.sowieso.ui.adapter.CommonOptionAdapter;
import pl.chiqvito.sowieso.ui.model.OptionItem;
import pl.chiqvito.sowieso.ui.model.enums.ManageType;

public class ExpenseDialog extends DialogFragment {

    private static final String TAG = ExpenseDialog.class.getName();

    public static ExpenseDialog newInstance(ExpenseEntity expense) {
        ExpenseDialog dialog = new ExpenseDialog();
        Bundle args = new Bundle();
        args.putParcelable("expense", expense);
        dialog.setArguments(args);
        return dialog;
    }

    private ExpenseEntity expense() {
        return getArguments().getParcelable("expense");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final CommonOptionAdapter<OptionItem<ManageType>> adapter = new CommonOptionAdapter<OptionItem<ManageType>>(getActivity(), R.layout.row_layout_manage) {
            @Override
            protected String getTitle(Context context, OptionItem<ManageType> item) {
                return context.getString(item.getType().resIdString());
            }
        };
        fill(adapter);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        builder.setTitle(getString(R.string.title_dialog_manage_expense));
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int position) {
                Log.v(TAG, "position:" + position);

                Log.d(TAG, "=============== selected position: " + position);
                OptionItem<ManageType> item = adapter.getItem(position);
                Log.d(TAG, "=============== selected position: " + position + ", " + item.getType());

                switch (item.getType()) {
                    case EDIT: {
                        dialog.dismiss();
                        EventBus.getDefault().post(new ExpenseOperationEvent(ExpenseOperationEvent.EDIT, expense()));
                        break;
                    }
                    case REMOVE: {
                        dialog.dismiss();
                        EventBus.getDefault().post(new ExpenseOperationEvent(ExpenseOperationEvent.REMOVE, expense()));
                        break;
                    }
                }
            }
        });
        Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }

    private void fill(CommonOptionAdapter adapter) {
        List<OptionItem<ManageType>> list = new ArrayList<OptionItem<ManageType>>();
        for (ManageType option : ManageType.values()) {
            try {
                OptionItem<ManageType> pf = new OptionItem<ManageType>(option);
                list.add(pf);
            } catch (IllegalArgumentException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        }
        adapter.load(list);
    }

}
