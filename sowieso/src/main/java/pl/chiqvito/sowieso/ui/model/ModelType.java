package pl.chiqvito.sowieso.ui.model;

public enum ModelType {

    TITLE(0),
    EXPENSE(1),
    EXPENSE_REPORT_DATE_AMOUNT(2),
    EXPENSE_REPORT_DATE_CATEGORY_AMOUNT(3),
    EXPENSE_REPORT_FILTER(4),
    CAR_CONSUMPTION(5);

    private int value;

    private ModelType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
