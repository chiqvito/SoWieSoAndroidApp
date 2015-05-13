package pl.chiqvito.sowieso.rest.client;

import android.content.Context;

import java.util.List;

import pl.chiqvito.sowieso.rest.ApiService;
import pl.chiqvito.sowieso.rest.dto.InventoryCarConsumptionDTO;

public class InventoryCarConsumptionsClient extends BaseApiClient<List<InventoryCarConsumptionDTO>> {
    private Integer page;

    public InventoryCarConsumptionsClient(Context context, String sessionID, Integer page) {
        super(context, sessionID);
        this.page = page;
    }

    @Override
    protected void executeService(ApiService service) {
        service.carConsumptions(page, this);
    }
}
