package ru.job4.parser;

import org.junit.Test;
import java.util.List;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SqlRuParseTest {

    @Test
    public void whenGetListPost() {
        SqlRuParse parse = new SqlRuParse();
        List<Post> list = parse.list("https://www.sql.ru/forum/job-offers");
        assertThat(list.size(), is(50));
    }

}