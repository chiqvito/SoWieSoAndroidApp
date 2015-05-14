package pl.chiqvito.sowieso.rest.dto;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import pl.chiqvito.sowieso.rest.dto.enums.CurrencyEnum;
import pl.chiqvito.sowieso.rest.dto.enums.PetrolStationEnum;

public class InventoryCarConsumptionDTO {
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
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd", Locale.UK);
        return sf.format(getRefuelDate());
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
