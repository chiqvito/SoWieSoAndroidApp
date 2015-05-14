package pl.chiqvito.sowieso.bus.events;

import java.util.List;

import pl.chiqvito.sowieso.rest.dto.InventoryCarConsumptionDTO;

public class CarConsumptionsEvent {

    private List<InventoryCarConsumptionDTO> consumptions;
    private int page;

    public CarConsumptionsEvent(List<InventoryCarConsumptionDTO> consumptions, int page) {
        this.consumptions = consumptions;
        this.page = page;
    }

    public List<InventoryCarConsumptionDTO> getConsumptions() {
        return consumptions;
    }

    public int getPage() {
        return page;
    }

    @Override
    public String toString() {
        return "CarConsumptionsEvent{page=" + page + '}';
    }
}
