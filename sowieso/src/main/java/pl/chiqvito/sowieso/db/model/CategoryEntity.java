package pl.chiqvito.sowieso.db.model;

import android.os.Parcel;
import android.os.Parcelable;

public class CategoryEntity implements Parcelable {

    public static final Parcelable.Creator<CategoryEntity> CREATOR = new Parcelable.Creator<CategoryEntity>() {
        public CategoryEntity createFromParcel(Parcel in) {
            return new CategoryEntity(in);
        }

        public CategoryEntity[] newArray(int size) {
            return new CategoryEntity[size];
        }
    };

    private Long id;
    private String name;
    private Long parentId;
    private Boolean isSelected;

    public CategoryEntity() {
        this.isSelected = false;
    }

    private CategoryEntity(Parcel in) {
        readFromParcel(in);
    }

    private void readFromParcel(Parcel in) {
        id = in.readLong();
        name = in.readString();
        parentId = in.readLong();
        isSelected = (in.readInt() == 0) ? false : true;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id == null ? -1 : id);
        dest.writeString(name == null ? "" : name);
        dest.writeLong(parentId == null ? -1 : parentId);
        dest.writeInt(isSelected ? 1 : 0);
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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Boolean getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(Boolean isSelected) {
        this.isSelected = isSelected;
    }

    @Override
    public String toString() {
        if (parentId != null)
            return " - " + name;
        return name;
    }

}
