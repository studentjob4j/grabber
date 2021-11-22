package ru.job4.solid.srp;

import org.junit.Test;
import java.util.Calendar;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * @author Shegai Evgenii
 * @since 22.11.2021
 * @version 1.0
 */

public class XmlReportTest {

    private Calendar hired = Calendar.getInstance();
    private Calendar fired = Calendar.getInstance();

    @Test
    public void whenGetXmlReport() {
        MemStore store = new MemStore();
        hired.set(2020, 02, 22);
        fired.set(2021, 05, 11);
        Employee worker = new Employee("Ivan", hired, fired, 8);
        store.add(worker);
        String xml = new XmlReport(store).generate(x -> true);
        StringBuilder builder = new StringBuilder();
        builder.append("<?xml version=\"1.1\" encoding=\"UTF-8\" ?>\n")
                .append(System.lineSeparator());
        builder.append("<example>").append(System.lineSeparator());
        builder.append("<name>").append("Ivan")
                .append("</name>").append(System.lineSeparator());
        builder.append("<hired>").append(hired)
                .append("</hired>").append(System.lineSeparator());
        builder.append("<fired>").append(fired)
                .append("</fired>").append(System.lineSeparator());
        builder.append("<salary>").append("8.0")
                .append("</salary>").append(System.lineSeparator());
        builder.append("</example>");
        assertThat(xml, is(builder.toString()));
    }

}