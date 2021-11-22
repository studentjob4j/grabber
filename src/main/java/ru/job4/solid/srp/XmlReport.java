package ru.job4.solid.srp;

import java.util.function.Predicate;

/**
 * @author Shegai Evgenii
 * @since 22.11.2021
 * @version 1.0
 */

public class XmlReport implements Report {

    private Store store;

    public XmlReport(Store store) {
        this.store = store;
    }

    @Override
    public String generate(Predicate<Employee> filter) {
        StringBuilder text = new StringBuilder();
        text.append("<?xml version=\"1.1\" encoding=\"UTF-8\" ?>\n").append(System.lineSeparator());
        text.append("<example>").append(System.lineSeparator());
        for (Employee employee : store.findBy(filter)) {
            text.append("<name>").append(employee.getName())
                    .append("</name>").append(System.lineSeparator())
                    .append("<hired>").append(employee.getHired())
                    .append("</hired>").append(System.lineSeparator())
                    .append("<fired>").append(employee.getFired())
                    .append("</fired>").append(System.lineSeparator())
                    .append("<salary>").append(employee.getSalary())
                    .append("</salary>").append(System.lineSeparator())
                    .append("</example>");
        }
        return text.toString();
    }
}
