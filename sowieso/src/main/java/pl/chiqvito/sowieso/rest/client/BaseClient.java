package pl.chiqvito.sowieso.rest.client;

import android.os.Handler;
import android.util.Log;

import pl.chiqvito.sowieso.Constants;
import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

public abstract class BaseClient<K> implements Callback<K>, RequestInterceptor, RestAdapter.Log {

    private String sessionID;

    private OnResultCallback<K> onResultCallback;
    protected Handler handler;

    public BaseClient(String sessionID, boolean runInThread) {
        this.sessionID = sessionID;
        if (runInThread)
            handler = new Handler();
    }

    public void execute() {

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setClient(new OkClient())
                .setEndpoint(Constants.API_URL)
                .setRequestInterceptor(this)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setLog(this)
                .build();

        executeService(restAdapter);
    }

    protected abstract void executeService(RestAdapter restAdapter);

    @Override
    public void success(final K k, final Response response) {
        if (handler != null) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    sendResponse(k, response);
                }
            });
        } else {
            sendResponse(k, response);
        }
    }

    private void sendResponse(K k, Response response) {
        if (onResultCallback != null) {
            onResultCallback.onResponseOk(k, response);
        }
    }

    @Override
    public void failure(final RetrofitError error) {
        if (handler != null) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    sendError(error);
                }
            });
        } else {
            sendError(error);
        }
    }

    private void sendError(RetrofitError error) {
        if (error.getResponse() != null && error.getResponse().getStatus() == 401) {
            //TODO access denied
        } else {
            if (onResultCallback != null) {
                onResultCallback.onFail(error);
            }
        }
    }

    public OnResultCallback<K> getOnResultCallback() {
        return onResultCallback;
    }

    public void setOnResultCallback(OnResultCallback<K> onResultCallback) {
        this.onResultCallback = onResultCallback;
    }

    @Override
    public void intercept(RequestFacade request) {
        String sid = sessionID();
        if (sid != null)
            request.addHeader("SWS-API-SESSION-ID", sid);
    }

    @Override
    public void log(String message) {
        Log.v("RETROFIT_LOG", message);
    }

    private String sessionID() {
        return sessionID;
    }

    public interface OnResultCallback<K> {
        void onResponseOk(K k, Response r);

        void onFail(RetrofitError error);
    }
}
