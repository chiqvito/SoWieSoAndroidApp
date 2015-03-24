package pl.chiqvito.sowieso.bus.subscribers;

import android.content.Context;
import android.util.Log;

import de.greenrobot.event.EventBus;
import pl.chiqvito.sowieso.bus.events.LoginEvent;
import pl.chiqvito.sowieso.bus.events.LoginInfoEvent;
import pl.chiqvito.sowieso.db.service.PropertiesService;

public class UserSubscriber {

    private static final String TAG = UserSubscriber.class.getName();

    private final Context context;
    private final PropertiesService propertiesService;

    public UserSubscriber(Context context, PropertiesService propertiesService) {
        this.context = context;
        this.propertiesService = propertiesService;
    }

    public void onEventAsync(LoginEvent event) {
        Log.v(TAG, "event:" + event);
        //TODO
        EventBus.getDefault().post(new LoginInfoEvent(LoginInfoEvent.FAIL));
    }

}
