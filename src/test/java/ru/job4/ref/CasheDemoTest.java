package ru.job4.ref;

import org.junit.Test;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * @author Shegai Evgenii
 * @since 29.10.2021
 * @version 1.0
 */

public class CasheDemoTest {

    @Test
    public void whenGetDataFromStorage() throws IOException {
        InputStream is = CasheDemo.class.getClassLoader().getResourceAsStream("data/text.txt");
        String result = new BufferedReader(new InputStreamReader(is)).readLine();
        assertThat(result, is("Hello i am simple test"));
    }

    @Test
    public void whenGetDataFromCache() {
        SoftCashe<String, String> softCache = new SoftCashe<>();
        softCache.put("Key", "Value");
        CasheDemo demo = new CasheDemo(softCache, new FileDataStorage());
        assertThat(demo.get("Key"), is("Value"));
    }

    @Test
    public void whenGetNull() {
        CasheDemo demo = new CasheDemo(new SoftCashe(), new FileDataStorage());
        assertNull(demo.get("not.txt"));
    }
}