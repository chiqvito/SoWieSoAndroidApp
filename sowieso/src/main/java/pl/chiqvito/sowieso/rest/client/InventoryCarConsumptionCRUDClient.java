package pl.chiqvito.sowieso.rest.client;

import android.content.Context;

import pl.chiqvito.sowieso.rest.ApiService;
import pl.chiqvito.sowieso.rest.dto.InventoryCarConsumptionDTO;

public class InventoryCarConsumptionCRUDClient extends BaseApiClient<Boolean> {
    private InventoryCarConsumptionDTO dto;
    private Operation operation;

    public InventoryCarConsumptionCRUDClient(Context context, String sessionID, Operation operation, InventoryCarConsumptionDTO dto) {
        super(context, sessionID);
        this.dto = dto;
        this.operation = operation;
    }

    @Override
    protected void executeService(ApiService service) {
        switch (operation) {
            case CREATE: {
                service.createCarConsumption(dto, this);
                break;
            }
            case REMOVE: {
                service.removeCarConsumption(dto.getId(), this);
                break;
            }
            case UPDATE: {
                service.updateCarConsumption(dto, this);
                break;
            }
        }
    }
}
