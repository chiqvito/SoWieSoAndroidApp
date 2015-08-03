package pl.chiqvito.sowieso.ui.dialog;

import android.os.Bundle;

import de.greenrobot.event.EventBus;
import pl.chiqvito.sowieso.R;
import pl.chiqvito.sowieso.bus.events.CarConsumptionOperationEvent;
import pl.chiqvito.sowieso.bus.events.Event;
import pl.chiqvito.sowieso.rest.dto.InventoryCarConsumptionDTO;

public class ManageCarConsumptionDialog extends ManageDialog {

    public static ManageCarConsumptionDialog newInstance(InventoryCarConsumptionDTO dto) {
        ManageCarConsumptionDialog dialog = new ManageCarConsumptionDialog();
        Bundle args = new Bundle();
        args.putParcelable("dto", dto);
        dialog.setArguments(args);
        return dialog;
    }

    private InventoryCarConsumptionDTO dto() {
        return getArguments().getParcelable("dto");
    }

    @Override
    protected String title() {
        return getString(R.string.title_dialog_manage_car_consumption);
    }

    @Override
    protected void edit() {
        EventBus.getDefault().post(new CarConsumptionOperationEvent(Event.Operation.EDIT, dto()));
    }

    @Override
    protected void remove() {
        YesNoDialog yesNo = YesNoDialog.newInstance(getString(R.string.title_confirm), getString(R.string.msg_are_you_sure_remove), new YesNoDialog.YesNoCallback() {
            @Override
            public void action() {
                EventBus.getDefault().post(new CarConsumptionOperationEvent(Event.Operation.REMOVE, dto()));
            }

            @Override
            public void cancel() {

            }
        });
        yesNo.show(getFragmentManager(), null);
    }
}
