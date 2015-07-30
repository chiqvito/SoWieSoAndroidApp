package pl.chiqvito.sowieso.bus.events;

import pl.chiqvito.sowieso.Constants;
import pl.chiqvito.sowieso.rest.dto.InventoryCarConsumptionDTO;

public class CarConsumptionOperationEvent extends Event {

    private final Operation operation;
    private int page;
    private InventoryCarConsumptionDTO dto;

    public CarConsumptionOperationEvent(Operation operation, int page, InventoryCarConsumptionDTO dto) {
        this.operation = operation;
        this.page = page;
        this.dto = dto;
    }

    public CarConsumptionOperationEvent(Operation operation, int page) {
        this(operation, page, null);
    }

    public CarConsumptionOperationEvent(Operation operation, InventoryCarConsumptionDTO dto) {
        this(operation, Constants.ERROR_PAGE, dto);
    }

    public Operation getOperation() {
        return operation;
    }

    public int getPage() {
        return page;
    }

    public InventoryCarConsumptionDTO getDto() {
        return dto;
    }

    @Override
    public String toString() {
        return "CarConsumptionOperationEvent{dto=" + dto + ", page=" + page + ", operation=" + operation + '}';
    }
}
