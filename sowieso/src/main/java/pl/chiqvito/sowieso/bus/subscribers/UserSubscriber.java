package pl.chiqvito.sowieso.bus.subscribers;

import android.content.Context;
import android.util.Log;

import de.greenrobot.event.EventBus;
import pl.chiqvito.sowieso.bus.events.LoginEvent;
import pl.chiqvito.sowieso.bus.events.LoginInfoEvent;
import pl.chiqvito.sowieso.db.service.PropertiesService;
import pl.chiqvito.sowieso.rest.client.BasicOnResultCallback;
import pl.chiqvito.sowieso.rest.client.LoginClient;
import retrofit.RetrofitError;
import retrofit.client.Response;

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

        LoginClient client = new LoginClient(context, event.getCredential());
        client.setOnResultCallback(new BasicOnResultCallback<String>() {
            @Override
            public void onResponseOk(String sid, Response r) {
                propertiesService.saveSessionId(sid);
                EventBus.getDefault().post(new LoginInfoEvent(LoginInfoEvent.LOGIN));
            }

            @Override
            public void onFail(RetrofitError error) {
                super.onFail(error);
                EventBus.getDefault().post(new LoginInfoEvent(LoginInfoEvent.FAIL));
            }
        });
        client.execute();
    }

}
