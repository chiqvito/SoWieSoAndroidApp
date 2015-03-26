package pl.chiqvito.sowieso.bus.events;

import java.util.List;

import pl.chiqvito.sowieso.rest.dto.ExpenseReportDTO;

public class ExpensesReportEvent {

    private List<? extends ExpenseReportDTO> reports;

    public ExpensesReportEvent(List<? extends ExpenseReportDTO> reports) {
        this.reports = reports;
    }

    public List<? extends ExpenseReportDTO> getReports() {
        return reports;
    }

    @Override
    public String toString() {
        return "ExpensesReportEvent{reports=" + reports + '}';
    }
}
