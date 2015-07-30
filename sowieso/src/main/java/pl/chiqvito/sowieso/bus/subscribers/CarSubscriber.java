package pl.chiqvito.sowieso.bus.subscribers;

import android.content.Context;
import android.util.Log;

import java.util.List;

import de.greenrobot.event.EventBus;
import pl.chiqvito.sowieso.Constants;
import pl.chiqvito.sowieso.bus.events.CarOperationEvent;
import pl.chiqvito.sowieso.bus.events.CarsEvent;
import pl.chiqvito.sowieso.bus.events.Event;
import pl.chiqvito.sowieso.db.model.CarEntity;
import pl.chiqvito.sowieso.db.service.CarService;
import pl.chiqvito.sowieso.db.service.PropertiesService;
import pl.chiqvito.sowieso.rest.client.BasicOnResultCallback;
import pl.chiqvito.sowieso.rest.client.InventoryCarsClient;
import pl.chiqvito.sowieso.rest.dto.InventoryCarDTO;
import retrofit.client.Response;

public class CarSubscriber {
    private static final String TAG = CarSubscriber.class.getName();

    private final Context context;
    private final PropertiesService propertiesService;
    private final CarService carService;

    public CarSubscriber(Context context, PropertiesService propertiesService, CarService carService) {
        this.context = context;
        this.propertiesService = propertiesService;
        this.carService = carService;
    }

    public void onEventBackgroundThread(CarOperationEvent event) {
        Log.v(TAG, "event:" + event);
        switch (event.getOperation()) {
            case SELECT: {
                carService.select(event.getCar());
                break;
            }
            case GET_ALL: {
                List<CarEntity> cars = carService.cars();
                Log.v(TAG, "cars:" + cars);
                if (cars.isEmpty()) {
                    EventBus.getDefault().post(new CarOperationEvent(Event.Operation.DOWNLOAD, Constants.FIRST_PAGE));
                } else {
                    EventBus.getDefault().post(new CarsEvent(cars));
                }
                break;
            }
        }
    }

    public void onEventAsync(CarOperationEvent event) {
        Log.v(TAG, "event:" + event);

        if (event.getOperation() != Event.Operation.DOWNLOAD)
            return;

        final int page = event.getPage();
        InventoryCarsClient client = new InventoryCarsClient(context, propertiesService.getSessionId(), page);
        client.setOnResultCallback(new BasicOnResultCallback<List<InventoryCarDTO>>() {
            @Override
            public void onResponseOk(List<InventoryCarDTO> carDTOs, Response r) {
                if (carDTOs.isEmpty()) {
                    List<CarEntity> cars = carService.cars();
                    EventBus.getDefault().post(new CarsEvent(cars));
                } else {
                    int nextPage = page + 1;
                    EventBus.getDefault().post(new CarOperationEvent(Event.Operation.DOWNLOAD, nextPage));
                }

                if (Constants.FIRST_PAGE == page) {
                    carService.saveCars(carDTOs);
                } else {
                    List<CarEntity> cars = carService.cars();
                    for (CarEntity car : cars) {
                        carDTOs.add(car.toCarDTO());
                    }
                    carService.saveCars(carDTOs);
                }
            }
        });
        client.execute();
    }

}
