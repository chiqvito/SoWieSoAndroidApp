package pl.chiqvito.sowieso.rest.dto;

import java.math.BigDecimal;

import pl.chiqvito.sowieso.rest.dto.enums.CurrencyEnum;

public abstract class ExpenseReportDTO {
    protected BigDecimal amount;
    protected CurrencyEnum currency;

    public abstract String getDate();

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public CurrencyEnum getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyEnum currency) {
        this.currency = currency;
    }
}
