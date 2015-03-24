package pl.chiqvito.sowieso.rest.client;

import android.content.Context;

import pl.chiqvito.sowieso.rest.ApiService;
import retrofit.RestAdapter;

public abstract class BaseApiClient<K> extends BaseClient<K> {

    public BaseApiClient(Context context, String sessionID) {
        super(context, sessionID, false);
    }

    @Override
    protected void executeService(RestAdapter restAdapter) {
        ApiService service = restAdapter.create(ApiService.class);
        executeService(service);
    }

    protected abstract void executeService(ApiService service);
}
