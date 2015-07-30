package pl.chiqvito.sowieso.rest.dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;
import java.util.Date;

import pl.chiqvito.sowieso.rest.dto.enums.CurrencyEnum;
import pl.chiqvito.sowieso.rest.dto.enums.PetrolStationEnum;
import pl.chiqvito.sowieso.utils.DateUtil;

public class InventoryCarConsumptionDTO implements Parcelable {

    public static final Parcelable.Creator<InventoryCarConsumptionDTO> CREATOR = new Parcelable.Creator<InventoryCarConsumptionDTO>() {
        public InventoryCarConsumptionDTO createFromParcel(Parcel in) {
            return new InventoryCarConsumptionDTO(in);
        }

        public InventoryCarConsumptionDTO[] newArray(int size) {
            return new InventoryCarConsumptionDTO[size];
        }
    };

    private Long id;
    private InventoryCarDTO car;
    private Date refuelDate;
    private BigDecimal refuelAmount;
    private BigDecimal distance;
    private BigDecimal price;
    private CurrencyEnum currency;
    private PetrolStationEnum petrolStation;
    private BigDecimal totalCost;
    private BigDecimal combustion;

    public InventoryCarConsumptionDTO() {
    }

    private InventoryCarConsumptionDTO(Parcel in) {
        readFromParcel(in);
    }

    private void readFromParcel(Parcel in) {
        id = (Long) in.readValue(Long.class.getClassLoader());
        car = in.readParcelable(InventoryCarDTO.class.getClassLoader());
        refuelDate = (Date) in.readValue(Date.class.getClassLoader());
        refuelAmount = (BigDecimal) in.readValue(BigDecimal.class.getClassLoader());
        distance = (BigDecimal) in.readValue(BigDecimal.class.getClassLoader());
        price = (BigDecimal) in.readValue(BigDecimal.class.getClassLoader());
        currency = in.readParcelable(CurrencyEnum.class.getClassLoader());
        petrolStation = in.readParcelable(PetrolStationEnum.class.getClassLoader());
        totalCost = (BigDecimal) in.readValue(BigDecimal.class.getClassLoader());
        combustion = (BigDecimal) in.readValue(BigDecimal.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeParcelable(car, flags);
        dest.writeValue(refuelDate);
        dest.writeValue(refuelAmount);
        dest.writeValue(distance);
        dest.writeValue(price);
        dest.writeParcelable(currency, flags);
        dest.writeParcelable(petrolStation, flags);
        dest.writeValue(totalCost);
        dest.writeValue(combustion);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public InventoryCarDTO getCar() {
        return car;
    }

    public void setCar(InventoryCarDTO car) {
        this.car = car;
    }

    public Date getRefuelDate() {
        return refuelDate;
    }

    public String getRefuelDateString() {
        return DateUtil.date(getRefuelDate());
    }

    public void setRefuelDate(Date refuelDate) {
        this.refuelDate = refuelDate;
    }

    public BigDecimal getRefuelAmount() {
        return refuelAmount;
    }

    public void setRefuelAmount(BigDecimal refuelAmount) {
        this.refuelAmount = refuelAmount;
    }

    public BigDecimal getDistance() {
        return distance;
    }

    public void setDistance(BigDecimal distance) {
        this.distance = distance;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public CurrencyEnum getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyEnum currency) {
        this.currency = currency;
    }

    public PetrolStationEnum getPetrolStation() {
        return petrolStation;
    }

    public void setPetrolStation(PetrolStationEnum petrolStation) {
        this.petrolStation = petrolStation;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public BigDecimal getCombustion() {
        return combustion;
    }

    public void setCombustion(BigDecimal combustion) {
        this.combustion = combustion;
    }
}
