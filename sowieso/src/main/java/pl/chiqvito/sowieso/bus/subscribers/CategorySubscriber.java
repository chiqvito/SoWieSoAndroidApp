package pl.chiqvito.sowieso.bus.subscribers;

import android.util.Log;

import java.util.List;

import de.greenrobot.event.EventBus;
import pl.chiqvito.sowieso.bus.events.CategoriesEvent;
import pl.chiqvito.sowieso.bus.events.CategoryOperationEvent;
import pl.chiqvito.sowieso.db.model.CategoryEntity;
import pl.chiqvito.sowieso.db.service.CategoriesService;

public class CategorySubscriber {

    private static final String TAG = CategorySubscriber.class.getName();

    private final CategoriesService categoriesService;

    public CategorySubscriber(CategoriesService categoriesService) {
        this.categoriesService = categoriesService;
    }

    public void onEvent(CategoryOperationEvent event) {
        Log.v(TAG, "event:" + event);
        switch (event.getOperation()) {
            case CategoryOperationEvent.SELECT: {
                categoriesService.select(event.getCategory());
                break;
            }
            case CategoryOperationEvent.GET_ALL: {
                List<CategoryEntity> categories = categoriesService.categories();
                if (categories.isEmpty()) {
//TODO download
                } else {
                    EventBus.getDefault().post(new CategoriesEvent(categories));
                }
                break;
            }
        }
    }

}
