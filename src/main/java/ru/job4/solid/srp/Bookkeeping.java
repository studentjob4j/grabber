package ru.job4.solid.srp;

import java.util.function.Predicate;

/**
 * Отчет для отдела бухгалтерии в другой валюте
 * @author Shegai Evgenii
 * @since 20.11.2021
 * @version 1.0
 */

public class Bookkeeping implements Report {

    private Store store;

    public Bookkeeping(Store store) {
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
                    .append(convertUsdRub(employee.getSalary())).append(";")
                    .append(System.lineSeparator());
        }
        return text.toString();
    }

    private double convertUsdRub(double value) {
        return value * 72;
    }
}