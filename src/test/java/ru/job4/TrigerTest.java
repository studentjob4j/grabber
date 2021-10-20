package ru.job4;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class TrigerTest {

    @Test
    public void whenGetSum() {
        Triger triger = new Triger();
        assertThat(triger.summ(1, 1), is(2));
    }

}