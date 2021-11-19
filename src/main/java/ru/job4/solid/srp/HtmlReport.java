package ru.job4.solid.srp;

import java.util.function.Predicate;

/**
 * Отчет для отдела программистов ввиде html
 * @author Shegai Evgenii
 * @since 20.11.2021
 * @version 1.0
 */

public class HtmlReport implements Report {

    private Store store;

    public HtmlReport(Store store) {
        this.store = store;
    }

    @Override
    public String generate(Predicate<Employee> filter) {
        StringBuilder text = new StringBuilder();
        text.append("Name; Salary;");
        for (Employee employee : store.findBy(filter)) {
            text.append(System.lineSeparator())
                    .append(employee.getName()).append(";")
                    .append(employee.getHired()).append(";")
                    .append(employee.getFired()).append(";")
                    .append(employee.getSalary()).append(";")
                    .append(System.lineSeparator());
        }
        String template = "<html><head><title>HtmlReport</title></head><body>%s</body></html>";
        String result = String.format(template, text.toString());
        return result;
    }
}
