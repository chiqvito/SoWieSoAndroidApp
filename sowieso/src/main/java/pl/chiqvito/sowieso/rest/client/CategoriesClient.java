package pl.chiqvito.sowieso.rest.client;

import android.content.Context;

import java.util.List;

import pl.chiqvito.sowieso.rest.ApiService;
import pl.chiqvito.sowieso.rest.dto.CategoryDTO;

public class CategoriesClient extends BaseApiClient<List<CategoryDTO>> {

    public CategoriesClient(Context context) {
        super(context, null);
    }

    @Override
    protected void executeService(ApiService service) {
        service.categories(this);
    }

}
