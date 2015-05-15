package pl.chiqvito.sowieso.rest.dto;

import com.google.gson.annotations.SerializedName;

import pl.chiqvito.sowieso.db.model.CarEntity;

public class InventoryCarDTO {
    @SerializedName("id")
    private Long id;

    @SerializedName("name")
    private String name;

    public CarEntity toCarEntity() {
        CarEntity car = new CarEntity();
        car.setId(getId());
        car.setName(getName());
        return car;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
