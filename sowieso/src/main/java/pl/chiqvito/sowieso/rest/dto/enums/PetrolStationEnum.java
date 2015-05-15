package pl.chiqvito.sowieso.rest.dto.enums;

import android.os.Parcel;
import android.os.Parcelable;

public enum PetrolStationEnum implements Parcelable {
    BP,
    SHELL,
    ORLEN;

    public static final Parcelable.Creator<PetrolStationEnum> CREATOR = new Parcelable.Creator<PetrolStationEnum>() {
        public PetrolStationEnum createFromParcel(Parcel in) {
            String value = in.readString();
            if (value != null)
                return PetrolStationEnum.valueOf(value);
            return null;
        }

        public PetrolStationEnum[] newArray(int size) {
            return new PetrolStationEnum[size];
        }
    };

    public String toString() {
        return name().toLowerCase();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name());
    }
}