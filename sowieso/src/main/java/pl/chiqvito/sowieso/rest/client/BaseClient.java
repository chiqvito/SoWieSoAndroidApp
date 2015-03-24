package pl.chiqvito.sowieso.rest.client;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.util.Date;

import pl.chiqvito.sowieso.Constants;
import pl.chiqvito.sowieso.R;
import pl.chiqvito.sowieso.utils.Boast;
import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

public abstract class BaseClient<K> implements Callback<K>, RequestInterceptor, RestAdapter.Log {

    private static final String TAG = "RETROFIT_LOG";

    private final Context context;
    private String sessionID;

    private OnResultCallback<K> onResultCallback;
    protected Handler handler;

    public BaseClient(Context context, String sessionID, boolean runInThread) {
        this.context = context;
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
                .setConverter(new GsonConverter(gson()))
                .build();

        executeService(restAdapter);
    }

    private Gson gson() {
        JsonSerializer<Date> dateSerializer = new JsonSerializer<Date>() {
            @Override
            public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
                return src == null ? null : new JsonPrimitive(src.getTime());
            }
        };
        JsonDeserializer<Date> dateDeserializer = new JsonDeserializer<Date>() {
            @Override
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return json == null ? null : new Date(json.getAsLong());
            }
        };
        return new GsonBuilder()
                .registerTypeAdapter(Date.class, dateSerializer)
                .registerTypeAdapter(Date.class, dateDeserializer).create();
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
        if (error.getResponse() != null) {
            Log.w(TAG, error.getResponse().getStatus() + " " + error.getResponse().getReason());
            switch (error.getResponse().getStatus()) {
                case 401: {
                    Boast.showText(context, context.getString(R.string.msg_unauthorized), Toast.LENGTH_SHORT);
                    break;
                }
                case 403: {
                    Boast.showText(context, context.getString(R.string.msg_forbidden), Toast.LENGTH_SHORT);
                    break;
                }
                default: {
                    Boast.showText(context, error.getResponse().getReason(), Toast.LENGTH_SHORT);
                    break;
                }
            }
        }
        if (onResultCallback != null) {
            onResultCallback.onFail(error);
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
        Log.v(TAG, message);
    }

    private String sessionID() {
        return sessionID;
    }

    public interface OnResultCallback<K> {
        void onResponseOk(K k, Response r);

        void onFail(RetrofitError error);
    }
}
