package pl.chiqvito.sowieso.rest.client;

import android.content.Context;

import java.util.List;

import pl.chiqvito.sowieso.rest.ApiService;
import pl.chiqvito.sowieso.rest.dto.InventoryCarDTO;

public class InventoryCarsClient extends BaseApiClient<List<InventoryCarDTO>> {
    private Integer page;

    public InventoryCarsClient(Context context, String sessionID, Integer page) {
        super(context, sessionID);
        this.page = page;
    }

    @Override
    protected void executeService(ApiService service) {
        service.cars(page, this);
    }
}
