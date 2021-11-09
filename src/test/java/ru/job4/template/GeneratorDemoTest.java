package ru.job4.template;

import org.junit.Ignore;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import static org.junit.Assert.*;

/**
 * Шаблонизатор
 * @author Shegai Evgenii
 * @since 09.11.2021
 * @version 1.0
 */

public class GeneratorDemoTest {

    private final String template = "I am a ${name}, Who are ${subject}?";

    @Test
    public void whenGetValueByKey() {
        Map<String, String> map = new HashMap<>();
        map.put("name", "John");
        map.put("subject", "you");
        String result = new GeneratorDemo().produce(template, map);
        assertNull(result);
    }

    @Ignore
    @Test (expected = NoSuchElementException.class)
    public void whenKeyNotExist() {
        Map<String, String> map = new HashMap<>();
        map.put("anykey", "John");
        map.put("we", "you");
        String result = new GeneratorDemo().produce(template, map);
    }

    // when map have more then 2 keys and values  throw exception
    @Ignore
    @Test (expected = UnsupportedOperationException.class)
    public void whenMapHaveMoreThenTwoKey() {
        Map<String, String> map = new HashMap<>();
        map.put("name", "John");
        map.put("key", "value");
        map.put("subject", "you");
        String result = new GeneratorDemo().produce(template, map);
    }

}