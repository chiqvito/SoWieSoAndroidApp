package pl.chiqvito.sowieso.ui.dialog;

import android.os.Bundle;

import de.greenrobot.event.EventBus;
import pl.chiqvito.sowieso.R;
import pl.chiqvito.sowieso.bus.events.Event;
import pl.chiqvito.sowieso.bus.events.ExpenseOperationEvent;
import pl.chiqvito.sowieso.db.model.ExpenseEntity;

public class ManageExpenseDialog extends ManageDialog {

    public static ManageExpenseDialog newInstance(ExpenseEntity expense) {
        ManageExpenseDialog dialog = new ManageExpenseDialog();
        Bundle args = new Bundle();
        args.putParcelable("expense", expense);
        dialog.setArguments(args);
        return dialog;
    }

    private ExpenseEntity expense() {
        return getArguments().getParcelable("expense");
    }

    @Override
    protected String title() {
        return getString(R.string.title_dialog_manage_expense);
    }

    @Override
    protected void remove() {
        YesNoDialog yesNo = YesNoDialog.newInstance(getString(R.string.title_confirm), getString(R.string.msg_are_you_sure_remove), new YesNoDialog.YesNoCallback() {
            @Override
            public void action() {
                EventBus.getDefault().post(new ExpenseOperationEvent(Event.Operation.REMOVE, expense()));
            }

            @Override
            public void cancel() {

            }
        });
        yesNo.show(getFragmentManager(), null);
    }

    @Override
    protected void edit() {
        EventBus.getDefault().post(new ExpenseOperationEvent(Event.Operation.EDIT, expense()));
    }
}
