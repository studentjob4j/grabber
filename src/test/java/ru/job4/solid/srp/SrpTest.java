package ru.job4.solid.srp;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SrpTest {

    @Test
    public void whenGetNumber() {
        Srp demo = new Srp();
        int result = demo.square(2, 5);
        assertThat(result, is(49));
    }

}