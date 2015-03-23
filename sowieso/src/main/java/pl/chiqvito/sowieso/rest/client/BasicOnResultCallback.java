package pl.chiqvito.sowieso.rest.client;

import pl.chiqvito.sowieso.BuildConfig;
import retrofit.RetrofitError;

public abstract class BasicOnResultCallback<K> implements BaseClient.OnResultCallback<K> {
    @Override
    public void onFail(RetrofitError error) {
        if (BuildConfig.DEBUG) {
            error.printStackTrace();
        }
    }
}
