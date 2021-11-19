package ru.job4.solid.srp;

import java.util.List;
import java.util.function.Predicate;

/**
 * @author Shegai Evgenii
 * @since 20.11.2021
 * @version 1.0
 */

public interface Store {

    List<Employee> findBy(Predicate<Employee> filter);
}
