package pl.chiqvito.sowieso.rest.client;

import pl.chiqvito.sowieso.rest.ApiService;
import retrofit.RestAdapter;

public abstract class BaseApiClient<K> extends BaseClient<K> {

    public BaseApiClient(String sessionID) {
        super(sessionID, false);
    }

    @Override
    protected void executeService(RestAdapter restAdapter) {
        ApiService service = restAdapter.create(ApiService.class);
        executeService(service);
    }

    protected abstract void executeService(ApiService service);
}
