package pl.chiqvito.sowieso.rest.dto;

public class ExpenseReportYearMonthDTO extends ExpenseReportYearDTO {

    private Integer month;

    @Override
    public String getDate() {
        return getYear() + "-" + month;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

}
