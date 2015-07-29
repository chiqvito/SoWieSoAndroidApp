package pl.chiqvito.sowieso.db.model;

import android.os.Parcel;
import android.os.Parcelable;

public class CarEntity implements Parcelable {

    public static final Parcelable.Creator<CarEntity> CREATOR = new Parcelable.Creator<CarEntity>() {
        public CarEntity createFromParcel(Parcel in) {
            return new CarEntity(in);
        }

        public CarEntity[] newArray(int size) {
            return new CarEntity[size];
        }
    };

    private Long id;
    private String name;
    private Boolean isSelected;

    public CarEntity() {
        isSelected = false;
    }

    private CarEntity(Parcel in) {
        readFromParcel(in);
    }

    private void readFromParcel(Parcel in) {
        id = (Long) in.readValue(Long.class.getClassLoader());
        name = (String) in.readValue(String.class.getClassLoader());
        isSelected = (Boolean) in.readValue(Boolean.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeValue(isSelected);
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

    public Boolean getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(Boolean isSelected) {
        this.isSelected = isSelected;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CarEntity)) return false;

        CarEntity carEntity = (CarEntity) o;

        if (id != null ? !id.equals(carEntity.id) : carEntity.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
