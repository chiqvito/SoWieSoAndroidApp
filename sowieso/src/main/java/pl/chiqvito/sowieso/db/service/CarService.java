package pl.chiqvito.sowieso.db.service;

import java.util.List;

import pl.chiqvito.sowieso.db.model.CarEntity;
import pl.chiqvito.sowieso.rest.dto.InventoryCarDTO;

public interface CarService {
    void select(CarEntity car);

    List<CarEntity> cars();

    void saveCars(List<InventoryCarDTO> dtoList);
}
