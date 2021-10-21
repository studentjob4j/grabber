package ru.job4.grabber;

import org.junit.Test;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class SqlRuDateTimeParserTest {

    // для запуска сервиса codecov
    @Test
    public void whenGetMonths() {
        SqlRuDateTimeParser parse = new SqlRuDateTimeParser();
        parse.createMonths();
        assertThat(parse.getMonths().size(), is(12));
    }

}