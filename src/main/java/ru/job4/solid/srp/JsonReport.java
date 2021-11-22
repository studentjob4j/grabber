package ru.job4.solid.srp;

import java.util.function.Predicate;

/**
 * @author Shegai Evgenii
 * @since 22.11.2021
 * @version 1.0
 */

public class JsonReport implements Report {

    private Store store;

    public JsonReport(Store store) {
        this.store = store;
    }

    @Override
    public String generate(Predicate<Employee> filter) {
        StringBuilder text = new StringBuilder();
        text.append("{");
        for (Employee employee : store.findBy(filter)) {
            text.append(System.lineSeparator())
                    .append("name : ").append(employee.getName())
                    .append(";").append(System.lineSeparator())
                    .append("hired : ").append(employee.getHired())
                    .append(";").append(System.lineSeparator())
                    .append("fired : ").append(employee.getFired())
                    .append(";").append(System.lineSeparator())
                    .append("salary : ").append(employee.getSalary())
                    .append(";").append(System.lineSeparator())
                    .append("}");
        }
        return text.toString();
    }
}
