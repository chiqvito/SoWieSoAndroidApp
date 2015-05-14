package pl.chiqvito.sowieso.bus.subscribers;

import android.content.Context;
import android.util.Log;

import java.util.List;

import de.greenrobot.event.EventBus;
import pl.chiqvito.sowieso.bus.events.CategoriesEvent;
import pl.chiqvito.sowieso.bus.events.CategoryOperationEvent;
import pl.chiqvito.sowieso.bus.events.Event;
import pl.chiqvito.sowieso.db.model.CategoryEntity;
import pl.chiqvito.sowieso.db.service.CategoriesService;
import pl.chiqvito.sowieso.rest.client.BasicOnResultCallback;
import pl.chiqvito.sowieso.rest.client.CategoriesClient;
import pl.chiqvito.sowieso.rest.dto.CategoryDTO;
import retrofit.client.Response;

public class CategorySubscriber {

    private static final String TAG = CategorySubscriber.class.getName();

    private final Context context;
    private final CategoriesService categoriesService;

    public CategorySubscriber(Context context, CategoriesService categoriesService) {
        this.context = context;
        this.categoriesService = categoriesService;
    }

    public void onEventBackgroundThread(CategoryOperationEvent event) {
        Log.v(TAG, "event:" + event);
        switch (event.getOperation()) {
            case SELECT: {
                categoriesService.select(event.getCategory());
                break;
            }
            case GET_ALL: {
                List<CategoryEntity> categories = categoriesService.categories();
                Log.v(TAG, "categories:" + categories);
                if (categories.isEmpty()) {
                    EventBus.getDefault().post(new CategoryOperationEvent(Event.Operation.DOWNLOAD, null));
                } else {
                    EventBus.getDefault().post(new CategoriesEvent(categories));
                }
                break;
            }
        }
    }

    public void onEventAsync(CategoryOperationEvent event) {
        Log.v(TAG, "event:" + event);

        if (event.getOperation() != Event.Operation.DOWNLOAD)
            return;

        CategoriesClient client = new CategoriesClient(context);
        client.setOnResultCallback(new BasicOnResultCallback<List<CategoryDTO>>() {
            @Override
            public void onResponseOk(List<CategoryDTO> categoryDTOs, Response r) {
                categoriesService.saveCategories(categoryDTOs);
                List<CategoryEntity> categories = categoriesService.categories();
                EventBus.getDefault().post(new CategoriesEvent(categories));
            }
        });
        client.execute();
    }

}
