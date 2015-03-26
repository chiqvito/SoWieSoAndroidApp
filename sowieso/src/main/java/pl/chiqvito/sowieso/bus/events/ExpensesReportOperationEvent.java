package pl.chiqvito.sowieso.bus.events;

import pl.chiqvito.sowieso.ui.fragment.FragmentBuilder;

public class ExpensesReportOperationEvent {

    private FragmentBuilder.FragmentName fragmentName;
    private Integer year;
    private Integer month;

    public ExpensesReportOperationEvent(FragmentBuilder.FragmentName fragmentName) {
        this(fragmentName, null, null);
    }

    public ExpensesReportOperationEvent(FragmentBuilder.FragmentName fragmentName, Integer year) {
        this(fragmentName, year, null);
    }

    public ExpensesReportOperationEvent(FragmentBuilder.FragmentName fragmentName, Integer year, Integer month) {
        this.fragmentName = fragmentName;
        this.year = year;
        this.month = month;
    }

    public FragmentBuilder.FragmentName getFragmentName() {
        return fragmentName;
    }

    public Integer getYear() {
        return year;
    }

    public Integer getMonth() {
        return month;
    }

    @Override
    public String toString() {
        return "ExpensesReportOperationEvent{" +
                "fragmentName=" + fragmentName +
                ", year=" + year +
                ", month=" + month +
                '}';
    }
}
