package pl.chiqvito.sowieso.rest.dto;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import pl.chiqvito.sowieso.db.model.CarEntity;

public class InventoryCarDTO implements Parcelable {

    public static final Parcelable.Creator<InventoryCarDTO> CREATOR = new Parcelable.Creator<InventoryCarDTO>() {
        public InventoryCarDTO createFromParcel(Parcel in) {
            return new InventoryCarDTO(in);
        }

        public InventoryCarDTO[] newArray(int size) {
            return new InventoryCarDTO[size];
        }
    };

    @SerializedName("id")
    private Long id;

    @SerializedName("name")
    private String name;

    private InventoryCarDTO(Parcel in) {
        readFromParcel(in);
    }

    private void readFromParcel(Parcel in) {
        id = (Long) in.readValue(Long.class.getClassLoader());
        name = (String) in.readValue(String.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(name);
    }

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
