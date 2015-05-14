package pl.chiqvito.sowieso.bus.subscribers;

import android.content.Context;
import android.util.Log;

import java.util.Collections;
import java.util.List;

import de.greenrobot.event.EventBus;
import pl.chiqvito.sowieso.Constants;
import pl.chiqvito.sowieso.bus.events.CarConsumptionOperationEvent;
import pl.chiqvito.sowieso.bus.events.CarConsumptionsEvent;
import pl.chiqvito.sowieso.db.service.PropertiesService;
import pl.chiqvito.sowieso.rest.client.BasicOnResultCallback;
import pl.chiqvito.sowieso.rest.client.InventoryCarConsumptionsClient;
import pl.chiqvito.sowieso.rest.dto.InventoryCarConsumptionDTO;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class CarConsumptionSubscriber {

    private static final String TAG = CarConsumptionSubscriber.class.getName();

    private final Context context;
    private final PropertiesService propertiesService;

    public CarConsumptionSubscriber(Context context, PropertiesService propertiesService) {
        this.context = context;
        this.propertiesService = propertiesService;
    }

    public void onEventAsync(CarConsumptionOperationEvent event) {
        Log.v(TAG, "event:" + event);
        switch (event.getOperation()) {
            case GET_ALL: {
                getAll(event.getPage());
                break;
            }
        }
    }

    private void getAll(final int page) {
        InventoryCarConsumptionsClient client = new InventoryCarConsumptionsClient(context, propertiesService.getSessionId(), page);
        client.setOnResultCallback(new BasicOnResultCallback<List<InventoryCarConsumptionDTO>>() {
            @Override
            public void onResponseOk(List<InventoryCarConsumptionDTO> inventoryCarConsumptionDTOs, Response r) {
                if (inventoryCarConsumptionDTOs.isEmpty()) {
                    EventBus.getDefault().post(new CarConsumptionsEvent(Collections.<InventoryCarConsumptionDTO>emptyList(), Constants.EMPTY_PAGE));
                    return;
                }
                EventBus.getDefault().post(new CarConsumptionsEvent(inventoryCarConsumptionDTOs, page));
            }

            @Override
            public void onFail(RetrofitError error) {
                super.onFail(error);
                EventBus.getDefault().post(new CarConsumptionsEvent(Collections.<InventoryCarConsumptionDTO>emptyList(), Constants.ERROR_PAGE));
            }
        });
        client.execute();
    }

}
