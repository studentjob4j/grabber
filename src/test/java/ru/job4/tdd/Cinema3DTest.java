package ru.job4.tdd;

import org.junit.Ignore;
import org.junit.Test;
import java.util.Calendar;
import static org.junit.Assert.assertNull;

/**
 * Описание функционала программы через тесты TDD
 * @author Shegai Evgenii
 * @since 08.11.2021
 * @version 1.0
 */

public class Cinema3DTest {

    @Test
    public void buy() {
        Account account = new AccountCinema();
        Cinema cinema = new Cinema3D();
        Calendar date = Calendar.getInstance();
        date.set(2020, 10, 10, 23, 00);
        assertNull(cinema.buy(account, 1, 1, date));
    }

    @Test
    public void find() {
        Cinema cinema = new Cinema3D();
        cinema.add(new Session3D());
        assertNull(cinema.find(session -> true));
    }

    // when buy two tickets on one place then get exception
    @Ignore
    @Test (expected = UnsupportedOperationException.class)
    public void whenBuyTwoTicketsOnOnePlace() {
        Account account = new AccountCinema();
        Cinema cinema = new Cinema3D();
        Calendar date = Calendar.getInstance();
        date.set(2020, 10, 10, 23, 00);
        Ticket ticket = cinema.buy(account, 1, 1, date);
        Ticket ticket2 = cinema.buy(account, 1, 1, date);
    }

    @Ignore
    @Test (expected = UnsupportedOperationException.class)
    public void whenIncorrectDate() {
        Account account = new AccountCinema();
        Cinema cinema = new Cinema3D();
        Calendar date = Calendar.getInstance();
        date.set(2019, 10, 10, 23, 00);
        Ticket ticket = cinema.buy(account, 1, 1, date);
    }

    @Ignore
    @Test (expected = UnsupportedOperationException.class)
    public void whenIncorrectPlace() {
        Account account = new AccountCinema();
        Cinema cinema = new Cinema3D();
        Calendar date = Calendar.getInstance();
        date.set(2020, 10, 10, 23, 00);
        Ticket ticket = cinema.buy(account, -1, 1, date);
    }
}
