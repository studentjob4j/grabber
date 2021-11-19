package ru.job4.solid.srp;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author Shegai Evgenii
 * @since 20.11.2021
 * @version 1.0
 */

public class MemStore implements Store {

    private List<Employee> list = new ArrayList<>();

    public void add(Employee value) {
        list.add(value);
    }

    public List<Employee> getList() {
        return list;
    }

    @Override
    public List<Employee> findBy(Predicate<Employee> filter) {
        List<Employee> result = list.stream().filter(filter::test).collect(Collectors.toList());
        return result;
    }
}
