package pl.chiqvito.sowieso.rest.dto.enums;

import android.os.Parcel;
import android.os.Parcelable;

public enum CurrencyEnum implements Parcelable {
    PLN, EUR, USD;
    public static final Parcelable.Creator<CurrencyEnum> CREATOR = new Parcelable.Creator<CurrencyEnum>() {
        public CurrencyEnum createFromParcel(Parcel in) {
            String value = in.readString();
            if (value != null)
                return CurrencyEnum.valueOf(value);
            return null;
        }

        public CurrencyEnum[] newArray(int size) {
            return new CurrencyEnum[size];
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
