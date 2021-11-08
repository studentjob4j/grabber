package ru.job4.kisss;

import org.junit.Test;
import java.util.Comparator;
import java.util.List;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class MaxMinTest {

    private final Comparator<Integer> comparator = Comparator.naturalOrder();
    private final MaxMin maxMin = new MaxMin();

    @Test
    public void whenGetMaxValue() {
        List<Integer> list = List.of(1, 5, 3, 4);
        int result = maxMin.max(list, comparator);
        assertThat(result, is(5));
    }

    @Test
    public void whenGetMinValue() {
        List<Integer> list = List.of(4, 2, 3, 5);
        int result = maxMin.min(list, comparator);
        assertThat(result, is(2));
    }
}