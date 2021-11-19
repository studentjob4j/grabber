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

public class BookkeepingTest {

    private Calendar hired = Calendar.getInstance();
    private Calendar fired = Calendar.getInstance();

    //изменил тип зп -> доллары перевел в рубли

    @Test
    public void whenChangeTypeSalary() {
        MemStore store = new MemStore();
        hired.set(2020, 02, 22);
        fired.set(2021, 05, 11);
        Employee worker = new Employee("Ivan", hired, fired,  12);
        store.add(worker);
        Report report = new Bookkeeping(store);
        String[] temp = report.generate(x -> true).split(";");
        assertThat(temp[temp.length - 2], is("864.0"));
    }

}