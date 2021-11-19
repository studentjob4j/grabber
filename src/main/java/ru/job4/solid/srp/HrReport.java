package ru.job4.solid.srp;

import java.util.Collections;
import java.util.Comparator;
import java.util.function.Predicate;

/**
 * Отчет для отдела найма сотрудников в порядке убывания зп
 * @author Shegai Evgenii
 * @since 20.11.2021
 * @version 1.0
 */

public class HrReport implements Report {

    private Store store;

    public HrReport(Store store) {
        this.store = store;
    }

    @Override
    public String generate(Predicate<Employee> filter) {
        StringBuilder text = new StringBuilder();
        text.append("Name; Salary;");
        for (Employee employee : store.findBy(filter)) {
            text.append(System.lineSeparator())
                    .append(employee.getName()).append(";")
                    .append(employee.getSalary()).append(";");
        }
        return text.toString();
    }

    public String showAll() {
        Comparator<Employee> comparator = (x, y) -> (int) (y.getSalary() - x.getSalary());
        MemStore memStore = (MemStore) store;
        Collections.sort(memStore.getList(), comparator);
        return (generate(x -> true));
    }
}
