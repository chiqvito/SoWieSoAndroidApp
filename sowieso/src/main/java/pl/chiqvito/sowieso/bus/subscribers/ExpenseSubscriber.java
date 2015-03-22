package pl.chiqvito.sowieso.bus.subscribers;

import android.util.Log;

import de.greenrobot.event.EventBus;
import pl.chiqvito.sowieso.bus.events.ExpenseEvent;
import pl.chiqvito.sowieso.bus.events.ExpenseInfoEvent;

public class ExpenseSubscriber {

    private static final String TAG = ExpenseSubscriber.class.getName();

    public void onEventBackgroundThread(ExpenseEvent event) {
        Log.v(TAG, "event:" + event);
        //TODO
        EventBus.getDefault().post(new ExpenseInfoEvent(ExpenseInfoEvent.FAIL));
    }

}
