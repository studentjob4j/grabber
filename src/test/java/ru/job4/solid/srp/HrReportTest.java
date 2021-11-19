package ru.job4.solid.srp;

import org.junit.Test;
import java.util.Calendar;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * @author Shegai Evgenii
 * @since 20.11.2021
 * @version 1.0
 */

public class HrReportTest {

    private Calendar hired = Calendar.getInstance();
    private Calendar fired = Calendar.getInstance();

    @Test
    public void whenGetSortListByFieldSalary() {
        MemStore store = new MemStore();
        hired.set(2020, 02, 22);
        fired.set(2021, 05, 11);
        Employee worker = new Employee("Ivan", hired, fired, 8);
        store.add(worker);
        Employee worker2 = new Employee("Petr", hired, fired, 10);
        store.add(worker2);
        Employee worker3 = new Employee("Kolya", hired, fired, 14);
        store.add(worker3);
        String result = new HrReport(store).showAll();
        assertThat(result, is("Name; Salary;\r\nKolya;14.0;\r\nPetr;10.0;\r\nIvan;8.0;"));
    }

}