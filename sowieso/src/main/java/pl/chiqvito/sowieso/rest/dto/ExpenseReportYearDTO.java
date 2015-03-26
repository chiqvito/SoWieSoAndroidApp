package pl.chiqvito.sowieso.rest.dto;

public class ExpenseReportYearDTO extends ExpenseReportDTO {

    private Integer year;

    @Override
    public String getDate() {
        return String.valueOf(year);
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

}
