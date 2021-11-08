package ru.job4.tdd;

import java.util.Calendar;
import java.util.List;
import java.util.function.Predicate;

/**
 * Описание функционала программы через тесты TDD
 * @author Shegai Evgenii
 * @since 08.11.2021
 * @version 1.0
 */

public class Cinema3D implements Cinema {

    @Override
    public List<Session> find(Predicate<Session> filter) {
        return null;
    }

    @Override
    public Ticket buy(Account account, int row, int column, Calendar date) {
        return null;
    }

    @Override
    public void add(Session session) {

    }
}
